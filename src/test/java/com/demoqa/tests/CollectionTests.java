package com.demoqa.tests;

import api.AccountApi;
import api.BookStoreApi;
import helpers.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

import static io.qameta.allure.Allure.step;

@Tag("SMOKE")
public class CollectionTests extends TestBase {
    final BookStoreApi booksApi = new BookStoreApi();
    final AccountApi accountApi = new AccountApi();
    final ProfilePage profilePage = new ProfilePage();

    @Test
    @WithLogin
    @DisplayName("Авторизация на demoqa, удаление имеющихся книг из корзины пользователя и добавление новых")
    void addBookToCollectionTest() {

        booksApi.getAllBooks();
        accountApi.getInfoAccount();
        booksApi.deleteOneBook();
        booksApi.deleteAllBook();
        booksApi.addBooks();
        booksApi.getInfoAboutBook();

        accountApi.getInfoAccount();

        step("UI Open page on profile", () -> {
            profilePage.openProfile();
        });
        step("UI Show all books in profile", () -> {
            profilePage.show10Rows();
        });
        step("UI Checks count and title books", () -> {
            profilePage.checkCountAndTitleOfBooks();
        });
    }
}