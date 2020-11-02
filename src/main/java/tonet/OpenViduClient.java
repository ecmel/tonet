package tonet;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Single;

@BasicAuth
@Client("${openvidu.uri}/api")
public interface OpenViduClient
{
    @Get("/sessions")
    public Single<OpenViduCollection<Session>> getSessions();

    @Post("/sessions")
    public Single<Session> createSession(@Body @NotNull @Valid SessionOptions properties);

    @Post("/tokens")
    public Single<Token> createToken(@Body @NotNull @Valid TokenOptions options);
}
