package com.example.myflashcardsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

final class FlashcardsContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FlashcardsContract() {}

    /* Inner class that defines the table contents */

    public static class FlashcardsColumns implements BaseColumns {
        public static final String TABLE_NAME = "flashcards";
        public static final String COLUMN_NAME_WORD = "title";
        public static final String COLUMN_NAME_TRANSLATION = "translation";
        public static final String COLUMN_NAME_STATE = "state";
        public static final String COLUMN_NAME_DATE = "revisonDate";
    }

    public static class SentencesColumns implements BaseColumns {
        public static final String TABLE_NAME = "sentences";
        public static final String COLUMN_NAME_FLASHCARDID = "flashcardId";
        public static final String COLUMN_NAME_SENTENCE = "sentence";
        public static final String COLUMN_NAME_TRANSLATION = "translation";
    }
}

public class FlashcardsDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Flashcards.db";

    private static final String SQL_CREATE_ENTRIES_1 =
            "CREATE TABLE " + FlashcardsContract.FlashcardsColumns.TABLE_NAME + " (" +
                    FlashcardsContract.FlashcardsColumns._ID + " INTEGER PRIMARY KEY," +
                    FlashcardsContract.FlashcardsColumns.COLUMN_NAME_WORD + " TEXT," +
                    FlashcardsContract.FlashcardsColumns.COLUMN_NAME_TRANSLATION + " TEXT," +
                    FlashcardsContract.FlashcardsColumns.COLUMN_NAME_STATE + " INTEGER," +
                    FlashcardsContract.FlashcardsColumns.COLUMN_NAME_DATE + " DATE)";

    private static final String SQL_CREATE_ENTRIES_2 =
            "CREATE TABLE " + FlashcardsContract.SentencesColumns.TABLE_NAME + " (" +
                    FlashcardsContract.SentencesColumns._ID + " INTEGER PRIMARY KEY," +
                    FlashcardsContract.SentencesColumns.COLUMN_NAME_FLASHCARDID + " INTEGER," +
                    FlashcardsContract.SentencesColumns.COLUMN_NAME_SENTENCE + " TEXT," +
                    FlashcardsContract.SentencesColumns.COLUMN_NAME_TRANSLATION + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_1 =
            "DROP TABLE IF EXISTS " + FlashcardsContract.FlashcardsColumns.TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES_2 =
            "DROP TABLE IF EXISTS " + FlashcardsContract.SentencesColumns.TABLE_NAME;

    public FlashcardsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_1);
        db.execSQL(SQL_CREATE_ENTRIES_2);

        //SimpleStateFactory ssf = new SimpleStateFactory();
        //Flashcard [] flashcards = new Flashcard[2];
//        flashcards[0] = new Flashcard("apple", "jabłko", null,  ssf.createState(0));
//        flashcards[1] = new Flashcard("car", "samochod", null, ssf.createState(0));
//
//        DatabaseFacade.getInstance().addFlashcard(null, flashcards);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES_1);
        db.execSQL(SQL_DELETE_ENTRIES_2);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}