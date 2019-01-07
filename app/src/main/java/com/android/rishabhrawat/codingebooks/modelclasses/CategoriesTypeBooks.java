package com.android.rishabhrawat.codingebooks.modelclasses;

import java.util.ArrayList;

public class CategoriesTypeBooks {

    private String bookcategoriesname;
    private ArrayList<CategoriesBooks> categoriesBooksArrayList;

    public CategoriesTypeBooks(String bookcategoriesname, ArrayList<CategoriesBooks> categoriesBooksArrayList) {
        this.bookcategoriesname = bookcategoriesname;
        this.categoriesBooksArrayList = categoriesBooksArrayList;
    }

    public String getBookcategoriesname() {
        return bookcategoriesname;
    }

    public void setBookcategoriesname(String bookcategoriesname) {
        this.bookcategoriesname = bookcategoriesname;
    }

    public ArrayList<CategoriesBooks> getCategoriesBooksArrayList() {
        return categoriesBooksArrayList;
    }

    public void setCategoriesBooksArrayList(ArrayList<CategoriesBooks> categoriesBooksArrayList) {
        this.categoriesBooksArrayList = categoriesBooksArrayList;
    }
}
