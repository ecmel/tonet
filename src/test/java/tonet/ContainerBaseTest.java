package tonet;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.GenericContainer;
import io.micronaut.test.support.TestPropertyProvider;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ContainerBaseTest implements TestPropertyProvider
{
    static final int port = 4443;
    static final GenericContainer<?> container;

    static
    {
        container = new GenericContainer<>("openvidu/openvidu-server-kms:latest");
        container.withExposedPorts(port).start();
    }

    @Override
    public Map<String, String> getProperties()
    {
        Map<String, String> map = new HashMap<>();
        map.put("openvidu.port", container.getMappedPort(port).toString());
        return map;
    }
}
