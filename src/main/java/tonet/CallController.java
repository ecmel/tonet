package tonet;

import java.text.Normalizer;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

@Controller("/call")
public class CallController
{
    private final OpenViduClient openVidu;

    public CallController(OpenViduClient openVidu)
    {
        this.openVidu = openVidu;
    }

    @Post
    @ExecuteOn(TaskExecutors.IO)
    public char[] generateToken(@Body @NotNull @Valid CallPayload payload) throws Exception
    {
        String sessionId = Normalizer
            .normalize(payload.getSessionId(), Normalizer.Form.NFD)
            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
            .replaceAll("[^0-9a-zA-Z-]", "_");

        SessionProperties properties = new SessionProperties();
        properties.setCustomSessionId(sessionId);

        Session session = null;

        try
        {
            session = openVidu.createSession(properties).blockingGet();
        }
        catch (HttpClientResponseException e)
        {
            if (!HttpStatus.CONFLICT.equals(e.getStatus()))
            {
                throw e;
            }
        }

        TokenOptions options = new TokenOptions();
        options.setSession(session == null ? sessionId : session.getId());
        options.setRole(OpenViduRole.PUBLISHER);

        Token token = openVidu.createToken(options).blockingGet();

        return token.getId().toCharArray();
    }
}
