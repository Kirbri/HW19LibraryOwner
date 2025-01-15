package owner.config;

import org.aeonbits.owner.ConfigFactory;

public class WebDriverProvider {

    public static final WebDriverConfig WEB_DRIVER_CONFIG = ConfigFactory
            .create(WebDriverConfig.class, System.getProperties());
    public static final LoginConfig LOGIN_CONFIG = ConfigFactory
            .create(LoginConfig.class, System.getProperties());
}