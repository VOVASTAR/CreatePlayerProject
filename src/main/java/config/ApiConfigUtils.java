package config;

import org.aeonbits.owner.ConfigFactory;

public class ApiConfigUtils {
    public static final IConfig CONFIG;

    static {
        ConfigFactory.setProperty("HOME", System.getProperty("user.dir"));
        CONFIG = ConfigFactory.create(IConfig.class);
    }

    @org.aeonbits.owner.Config.Sources("file:${HOME}/src/main/resources/config/local_config.properties")
    public interface IConfig extends org.aeonbits.owner.Config {
        @Key("api.url")
        String apiUrl();
    }
}