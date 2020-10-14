package tonet;

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
@ExecuteOn(TaskExecutors.IO)
public class CallController
{
    private final OpenViduClient client;

    public CallController(OpenViduClient client)
    {
        this.client = client;
    }

    @Post
    public char[] generateToken(@NotNull @Valid @Body CallPayload payload) throws Exception
    {
        String sessionId = payload.getSessionId()
            .replace('ğ', 'g').replace('Ğ', 'G').replace('ü', 'u').replace('Ü', 'U')
            .replace('ş', 's').replace('Ş', 'S').replace('ı', 'i').replace('İ', 'I')
            .replace('ö', 'o').replace('Ö', 'O').replace('ç', 'c').replace('Ç', 'C')
            .replaceAll("[^0-9a-zA-Z-]", "_");

        SessionProperties properties = new SessionProperties();
        properties.setCustomSessionId(sessionId);

        Session session = null;

        try
        {
            session = client.createSession(properties).blockingGet();
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

        Token token = client.createToken(options).blockingGet();

        return token.getId().toCharArray();
    }
}
