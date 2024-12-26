package models.lombok;

import lombok.Data;

import java.util.HashSet;

@Data
public class GetCreateNewUserResponseModel {
    String userId, username;
    HashSet<Books> books;
}