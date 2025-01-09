package pages;

import api.AccountApi;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import tests.TestData;

import static com.codeborne.selenide.CollectionCondition.itemWithText;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static tests.TestData.*;

public class ProfilePage {
    final AccountApi accountApi = new AccountApi();

    private final SelenideElement userNameLogin = $("#userName-value"),
            menuProfile = $(".element-list.collapse.show #item-3"),
            rowsPerPage10 = $("[aria-label='rows per page'] [value='10']"),
            deleteAllBooksBtn = $(".text-right.button.di #submit"),
            confirmationOfDeletion = $("#closeSmallModal-ok"),
            deleteOneBookBtn = $("#delete-record-undefined"),
            booksOnProfile = $(".profile-wrapper"),
            logOut = $(".text-right.col-md-5.col-sm-12 #submit"),
            titleInfoAboutBook = $("#title-wrapper #userName-value");

    private final ElementsCollection reactTableDataOfBooks = $$(".ReactTable img"),
            reactTableTitleOfBooks = $$(".ReactTable .mr-2");

    @Step("UI Open profile")
    public ProfilePage openPageProfile() {
        open("/profile");
        return this;
    }

    @Step("UI Check login in profile")
    public ProfilePage checkProfile() {
        userNameLogin.shouldHave(text(TestData.getLogin()));
        menuProfile.scrollTo().should(cssClass("active"));
        return this;
    }

    @Step("UI Show 10 books in profile")
    public ProfilePage show10Rows() {
        rowsPerPage10.click();
        return this;
    }

    @Step("UI Checks count and title books")
    public ProfilePage checkCountAndTitleOfBooks() {
        accountApi.getInfoAccount();
        reactTableDataOfBooks.shouldHave(size(sizeInProfileBooksCollection));
        for (String key : booksInProfileIsbnTitle.keySet()) {
            reactTableTitleOfBooks.shouldHave(itemWithText(booksInProfileIsbnTitle.get(key)));
        }
        return this;
    }

    @Step("UI Delete all books")
    public ProfilePage deleteAllBooks() {
        deleteAllBooksBtn.scrollTo().click();
        confirmationOfDeletion.click();
        Selenide.confirm();
        return this;
    }

    @Step("UI Delete first book")
    public ProfilePage deleteFirstBook() {
        deleteOneBookBtn.scrollTo().click();
        confirmationOfDeletion.click();
        Selenide.confirm();
        return this;
    }

    @Step("UI Checks books on profile")
    public ProfilePage checksBookOnProfile() {
        booksOnProfile.shouldNotHave(cssClass("rt-noData"));
        return this;
    }

    @Step("UI Checks count of books on profile")
    public ProfilePage checksCountOfBookOnProfile(String numberOfDeletedBooks) {
        switch (numberOfDeletedBooks) {
            case ("All"):
                checksBookOnProfile();
                break;
            case ("One"):
                accountApi.getInfoAccount();
                reactTableDataOfBooks.shouldHave(size(sizeInProfileBooksCollection));
                break;
        }
        return this;
    }

    @Step("UI Log out")
    public ProfilePage logOutOnProfile() {
        logOut.click();
        return this;
    }

    @Step("Open page with info about book")
    public ProfilePage openInfoAboutBook() {
        open("/profile?book=" + randomBookIsbn);
        titleInfoAboutBook.should(text(randomBookTitle));
        return this;
    }
}