package tonet;

import javax.validation.constraints.NotNull;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class TokenOptions
{
    private String session;
    private OpenViduRole role;
    private String data;

    @NotNull
    public String getSession()
    {
        return session;
    }

    public void setSession(String session)
    {
        this.session = session;
    }

    public OpenViduRole getRole()
    {
        return role;
    }

    public void setRole(OpenViduRole role)
    {
        this.role = role;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }
}
