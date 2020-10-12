package tonet;

import javax.inject.Singleton;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import io.openvidu.java.client.OpenVidu;

@Factory
public class OpenViduFactory
{
    private final String hostname;
    private final String secret;

    public OpenViduFactory(
        @Value("${openvidu.hostname}") String hostname,
        @Value("${openvidu.secret}") String secret)
    {
        this.hostname = hostname;
        this.secret = secret;
    }

    @Singleton
    public OpenVidu create()
    {
        return new OpenVidu(hostname, secret);
    }
}
