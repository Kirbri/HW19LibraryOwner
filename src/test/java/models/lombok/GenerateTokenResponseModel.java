package models.lombok;

import lombok.Data;

@Data
public class GenerateTokenResponseModel {
    String token,
            expires,
            status,
            result;
}