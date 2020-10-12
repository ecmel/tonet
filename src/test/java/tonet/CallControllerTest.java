package tonet;

import static org.junit.jupiter.api.Assertions.*;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.Session;

@MicronautTest
class CallControllerTest
{
    @Inject
    @Client("/call")
    HttpClient client;

    @Inject
    OpenVidu openVidu;

    @Test
    void shouldValidateSessionId()
    {
        CallPayload payload = new CallPayload();

        payload.setSessionId("123");

        HttpResponse<String> res = client
            .toBlocking()
            .exchange(HttpRequest.POST("/", payload),
                Argument.of(String.class),
                Argument.of(String.class));

        assertNotEquals(HttpStatus.OK, res.getStatus());
    }

    @Test
    void shouldReturnTokenQuoted()
    {
        CallPayload payload = new CallPayload();

        payload.setSessionId("quoted");

        String res = client
            .toBlocking()
            .retrieve(HttpRequest.POST("/", payload));

        assertEquals('"', res.charAt(0));
    }

    @Test
    void shouldRemoveDiacriticsFromSessionId()
    {
        CallPayload payload = new CallPayload();

        payload.setSessionId("AaĞğŞşİıÖöÇçĞğŞşİıÖöÇç-0123456789-%%");

        String res = client
            .toBlocking()
            .retrieve(HttpRequest.POST("/", payload));

        assertNotEquals(-1, res.indexOf("AaGgSsIiOoCcGgSsIiOoCc-0123456789-__"));
    }

    @Test
    void shouldJoinExistingSession() throws Exception
    {
        CallPayload payload = new CallPayload();

        payload.setSessionId("existing-session");

        HttpResponse<Object> res;

        for (int i = 0; i < 10; i++)
        {
            res = client
                .toBlocking()
                .exchange(HttpRequest.POST("/", payload));

            assertEquals(HttpStatus.OK, res.getStatus());
        }

        openVidu.fetch();

        int count = 0;

        for (Session session : openVidu.getActiveSessions())
        {
            if (payload.getSessionId().equals(session.getSessionId()))
            {
                count++;
            }
        }

        assertEquals(1, count);
    }
}
