package api;

import models.lombok.*;

import java.util.*;

import static com.demoqa.tests.TestData.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.GeneralSpec.*;

public class BookStoreApi {

    public void deleteAllBook() {
        step("Delete all book", () -> given(requestSpecification)
                .header("Authorization", "Bearer " + loginResponseLombokModel.getToken())
                .queryParams("UserId", loginResponseLombokModel.getUserId())
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(responseSpecification204));
    }

    public GetAllBooksResponseModel getAllBooks() {
        GetAllBooksResponseModel responseModel = step("Get info about all books", () -> given(requestSpecification)
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .spec(responseSpecification200)
                .extract().as(GetAllBooksResponseModel.class));
        sizeAllBooksCollection = responseModel.getBooks().size();
        allBooksIsbnTitle = new HashMap<>(sizeAllBooksCollection);
        for (Books books : responseModel.getBooks()) {
            System.out.println(books.getIsbn());
            allBooksIsbnTitle.put(books.getIsbn(), books.getTitle());
        }
        System.out.println(allBooksIsbnTitle);
        return responseModel;
    }

    public void addBooks() {
        int countOfBooks = new Random().nextInt(sizeAllBooksCollection + 1) + 1;
        HashSet<IsbnRequestModel> isbnSet = new HashSet<>(countOfBooks);
        IsbnRequestModel isbnRequestMode;
        System.out.println(countOfBooks);
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

        step("Add " + countOfBooks + "books", () -> given(requestSpecification)
                .header("Authorization", "Bearer " + loginResponseLombokModel.getToken())
                .body(bookRequestModel)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpecification201));
    }

    public GetCreateNewUserResponseModel replaceBookInAccount(String isbn) {

        DeleteReplaceBookRequestModel deleteReplaceBookRequestModel = new DeleteReplaceBookRequestModel();
        deleteReplaceBookRequestModel.setUserId(loginResponseLombokModel.getUserId());
        deleteReplaceBookRequestModel.setIsbn(isbn);

        return step("Replace one book", () -> given(requestSpecification)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + loginResponseLombokModel.getToken())
                .body(deleteReplaceBookRequestModel)
                .when()
                .put("/BookStore/v1/Books/" + isbn)//NEW ISBN
                .then()
                .spec(responseSpecification204)
                .extract().as(GetCreateNewUserResponseModel.class));
    }


    public Books getInfoAboutBook() {
        int bookNumber = new Random().nextInt(allBooksIsbnTitle.size() + 1) + 1;
        String isbn = allBooksIsbnTitle.keySet().toArray()[new Random().nextInt(bookNumber)].toString();

        return step("Get info about random book in all collection", () -> given(requestSpecification)
                .queryParams("ISBN", isbn)
                .when()
                .get("/BookStore/v1/Book")
                .then()
                .spec(responseSpecification200)
                .extract().as(Books.class));
    }

    public void deleteOneBook() {
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

        step("Delete random book in account", () -> given(requestSpecification)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + loginResponseLombokModel.getToken())
                .body(deleteBookRequestModel)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .spec(responseSpecification204));
    }
}