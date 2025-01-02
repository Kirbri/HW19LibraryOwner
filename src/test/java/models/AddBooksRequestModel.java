package models;

import lombok.Data;

import java.util.HashSet;

@Data
public class AddBooksRequestModel {

    String userId;
    HashSet<IsbnRequestModel> collectionOfIsbns;
}