package api;

import io.qameta.allure.Step;
import models.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static specs.GeneralSpec.*;
import static tests.TestData.*;

public class BookStoreApi {
    final AccountApi accountApi = new AccountApi();

    @Step("Delete all book")
    public void deleteAllBook() {
        accountApi.getInfoAccount();
        given(requestSpecification)
                .header("Authorization", "Bearer " + loginResponseLombokModel.getToken())
                .queryParams("UserId", loginResponseLombokModel.getUserId())
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(responseSpecification204);
    }

    @Step("Get info about all books")
    private GetAllBooksResponseModel getAllBooks() {
        GetAllBooksResponseModel responseModel = given(requestSpecification)
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .spec(responseSpecification200)
                .extract().as(GetAllBooksResponseModel.class);
        sizeAllBooksCollection = responseModel.getBooks().size();
        allBooksIsbnTitle = new HashMap<>(sizeAllBooksCollection);
        for (Books books : responseModel.getBooks()) {
            allBooksIsbnTitle.put(books.getIsbn(), books.getTitle());
        }
        return responseModel;
    }

    @Step("Add {countOfBooks}  books")
    public void addBooks() {
        getAllBooks();
        int countOfBooks = new Random().nextInt(sizeAllBooksCollection + 1) + 1;
        HashSet<IsbnRequestModel> isbnSet = new HashSet<>(countOfBooks);
        IsbnRequestModel isbnRequestMode;
        int count = 0;
        for (String key : allBooksIsbnTitle.keySet()) {
            count++;
            if (count > countOfBooks) {
                break;
            }
            isbnRequestMode = new IsbnRequestModel(key);
            isbnSet.add(isbnRequestMode);
        }

        AddBooksRequestModel bookRequestModel = new AddBooksRequestModel();
        bookRequestModel.setUserId(loginResponseLombokModel.getUserId());
        bookRequestModel.setCollectionOfIsbns(isbnSet);

        given(requestSpecification)
                .header("Authorization", "Bearer " + loginResponseLombokModel.getToken())
                .body(bookRequestModel)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpecification201);
    }

    @Step("Replace one book")
    public CreateNewUserResponseModel replaceBookInAccount(String isbn) {

        DeleteReplaceBookRequestModel deleteReplaceBookRequestModel = new DeleteReplaceBookRequestModel();
        deleteReplaceBookRequestModel.setUserId(loginResponseLombokModel.getUserId());
        deleteReplaceBookRequestModel.setIsbn(isbn);

        return given(requestSpecification)
                .header("Authorization", "Bearer " + loginResponseLombokModel.getToken())
                .body(deleteReplaceBookRequestModel)
                .when()
                .put("/BookStore/v1/Books/" + isbn)//NEW ISBN
                .then()
                .spec(responseSpecification204)
                .extract().as(CreateNewUserResponseModel.class);
    }

    @Step("Get info about random book in all collection")
    public Books getInfoAboutBook() {
        getAllBooks();
        int bookNumber = new Random().nextInt(allBooksIsbnTitle.size() + 1) + 1;
        String isbn = allBooksIsbnTitle.keySet().toArray()[new Random().nextInt(bookNumber)].toString();

        Books book = given(requestSpecification)
                .queryParams("ISBN", isbn)
                .when()
                .get("/BookStore/v1/Book")
                .then()
                .spec(responseSpecification200)
                .extract().as(Books.class);
        randomBookTitle = book.getTitle();
        randomBookIsbn = book.getIsbn();
        return book;
    }

    @Step("Delete random book in account")
    public void deleteOneBook() {
        accountApi.getInfoAccount();
        int bookNumber = new Random().nextInt(sizeInProfileBooksCollection + 1) + 1;
        String isbn = "";
        int count = 0;
        for (String key : booksInProfileIsbnTitle.keySet()) {
            count++;
            if (count > bookNumber) {
                break;
            }
            isbn = key;
        }

        DeleteReplaceBookRequestModel deleteBookRequestModel = new DeleteReplaceBookRequestModel();
        deleteBookRequestModel.setUserId(loginResponseLombokModel.getUserId());
        deleteBookRequestModel.setIsbn(isbn);

        given(requestSpecification)
                .header("Authorization", "Bearer " + loginResponseLombokModel.getToken())
                .body(deleteBookRequestModel)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .spec(responseSpecification204);
    }
}