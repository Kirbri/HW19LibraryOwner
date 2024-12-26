package com.demoqa.tests;

import lombok.Getter;
import models.lombok.LoginResponseModel;

import java.util.Map;

public class TestData {

    @Getter
    public static String login = System.getProperty("login"),
            password = System.getProperty("password");

    public static LoginResponseModel loginResponseLombokModel;

    public static Map<String, String> allBooksIsbnTitle;

    public static Map<String, String> booksInProfileIsbnTitle;

    public static int sizeAllBooksCollection;

    public static int sizeInProfileBooksCollection;
}