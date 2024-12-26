package api;

import models.lombok.*;

import java.util.HashMap;

import static com.demoqa.tests.TestData.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.GeneralSpec.*;

public class AccountApi {

    public AuthorizedResponseModel authorizedAccount() {
        GenerateTokenLoginRequestModel authData = new GenerateTokenLoginRequestModel();

        return step("Authorized account", () ->
                given(requestSpecification)
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .body(authData)
                        .when()
                        .post("/Account/v1/Authorized")
                        .then()
                        .spec(responseSpecification200)
                        .extract().as(AuthorizedResponseModel.class));
    }

    public GenerateTokenResponseModel generateTokenAccount() {
        GenerateTokenLoginRequestModel authData = new GenerateTokenLoginRequestModel();

        return step("Generate token", () ->
                given(requestSpecification)
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .body(authData)
                        .when()
                        .post("/Account/v1/GenerateToken")
                        .then()
                        .spec(responseSpecification200)
                        .extract().as(GenerateTokenResponseModel.class));
    }

    public GetCreateNewUserResponseModel createNewAccount(String login, String password) {
        GenerateTokenLoginRequestModel authData = new GenerateTokenLoginRequestModel();
        authData.setPassword(password);
        authData.setUserName(login);

        return step("Create new account", () ->
                given(requestSpecification)
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .body(authData)
                        .when()
                        .post("/Account/v1/User")
                        .then()
                        .spec(responseSpecification201)
                        .extract().as(GetCreateNewUserResponseModel.class));
    }

    public DeleteAccountRequestModel deleteAccount() {

        return step("Delete account", () ->
                given(requestSpecification)
                        .header("Authorization", "Bearer " + loginResponseLombokModel.getToken())
                        .header("accept", "application/json")
                        .when()
                        .delete("/Account/v1/User/" + loginResponseLombokModel.getUserId())
                        .then()
                        .spec(responseSpecification200)
                        .extract().as(DeleteAccountRequestModel.class));
    }

    public GetCreateNewUserResponseModel getInfoAccount() {

        GetCreateNewUserResponseModel responseModel = step("Get info about book in account", () ->
                given(requestSpecification)
                        .header("Authorization", "Bearer " + loginResponseLombokModel.getToken())
                        .header("accept", "application/json")
                        .when()
                        .get("/Account/v1/User/" + loginResponseLombokModel.getUserId())
                        .then()
                        .spec(responseSpecification200)
                        .extract().as(GetCreateNewUserResponseModel.class));

        sizeInProfileBooksCollection = responseModel.getBooks().size();
        booksInProfileIsbnTitle = new HashMap<>(sizeInProfileBooksCollection);
        for (Books books : responseModel.getBooks()) {
            System.out.println(books.getIsbn());
            booksInProfileIsbnTitle.put(books.getIsbn(), books.getTitle());
        }

        return responseModel;
    }
}