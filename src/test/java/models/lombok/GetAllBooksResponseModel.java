package models.lombok;

import lombok.Data;

import java.util.HashSet;

@Data
public class GetAllBooksResponseModel {
    HashSet<Books> books;
}