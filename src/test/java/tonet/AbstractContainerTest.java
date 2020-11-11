package tonet;

import static java.util.Collections.*;
import static org.testcontainers.utility.DockerImageName.*;
import java.util.Map;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.GenericContainer;
import io.micronaut.test.support.TestPropertyProvider;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractContainerTest implements TestPropertyProvider
{
    static final GenericContainer<?> container;

    static
    {
        container = new GenericContainer<>(parse("openvidu/openvidu-server-kms:2.15.0"));
        container.withExposedPorts(4443).start();
    }

    @Override
    public Map<String, String> getProperties()
    {
        return singletonMap("openvidu.port", container.getFirstMappedPort().toString());
    }
}
