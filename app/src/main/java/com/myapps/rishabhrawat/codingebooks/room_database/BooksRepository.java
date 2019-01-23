package com.myapps.rishabhrawat.codingebooks.room_database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

class BooksRepository {

    private BookDao mBookDao;
    private LiveData<List<BookEntity>> mAllooks;

    private static BookEntity specificbook;

    BooksRepository(Application application) {
        BooksRoomDatabase db = BooksRoomDatabase.getDatabase(application);
        mBookDao = db.bookDao();
        mAllooks = mBookDao.getAllBooks();
    }

    void insert(BookEntity bookEntity) {
        new InsertAsyncTask(mBookDao).execute(bookEntity);
    }

    void delete(BookEntity bookEntity) {

        new DeleteAsyncTask(mBookDao).execute(bookEntity);
    }

    void update(BookEntity bookEntity) {
        new UpdateAsyncTask(mBookDao).execute(bookEntity);
    }

    void getBook(String URL) {
        new GetBookAsyncTask(mBookDao).execute(URL);
    }

    void deleteAll() {
        new DeleteAllBooksAsyncTask(mBookDao).execute();
    }


    static class InsertAsyncTask extends AsyncTask<BookEntity, Void, Void> {
        BookDao bookDao;

        InsertAsyncTask(BookDao bookDao) {
            this.bookDao = bookDao;
        }

        @Override
        protected Void doInBackground(BookEntity... bookEntities) {
            bookDao.insert(bookEntities[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    static class DeleteAsyncTask extends AsyncTask<BookEntity, Void, Void> {
        BookDao bookDao;

        DeleteAsyncTask(BookDao bookDao) {
            this.bookDao = bookDao;
        }

        @Override
        protected Void doInBackground(BookEntity... bookEntities) {
            bookDao.delete(bookEntities[0]);
            return null;
        }

    }

    static class GetBookAsyncTask extends AsyncTask<String, Void, Void> {
        BookDao bookDao;

        GetBookAsyncTask(BookDao bookDao) {
            this.bookDao = bookDao;
        }

        @Override
        protected Void doInBackground(String... url) {
            specificbook = bookDao.getBook(url[0]);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<BookEntity, Void, Void> {
        BookDao bookDao;

        UpdateAsyncTask(BookDao bookDao) {
            this.bookDao = bookDao;
        }

        @Override
        protected Void doInBackground(BookEntity... bookEntities) {
            bookDao.update(bookEntities[0]);
            return null;
        }
    }

    static class DeleteAllBooksAsyncTask extends AsyncTask<Void, Void, Void> {
        BookDao bookDao;

        DeleteAllBooksAsyncTask(BookDao bookDao) {
            this.bookDao = bookDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            bookDao.deleteAll();
            return null;
        }
    }


    //return all books automatically
    LiveData<List<BookEntity>> getmAllooks() {
        return mAllooks;
    }
}

