package tonet;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.GenericContainer;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CallControllerTest implements TestPropertyProvider
{
    @Inject
    CallControllerClient client;

    @Inject
    OpenViduClient openVidu;

    @Test
    void shouldValidateSessionId()
    {
        CallOptions options = new CallOptions();
        options.setSessionId("123");

        HttpResponse<String> res = client.generateToken(options);
        assertNotEquals(HttpStatus.OK, res.getStatus());
    }

    @Test
    void shouldReturnTokenQuoted()
    {
        CallOptions options = new CallOptions();
        options.setSessionId("quoted");

        HttpResponse<String> res = client.generateToken(options);
        assertEquals('"', res.body().charAt(0));
    }

    @Test
    void shouldRemoveDiacriticsFromSessionId()
    {
        CallOptions options = new CallOptions();
        options.setSessionId("AaĞğŞşİıÖöÇçĞğŞşİıÖöÇç-0123456789-%%");

        HttpResponse<String> res = client.generateToken(options);
        assertNotEquals(-1, res.body().indexOf("AaGgSsI_OoCcGgSsI_OoCc-0123456789-__"));
    }

    @Test
    void shouldJoinExistingSession()
    {
        CallOptions options = new CallOptions();
        options.setSessionId("existing-session");

        HttpResponse<String> res;

        for (int i = 0; i < 10; i++)
        {
            res = client.generateToken(options);
            assertEquals(HttpStatus.OK, res.getStatus());
        }

        int count = 0;

        for (Session session : openVidu.getSessions().blockingGet().getContent())
        {

            if (options.getSessionId().equals(session.getSessionId()))
            {
                count++;
            }
        }

        assertEquals(1, count);
    }

    static final GenericContainer<?> container;

    static
    {
        container = new GenericContainer<>("openvidu/openvidu-server-kms:latest");
        container.withExposedPorts(4443).start();
    }

    @Override
    public Map<String, String> getProperties()
    {
        Map<String, String> map = new HashMap<>();
        map.put("openvidu.hostname", "https://localhost:" + container.getFirstMappedPort());
        return map;
    }
}
