package tonet;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;
import io.openvidu.java.client.TokenOptions;

@Controller("/call")
@ExecuteOn(TaskExecutors.IO)
public class CallController
{
    private final OpenVidu openVidu;

    public CallController(OpenVidu openVidu)
    {
        this.openVidu = openVidu;
    }

    @Post
    public char[] generateToken(@NotNull @Valid @Body CallPayload payload) throws Exception
    {
        String sessionId = payload.getSessionId()
            .replace('ğ', 'g').replace('Ğ', 'G').replace('ü', 'u').replace('Ü', 'U')
            .replace('ş', 's').replace('Ş', 'S').replace('ı', 'i').replace('İ', 'I')
            .replace('ö', 'o').replace('Ö', 'O').replace('ç', 'c').replace('Ç', 'C')
            .replaceAll("[^0-9a-zA-Z-]", "_");

        SessionProperties properties = new SessionProperties.Builder()
            .customSessionId(sessionId)
            .build();

        Session session = openVidu.createSession(properties);

        TokenOptions options = new TokenOptions.Builder()
            .role(OpenViduRole.PUBLISHER)
            .build();

        return session.generateToken(options).toCharArray();
    }
}
