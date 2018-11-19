package test.common.config;

public class Constants {
    public static final int WAIT_SHORT_SECONDS = ApplicationProperties.getInteger(ApplicationProperties.ApplicationProperty.WAIT_SHORT_SECONDS);
    public static final int WAIT_NORMAL_SECONDS = ApplicationProperties.getInteger(ApplicationProperties.ApplicationProperty.WAIT_NORMAL_SECONDS);
    public static final int WAIT_LONG_SECONDS = ApplicationProperties.getInteger(ApplicationProperties.ApplicationProperty.WAIT_LONG_SECONDS);
    public static final int WAIT_TOO_LONG_SECONDS = ApplicationProperties.getInteger(ApplicationProperties.ApplicationProperty.WAIT_TOO_LONG_SECONDS);
}