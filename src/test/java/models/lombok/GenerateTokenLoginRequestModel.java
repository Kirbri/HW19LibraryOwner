package models.lombok;

import com.demoqa.tests.TestData;
import lombok.Data;

@Data
public class GenerateTokenLoginRequestModel {
    String password,
            userName;

    public GenerateTokenLoginRequestModel() {
        this.password = TestData.getPassword();
        this.userName = TestData.getLogin();
    }
}