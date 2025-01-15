package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static owner.config.WebDriverProvider.LOGIN_CONFIG;
import static owner.config.WebDriverProvider.WEB_DRIVER_CONFIG;
import static tests.TestData.login;
import static tests.TestData.password;

public class TestBase {

    @BeforeAll
    public static void setUp() {
        System.setProperty("browser", System.getProperty("browser"));
        Configuration.browserSize = WEB_DRIVER_CONFIG.getBrowserWindowSize();
        Configuration.browserVersion = WEB_DRIVER_CONFIG.getBrowserVersion();
        Configuration.browser = WEB_DRIVER_CONFIG.getBrowser();
        Configuration.baseUrl = WEB_DRIVER_CONFIG.getBaseUrl();
        RestAssured.baseURI = WEB_DRIVER_CONFIG.getBaseUrl();
        RestAssured.defaultParser = Parser.JSON;
        Configuration.timeout = 10000;
        if(WEB_DRIVER_CONFIG.isRemote()) {
            Configuration.remote = "https://" + WEB_DRIVER_CONFIG.getRemoteUser() + ":" +
                    WEB_DRIVER_CONFIG.getRemotePassword() + "@" + WEB_DRIVER_CONFIG.getRemoteUrl() + "/wd/hub";
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
            Configuration.browserCapabilities = capabilities;
        }
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void afterEachCloseWebDriver() {
        login = LOGIN_CONFIG.getProfileLogin();
        password = LOGIN_CONFIG.getProfilePassword();

        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        if (Configuration.browser.equals("chrome")) {
            Attach.browserConsoleLogs();
        }
        Attach.addVideo();
        Selenide.closeWebDriver();
    }
}