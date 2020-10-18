package tonet;

import java.text.Normalizer;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.reactivex.Single;

@Controller("/call")
public class CallController
{
    private final OpenViduClient openVidu;

    public CallController(OpenViduClient openVidu)
    {
        this.openVidu = openVidu;
    }

    @Post
    public Single<char[]> generateToken(@Body @NotNull @Valid CallOptions options) throws Exception
    {
        String sessionId = Normalizer
            .normalize(options.getSessionId(), Normalizer.Form.NFD)
            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
            .replaceAll("[^0-9a-zA-Z-]", "_");

        SessionProperties properties = new SessionProperties();
        properties.setCustomSessionId(sessionId);

        return openVidu
            .createSession(properties)
            .flatMap(session -> createToken(session.getId()))
            .onErrorResumeNext(e -> createToken(sessionId))
            .map(token -> token.getId().toCharArray());
    }

    private Single<Token> createToken(String sessionId)
    {
        TokenOptions options = new TokenOptions();
        options.setSession(sessionId);
        options.setRole(OpenViduRole.PUBLISHER);
        return openVidu.createToken(options);
    }
}
