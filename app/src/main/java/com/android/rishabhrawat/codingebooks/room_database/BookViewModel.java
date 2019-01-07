package com.android.rishabhrawat.codingebooks.room_database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class BookViewModel extends AndroidViewModel {

    private BooksRepository mRepository;

    private LiveData<List<BookEntity>> mAllBooks;

    public BookViewModel(Application application) {
        super(application);
        mRepository = new BooksRepository(application);
        mAllBooks = mRepository.getmAllooks();
    }

    public LiveData<List<BookEntity>> getAllBooks() {
        return mAllBooks;
    }

    public void insert(BookEntity book) {
        mRepository.insert(book);
    }

    public void update(BookEntity book) {
        mRepository.update(book);
    }

    public void delete(BookEntity book) {
        mRepository.delete(book);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void getBook(String url) {
        mRepository.getBook(url);
    }
}

