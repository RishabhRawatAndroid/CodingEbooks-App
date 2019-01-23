package com.myapps.rishabhrawat.codingebooks.room_database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "book_table",indices = {@Index(value = "book_url",unique = true)})
public class BookEntity {


    @PrimaryKey(autoGenerate = true)
    private int id;
    private String book_name;
    @ColumnInfo(name = "book_url")
    private String book_url;
    private String book_description;
    private String book_href;

    public BookEntity(String book_name, String book_url, String book_description,String book_href) {
        this.book_name = book_name;
        this.book_url = book_url;
        this.book_description = book_description;
        this.book_href=book_href;
    }

    public String getBook_href() {
        return book_href;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getBook_url() {
        return book_url;
    }

    public String getBook_description() {
        return book_description;
    }
}
