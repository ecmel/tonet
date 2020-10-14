package tonet;

import javax.inject.Singleton;
import org.reactivestreams.Publisher;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;

@BasicAuth
@Singleton
public class BasicAuthClientFilter implements HttpClientFilter
{
    private final String username;
    private final String password;

    public BasicAuthClientFilter(
        @Value("${openvidu.username}") String username,
        @Value("${openvidu.password}") String password)
    {
        this.username = username;
        this.password = password;
    }

    @Override
    public Publisher<? extends HttpResponse<?>> doFilter(
        MutableHttpRequest<?> request,
        ClientFilterChain chain)
    {
        return chain.proceed(request.basicAuth(username, password));
    }
}
