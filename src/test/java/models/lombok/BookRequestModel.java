package models.lombok;

import lombok.Data;

import java.util.HashSet;

@Data
public class BookRequestModel {

   // {"userId":"%s","collectionOfIsbns":[{"isbn":"%s"},{"isbn":"%s"}]}

    String userId;
    HashSet<IsbnRequestModel> collectionOfIsbns;
}