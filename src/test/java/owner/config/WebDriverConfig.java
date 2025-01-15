package owner.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:properties/${browser}.properties",
})
public interface WebDriverConfig extends Config {

    @Key("browser.name")
    String getBrowser();

    @Key("browser.version")
    String getBrowserVersion();

    @Key("browser.windowSize")
    String getBrowserWindowSize();

    @Key("browser.baseUrl")
    String getBaseUrl();

    @Key("browser.isRemote")
    @DefaultValue("false")
    boolean isRemote();

    @Key("remote.user")
    String getRemoteUser();

    @Key("remote.password")
    String getRemotePassword();

    @Key("remote.url")
    String getRemoteUrl();
}