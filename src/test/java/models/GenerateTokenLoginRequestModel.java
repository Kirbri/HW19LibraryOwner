package models;

import lombok.Data;
import tests.TestData;

@Data
public class GenerateTokenLoginRequestModel {
    String password,
            userName;

    public GenerateTokenLoginRequestModel() {
        this.password = TestData.getPassword();
        this.userName = TestData.getLogin();
    }
}