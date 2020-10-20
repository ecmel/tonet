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

        SessionOptions sessionOptions = new SessionOptions();
        sessionOptions.setCustomSessionId(sessionId);

        TokenOptions tokenOptions = new TokenOptions();
        tokenOptions.setSession(sessionId);
        tokenOptions.setRole(OpenViduRole.PUBLISHER);

        return openVidu
            .createSession(sessionOptions)
            .flatMap(session -> openVidu.createToken(tokenOptions))
            .onErrorResumeNext(e -> openVidu.createToken(tokenOptions))
            .map(token -> token.getId().toCharArray());
    }
}
