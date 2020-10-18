package tonet;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

@Client(value = "/", errorType = String.class)
public interface TonetClient
{
    @Post("/call")
    public HttpResponse<String> generateToken(@Body CallPayload payload);
}
