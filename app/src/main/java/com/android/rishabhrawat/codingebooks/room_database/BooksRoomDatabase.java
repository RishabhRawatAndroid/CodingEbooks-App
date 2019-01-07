package com.android.rishabhrawat.codingebooks.room_database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

//Singleton class only one object can be created at one time
@Database(entities = {BookEntity.class}, version = 1)
public abstract class BooksRoomDatabase extends RoomDatabase {

    public abstract BookDao bookDao();

    private static volatile BooksRoomDatabase INSTANCE;

    static BooksRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BooksRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BooksRoomDatabase.class, "books_database")
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //this is callback if we want some operation at the time of database create and open
    private static RoomDatabase.Callback callback=new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };


}
