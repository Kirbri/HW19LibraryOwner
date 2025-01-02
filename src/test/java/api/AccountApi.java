package api;

import io.qameta.allure.Step;
import models.*;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static specs.GeneralSpec.*;
import static tests.TestData.*;

public class AccountApi {

    @Step("Authorized account")
    public AuthorizedResponseModel authorizedAccount() {
        GenerateTokenLoginRequestModel authData = new GenerateTokenLoginRequestModel();

        return given(requestSpecification)
                .body(authData)
                .when()
                .post("/Account/v1/Authorized")
                .then()
                .spec(responseSpecification200)
                .extract().as(AuthorizedResponseModel.class);
    }

    @Step("Generate token")
    public GenerateTokenResponseModel generateTokenAccount() {
        GenerateTokenLoginRequestModel authData = new GenerateTokenLoginRequestModel();

        return given(requestSpecification)
                .body(authData)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .spec(responseSpecification200)
                .extract().as(GenerateTokenResponseModel.class);
    }

    @Step("Create new account")
    public CreateNewUserResponseModel createNewAccount(String login, String password) {
        GenerateTokenLoginRequestModel authData = new GenerateTokenLoginRequestModel();
        authData.setPassword(password);
        authData.setUserName(login);

        return given(requestSpecification)
                .body(authData)
                .when()
                .post("/Account/v1/User")
                .then()
                .spec(responseSpecification201)
                .extract().as(CreateNewUserResponseModel.class);
    }

    @Step("Delete account")
    public void deleteAccount() {
        given(requestSpecification)
                .header("Authorization", "Bearer " + loginResponseLombokModel.getToken())
                .when()
                .delete("/Account/v1/User/" + loginResponseLombokModel.getUserId())
                .then()
                .spec(responseSpecification204);
    }

    @Step("Get info about book in account")
    public GetOldUserResponseModel getInfoAccount() {

        GetOldUserResponseModel responseModel = given(requestSpecification)
                .header("Authorization", "Bearer " + loginResponseLombokModel.getToken())
                .when()
                .get("/Account/v1/User/" + loginResponseLombokModel.getUserId())
                .then()
                .spec(responseSpecification200)
                .extract().as(GetOldUserResponseModel.class);

        sizeInProfileBooksCollection = responseModel.getBooks().size();
        booksInProfileIsbnTitle = new HashMap<>(sizeInProfileBooksCollection);
        for (Books books : responseModel.getBooks()) {
            booksInProfileIsbnTitle.put(books.getIsbn(), books.getTitle());
        }

        return responseModel;
    }
}