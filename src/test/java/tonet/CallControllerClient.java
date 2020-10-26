package tonet;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

@Client(value = "/call", errorType = String.class)
public interface CallControllerClient
{
    @Post
    public HttpResponse<String> generateToken(@Body CallOptions options);
}
