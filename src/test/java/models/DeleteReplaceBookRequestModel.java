package models;

import lombok.Data;

@Data
public class DeleteReplaceBookRequestModel {
    String isbn, userId;
}