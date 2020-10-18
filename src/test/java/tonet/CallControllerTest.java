package tonet;

import static org.junit.jupiter.api.Assertions.*;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;

@MicronautTest
class CallControllerTest
{
    @Inject
    TonetClient client;

    @Inject
    OpenViduClient openVidu;

    @Test
    void shouldValidateSessionId()
    {
        CallPayload payload = new CallPayload();
        payload.setSessionId("123");

        HttpResponse<String> res = client.generateToken(payload);
        assertNotEquals(HttpStatus.OK, res.getStatus());
    }

    @Test
    void shouldReturnTokenQuoted()
    {
        CallPayload payload = new CallPayload();
        payload.setSessionId("quoted");

        HttpResponse<String> res = client.generateToken(payload);
        assertEquals('"', res.body().charAt(0));
    }

    @Test
    void shouldRemoveDiacriticsFromSessionId()
    {
        CallPayload payload = new CallPayload();
        payload.setSessionId("AaĞğŞşİıÖöÇçĞğŞşİıÖöÇç-0123456789-%%");

        HttpResponse<String> res = client.generateToken(payload);
        assertNotEquals(-1, res.body().indexOf("AaGgSsI_OoCcGgSsI_OoCc-0123456789-__"));
    }

    @Test
    void shouldJoinExistingSession() throws Exception
    {
        CallPayload payload = new CallPayload();
        payload.setSessionId("existing-session");

        HttpResponse<String> res;

        for (int i = 0; i < 10; i++)
        {
            res = client.generateToken(payload);
            assertEquals(HttpStatus.OK, res.getStatus());
        }

        int count = 0;

        for (Session session : openVidu.getSessions().blockingGet().getContent())
        {

            if (payload.getSessionId().equals(session.getSessionId()))
            {
                count++;
            }
        }

        assertEquals(1, count);
    }
}
