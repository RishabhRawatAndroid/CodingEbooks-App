package com.android.rishabhrawat.codingebooks.modelclasses;

public class CategoriesBooks {
    private String name;
    private Integer drawable_image;
    private String categories_url;

    public CategoriesBooks(String name, Integer drawable_image, String categories_url) {
        this.name = name;
        this.drawable_image = drawable_image;
        this.categories_url = categories_url;
    }

    public String getCategories_url() {
        return categories_url;
    }

    public void setCategories_url(String categories_url) {
        this.categories_url = categories_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDrawable_image() {
        return drawable_image;
    }

    public void setDrawable_image(Integer drawable_image) {
        this.drawable_image = drawable_image;
    }
}
