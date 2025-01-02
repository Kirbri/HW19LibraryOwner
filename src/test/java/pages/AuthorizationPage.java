package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import models.LoginResponseModel;
import org.openqa.selenium.Cookie;
import tests.TestData;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class AuthorizationPage {
    final SelenideElement userName = $("#userName");
    final SelenideElement password = $("#password");
    final SelenideElement login = $("#login");
    final SelenideElement userNameValue = $("#userName-value");

    @Step("UI Open login page")
    public AuthorizationPage openLoginUIPage() {
        open("/login");
        return this;
    }

    @Step("UI Enter credentials")
    public AuthorizationPage enterCredentialsUIPage() {
        userName.setValue(TestData.getLogin());
        password.setValue(TestData.getPassword());
        login.click();
        return this;
    }

    @Step("UI Check login on profile")
    public AuthorizationPage checkLoginUIPage() {
        userNameValue.shouldHave(text(TestData.getLogin()));
        return this;
    }

    @Step("UI Add cookie")
    public AuthorizationPage addCookieUIPage(LoginResponseModel loginResponseLombokModel) {
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", loginResponseLombokModel.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("token", loginResponseLombokModel.getToken()));
        getWebDriver().manage().addCookie(new Cookie("expires", loginResponseLombokModel.getExpires()));
        return this;
    }
}