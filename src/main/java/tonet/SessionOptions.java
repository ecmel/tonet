package tonet;

import javax.validation.constraints.NotNull;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class SessionOptions
{
    private String mediaMode;
    private String customSessionId;
    private String recordingMode;
    private String defaultOutputMode;
    private String defaultRecordingLayout;
    private String defaultCustomLayout;

    public String getMediaMode()
    {
        return mediaMode;
    }

    public void setMediaMode(String mediaMode)
    {
        this.mediaMode = mediaMode;
    }

    @NotNull
    public String getCustomSessionId()
    {
        return customSessionId;
    }

    public void setCustomSessionId(String customSessionId)
    {
        this.customSessionId = customSessionId;
    }

    public String getRecordingMode()
    {
        return recordingMode;
    }

    public void setRecordingMode(String recordingMode)
    {
        this.recordingMode = recordingMode;
    }

    public String getDefaultOutputMode()
    {
        return defaultOutputMode;
    }

    public void setDefaultOutputMode(String defaultOutputMode)
    {
        this.defaultOutputMode = defaultOutputMode;
    }

    public String getDefaultRecordingLayout()
    {
        return defaultRecordingLayout;
    }

    public void setDefaultRecordingLayout(String defaultRecordingLayout)
    {
        this.defaultRecordingLayout = defaultRecordingLayout;
    }

    public String getDefaultCustomLayout()
    {
        return defaultCustomLayout;
    }

    public void setDefaultCustomLayout(String defaultCustomLayout)
    {
        this.defaultCustomLayout = defaultCustomLayout;
    }
}
