package tonet;

import static org.junit.jupiter.api.Assertions.*;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;

@MicronautTest
public class ApplicationTest
{
    @Inject
    EmbeddedApplication<?> application;

    @Test
    void testItWorks()
    {
        assertTrue(application.isRunning());
    }
}
