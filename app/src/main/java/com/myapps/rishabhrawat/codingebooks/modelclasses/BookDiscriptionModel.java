package com.myapps.rishabhrawat.codingebooks.modelclasses;

public class BookDiscriptionModel {
    String author;
    String isbn;
    String year;
    String pages;
    String language;
    String pdf_size;
    String format;
    String category;
    String pdf_url;
    String book_description;

    public String getPdf_url() {
        return pdf_url;
    }

    public void setPdf_url(String pdf_url) {
        this.pdf_url = pdf_url;
    }

    public String getBook_description() {
        return book_description;
    }

    public void setBook_description(String book_description) {
        this.book_description = book_description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }


    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPdf_size() {
        return pdf_size;
    }

    public void setPdf_size(String pdf_size) {
        this.pdf_size = pdf_size;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
