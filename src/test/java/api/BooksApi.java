package api;

import models.lombok.BookRequestModel;
import models.lombok.LoginResponseModel;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static specs.GeneralSpec.responseSpecification201;
import static specs.GeneralSpec.responseSpecification204;

public class BooksApi {

    public void deleteAllBook(LoginResponseModel loginResponseLombokModel) {
        step("Delete all book", () -> given()
                .log().all()
                .contentType(JSON)
                .header("Authorization", "Bearer " + loginResponseLombokModel.getToken())
                .queryParams("UserId", loginResponseLombokModel.getUserId())
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(responseSpecification204));
    }

    public void addBooks(LoginResponseModel loginResponseLombokModel, BookRequestModel bookRequestLombokModel) {
        step("Add book", () -> given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .header("Authorization", "Bearer " + loginResponseLombokModel.getToken())
                .body(bookRequestLombokModel)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpecification201));
    }
}