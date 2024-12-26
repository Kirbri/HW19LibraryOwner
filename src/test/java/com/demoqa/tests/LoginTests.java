package com.demoqa.tests;

import api.AuthorizationApi;
import models.lombok.LoginResponseModel;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;

public class LoginTests extends TestBase {

    @Test
    public void successfulLoginUITest() {
        step("Open login page", () ->
                open("/login"));

        step("Enter credentials", () -> {
            $("#userName").setValue(TestData.getLogin());
            $("#password").setValue(TestData.getPassword());
            $("#login").click();
        });

        step("Check login on profile", () ->
                $("#userName-value").shouldHave(text(TestData.getLogin())));
    }

    @Test
    public void successfulLoginAPITest() {

        AuthorizationApi authorizationApi = new AuthorizationApi();
        LoginResponseModel loginResponseLombokModel = authorizationApi.login();

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