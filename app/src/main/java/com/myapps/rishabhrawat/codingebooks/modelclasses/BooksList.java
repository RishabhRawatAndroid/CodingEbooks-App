package com.myapps.rishabhrawat.codingebooks.modelclasses;

import java.util.ArrayList;

public class BooksList {
    private static ArrayList<Books> booksArrayList;
    private static int position;

    public static int getPosition() {
        return position;
    }

    public static void setPosition(int position) {
        BooksList.position = position;
    }

    public  static ArrayList<Books> getBooksArrayList() {
        return booksArrayList;
    }


    public static void setBooksArrayList(ArrayList<Books> booksArrayList) {
        BooksList.booksArrayList = booksArrayList;
    }
}
