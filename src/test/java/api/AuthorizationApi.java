package api;

import models.lombok.GenerateTokenLoginRequestModel;
import models.lombok.LoginResponseModel;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.GeneralSpec.requestSpecification;
import static specs.GeneralSpec.responseSpecification200;

public class AuthorizationApi {

    public LoginResponseModel login() {
        GenerateTokenLoginRequestModel authData = new GenerateTokenLoginRequestModel();

        return step("Make Login", () ->
                given(requestSpecification)
                        .body(authData)
                        .when()
                        .post("/Account/v1/Login")
                        .then()
                        .spec(responseSpecification200)
                        .extract().as(LoginResponseModel.class));
    }
}