package tonet;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Single;

@BasicAuth
@Client("${openvidu.hostname}")
public interface OpenViduClient
{
    @Post("/api/sessions")
    public Single<Session> createSession(@NotNull @Valid @Body SessionProperties properties);

    @Post("/api/tokens")
    public Single<Token> createToken(@NotNull @Valid @Body TokenOptions options);

    @Get("/api/sessions")
    public Single<OpenViduCollection<Session>> getSessions();
}
