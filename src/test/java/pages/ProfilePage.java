package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.demoqa.tests.TestData;

import static com.codeborne.selenide.CollectionCondition.itemWithText;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.demoqa.tests.TestData.booksInProfileIsbnTitle;
import static com.demoqa.tests.TestData.sizeInProfileBooksCollection;

public class ProfilePage {
    private final SelenideElement userNameLogin = $("#userName-value"),
    menuProfile = $(".element-list.collapse.show #item-3"),
    rowsPerPage10 = $("[aria-label='rows per page'] [value='10']");

    private final ElementsCollection reactTableDataOfBooks = $$(".ReactTable img"),
    reactTableTitleOfBooks =  $$(".ReactTable .mr-2");

    public ProfilePage openProfile() {
        open("/profile");
        userNameLogin.shouldHave(text(TestData.getLogin()));
        menuProfile.scrollTo().should(cssClass("active"));
        return this;
    }

    public ProfilePage show10Rows() {
        rowsPerPage10.click();
        return this;
    }

    public ProfilePage checkCountAndTitleOfBooks() {
        reactTableDataOfBooks.shouldHave(size(sizeInProfileBooksCollection));
        for (String key : booksInProfileIsbnTitle.keySet()) {
            reactTableTitleOfBooks.shouldHave(itemWithText(booksInProfileIsbnTitle.get(key)));
        }
        return this;
    }
}