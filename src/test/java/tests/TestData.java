package tests;

import lombok.Getter;
import models.LoginResponseModel;

import java.util.Map;

import static owner.config.WebDriverProvider.LOGIN_CONFIG;

public class TestData {

    @Getter
    public static String login = LOGIN_CONFIG.getProfileLogin(),
            password = LOGIN_CONFIG.getProfilePassword(),
            randomBookTitle, randomBookIsbn;

    public static LoginResponseModel loginResponseLombokModel;

    public static Map<String, String> allBooksIsbnTitle;

    public static Map<String, String> booksInProfileIsbnTitle;

    public static int sizeAllBooksCollection;

    public static int sizeInProfileBooksCollection;
}