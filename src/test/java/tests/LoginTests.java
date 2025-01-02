package tests;

import api.AuthorizationApi;
import models.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.AuthorizationPage;
import pages.ProfilePage;

public class LoginTests extends TestBase {
    final AuthorizationPage authorizationPage = new AuthorizationPage();
    final ProfilePage profilePage = new ProfilePage();

    @Test
    @DisplayName("Open login page in UI")
    public void successfulLoginUITest() {
        authorizationPage.openLoginUIPage()
                .enterCredentialsUIPage()
                .checkLoginUIPage();
    }

    @Test
    @DisplayName("Open login page in API")
    public void successfulLoginAPITest() {

        AuthorizationApi authorizationApi = new AuthorizationApi();
        LoginResponseModel loginResponseLombokModel = authorizationApi.login();

        authorizationPage.addCookieUIPage(loginResponseLombokModel);

        profilePage.openProfile();

    }
}