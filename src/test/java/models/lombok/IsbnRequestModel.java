package models.lombok;

import lombok.Data;

@Data
public class IsbnRequestModel {
    String isbn;

    public IsbnRequestModel(String isbn) {
        this.isbn = isbn;
    }
}