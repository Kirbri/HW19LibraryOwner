package helpers;

import api.AuthorizationApi;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import pages.AuthorizationPage;
import pages.ProfilePage;

import static tests.TestData.loginResponseLombokModel;

public class LoginExtension implements BeforeEachCallback {
    final AuthorizationPage authorizationPage = new AuthorizationPage();
    final ProfilePage profilePage = new ProfilePage();

    @Override
    public void beforeEach(ExtensionContext context) {

        AuthorizationApi authorizationApi = new AuthorizationApi();
        loginResponseLombokModel = authorizationApi.login();

        authorizationPage.addCookieUIPage(loginResponseLombokModel);
        profilePage.openPageProfile();
        profilePage.checkProfile();

    }
}