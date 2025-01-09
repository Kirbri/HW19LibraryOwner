package tests;

import api.AccountApi;
import api.AuthorizationApi;
import api.BookStoreApi;
import com.codeborne.selenide.Selenide;
import helpers.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.AuthorizationPage;
import pages.ProfilePage;

import java.time.LocalDate;
import java.time.LocalTime;

import static tests.TestData.*;

@Tag("SMOKE")
public class CollectionTests extends TestBase {
    final BookStoreApi booksApi = new BookStoreApi();
    final AccountApi accountApi = new AccountApi();
    final ProfilePage profilePage = new ProfilePage();
    final AuthorizationPage authorizationPage = new AuthorizationPage();
    final AuthorizationApi authorizationApi = new AuthorizationApi();

    @Test
    @WithLogin
    @DisplayName("Авторизация на demoqa @WithLogin, API удаление имеющихся книг из корзины пользователя и добавление новых," +
            "UI проверка книг в профиле")
    void addBookToCollectionTest() {
        booksApi.deleteAllBook();
        booksApi.addBooks();

        profilePage.openPageProfile()
                .checkProfile()
                .show10Rows()
                .checkCountAndTitleOfBooks();
    }

    @Test
    @WithLogin
    @DisplayName("Авторизация на demoqa @WithLogin, API удаление книг, добавление новых, UI проверка книг в профиле," +
            "удаление одной любой книги через API")
    void deleteOneRandomBookTest() {
        booksApi.deleteAllBook();
        booksApi.addBooks();

        profilePage.openPageProfile()
                .checkProfile()
                .show10Rows()
                .checkCountAndTitleOfBooks();

        booksApi.deleteOneBook();

        Selenide.refresh();
        profilePage.show10Rows()
                .checksCountOfBookOnProfile("One");
    }

    @Test
    @WithLogin
    @DisplayName("Авторизация @WithLogin, API добавление книг, UI удаление первой книги")
    void deleteOneBookToCollectionTest() {
        booksApi.deleteAllBook();
        booksApi.addBooks();

        profilePage.openPageProfile()
                .checkProfile()
                .checksBookOnProfile()
                .deleteFirstBook()
                .show10Rows()
                .checksCountOfBookOnProfile("One");
    }

    @Test
    @WithLogin
    @DisplayName("Авторизация @WithLogin, API добавление всех книг, UI удаление всех книг")
    void deleteAllBookToCollectionTest() {
        booksApi.deleteAllBook();
        booksApi.addBooks();

        profilePage.openPageProfile()
                .checkProfile()
                .deleteAllBooks()
                .checksCountOfBookOnProfile("All");
    }

    @Test
    @DisplayName("Регистрация аккаунта")
    void createAccountTest() {

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        login = "Login_" + currentDate + currentTime;
        password = "Password!_" + currentDate + currentTime;
        accountApi.createNewAccount(login, password);

        authorizationPage.openLoginUIPage()
                .enterCredentialsUIPage()
                .checkLoginUIPage();
    }

    @Test
    @DisplayName("Регистрация и удаление аккаунта")
    void deleteCreatedAccountTest() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        login = "Login_" + currentDate + currentTime;
        password = "Password!_" + currentDate + currentTime;
        accountApi.createNewAccount(login, password);
        authorizationPage.openLoginUIPage()
                .enterCredentialsUIPage()
                .checkLoginUIPage();

        loginResponseLombokModel = authorizationApi.login();

        profilePage.logOutOnProfile();

        accountApi.deleteAccount();
    }

    @Test
    @WithLogin
    @DisplayName("Получение полной информации о случайной книге")
    void getInfoAboutRandomBookTest() {
        booksApi.getInfoAboutBook();
        profilePage.openInfoAboutBook();
    }
}