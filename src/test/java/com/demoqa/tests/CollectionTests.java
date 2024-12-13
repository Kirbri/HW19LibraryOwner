package com.demoqa.tests;

import api.AuthorizationApi;
import api.BooksApi;
import models.lombok.BookRequestModel;
import models.lombok.GenerateTokenLoginRequestModel;
import models.lombok.IsbnRequestModel;
import models.lombok.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import java.util.Arrays;
import java.util.HashSet;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

@Tag("SMOKE")
public class CollectionTests extends TestBase {

    @Test
    @DisplayName("Авторизация на demoqa, удаление имеющихся книг из корзины пользователя и добавление новых")
    void addBookToCollectionTest() {
        GenerateTokenLoginRequestModel authData = new GenerateTokenLoginRequestModel();
        AuthorizationApi authorizationApi = new AuthorizationApi();
        LoginResponseModel loginResponseLombokModel = authorizationApi.login(authData);

        BooksApi booksApi = new BooksApi();
        booksApi.deleteAllBook(loginResponseLombokModel);

        IsbnRequestModel isbnRequestModel1 = new IsbnRequestModel("9781449365035");
        IsbnRequestModel isbnRequestModel2 = new IsbnRequestModel("9781449325862");
        IsbnRequestModel isbnRequestModel3 = new IsbnRequestModel("9781449337711");
        HashSet<IsbnRequestModel> isbnSet = new HashSet<>(
                Arrays.asList(isbnRequestModel1, isbnRequestModel2, isbnRequestModel3));

        BookRequestModel bookRequestModel = new BookRequestModel();
        bookRequestModel.setUserId(loginResponseLombokModel.getUserId());
        bookRequestModel.setCollectionOfIsbns(isbnSet);

        booksApi.addBooks(loginResponseLombokModel, bookRequestModel);

        open("/favicon.ico");

        getWebDriver().manage().addCookie(new Cookie("userID", loginResponseLombokModel.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("token", loginResponseLombokModel.getToken()));
        getWebDriver().manage().addCookie(new Cookie("expires", loginResponseLombokModel.getExpires()));

        open("/profile");
        $("#userName-value").shouldHave(text(TestData.login));
        $(".ReactTable").shouldHave(text("Speaking JavaScript"));
        $(".ReactTable").shouldHave(text("Git Pocket Guide"));
        $(".ReactTable").shouldHave(text("Designing Evolvable Web APIs with ASP.NET"));
    }
}