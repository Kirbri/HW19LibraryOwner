package owner.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:properties/credentials.properties",
})
public interface LoginConfig extends Config {

    @Key("profile.user")
    String getProfileLogin();

    @Key("profile.password")
    String getProfilePassword();
}