package tonet;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class CallOptions
{
    private String sessionId;

    @NotNull
    @Size(min = 4, max = 50)
    public String getSessionId()
    {
        return sessionId;
    }

    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }
}
