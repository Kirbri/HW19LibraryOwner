package api;

import io.qameta.allure.Step;
import models.GenerateTokenLoginRequestModel;
import models.LoginResponseModel;

import static io.restassured.RestAssured.given;
import static specs.GeneralSpec.requestSpecification;
import static specs.GeneralSpec.responseSpecification200;

public class AuthorizationApi {

    @Step("Make Login")
    public LoginResponseModel login() {
        GenerateTokenLoginRequestModel authData = new GenerateTokenLoginRequestModel();

        return given(requestSpecification)
                .body(authData)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpecification200)
                .extract().as(LoginResponseModel.class);
    }
}