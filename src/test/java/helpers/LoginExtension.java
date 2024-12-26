package helpers;

import api.AuthorizationApi;
import com.demoqa.tests.TestData;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.demoqa.tests.TestData.loginResponseLombokModel;
import static io.qameta.allure.Allure.step;

public class LoginExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {

        AuthorizationApi authorizationApi = new AuthorizationApi();
        loginResponseLombokModel = authorizationApi.login();

        step("Add cookie", () -> {
                    open("/favicon.ico");
                    getWebDriver().manage().addCookie(new Cookie("userID", loginResponseLombokModel.getUserId()));
                    getWebDriver().manage().addCookie(new Cookie("token", loginResponseLombokModel.getToken()));
                    getWebDriver().manage().addCookie(new Cookie("expires", loginResponseLombokModel.getExpires()));
                });

        step("Check login on profile", () -> {
            open("/profile");
            $("#userName-value").shouldHave(text(TestData.getLogin()));
        });
    }
}