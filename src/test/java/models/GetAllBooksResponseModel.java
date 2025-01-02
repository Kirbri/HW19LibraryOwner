package models;

import lombok.Data;

import java.util.HashSet;

@Data
public class GetAllBooksResponseModel {
    HashSet<Books> books;
}