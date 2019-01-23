package com.myapps.rishabhrawat.codingebooks.room_database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(BookEntity bookEntity);



    @Query("Select * from book_table where book_url==:URL")
    BookEntity getBook(String URL);

    @Delete
    void delete(BookEntity bookEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(BookEntity bookEntity);

    @Query("Delete from book_table")
    void deleteAll();

    @Query("SELECT * from book_table")
    LiveData<List<BookEntity>> getAllBooks();
}
