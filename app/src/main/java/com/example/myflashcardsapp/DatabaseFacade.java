package com.example.myflashcardsapp;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.myflashcardsapp.Flashcard;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DatabaseFacade {

    private static DatabaseFacade instance;

    private final int newFlashcards = 20;
    private static FlashcardsDbHelper dbHelper;

    private DatabaseFacade() {}

    public static DatabaseFacade getInstance() {
        if (instance == null) {
            instance = new DatabaseFacade();
            dbHelper = new FlashcardsDbHelper(MyApplication.getAppContext());
        }

        return instance;
    }

    public List getFlashcards() {
        //Flashcard [] flashcards = new Flashcard[100];
        //flashcards[0] = new Flashcard("apple", "jabłko", new String[]{"I like apple", "eating apple is healthy"}, new State1(flashcards[0]));
        //flashcards[1] = new Flashcard("car", "samochod", new String[]{"this car is nice"}, new State0(flashcards[1]));

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                FlashcardsContract.FlashcardsColumns.COLUMN_NAME_WORD,
                FlashcardsContract.FlashcardsColumns.COLUMN_NAME_TRANSLATION,
                FlashcardsContract.FlashcardsColumns.COLUMN_NAME_STATE
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = FlashcardsContract.FlashcardsColumns.COLUMN_NAME_STATE + " = ?";
        String[] selectionArgs = { "0" };

        // How you want the results sorted in the resulting Cursor
//        String sortOrder =
//                FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor cursor = db.query(
                FlashcardsContract.FlashcardsColumns.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null,               // The sort order
                "20"
        );

        List flashcards = new ArrayList<>();

        while(cursor.moveToNext()) {
            int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(FlashcardsContract.FlashcardsColumns._ID));
            int state = cursor.getInt(cursor.getColumnIndexOrThrow(FlashcardsContract.FlashcardsColumns.COLUMN_NAME_STATE));
            String word = cursor.getString(cursor.getColumnIndexOrThrow(FlashcardsContract.FlashcardsColumns.COLUMN_NAME_WORD));
            String translation = cursor.getString(cursor.getColumnIndexOrThrow(FlashcardsContract.FlashcardsColumns.COLUMN_NAME_TRANSLATION));
            Flashcard fl = new Flashcard(itemId, word, translation, getSentences(itemId), state);

            flashcards.add(fl);
        }
        cursor.close();

        TimeZone zone = TimeZone.getTimeZone("GMT+1");
        DateFormat format = new SimpleDateFormat("YYYY-MM-DD");// DateFormat.getDateTimeInstance();
        format.setTimeZone(zone);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        String date = format.format(calendar.getTime());


        Cursor cursor2 = db.query(
                FlashcardsContract.FlashcardsColumns.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                FlashcardsContract.FlashcardsColumns.COLUMN_NAME_DATE + " = ?",              // The columns for the WHERE clause
                new String[]{format.format(calendar.getTime())},          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order=
        );

        while(cursor2.moveToNext()) {
            int itemId = cursor2.getInt(cursor.getColumnIndexOrThrow(FlashcardsContract.FlashcardsColumns._ID));
            int state = cursor2.getInt(cursor.getColumnIndexOrThrow(FlashcardsContract.FlashcardsColumns.COLUMN_NAME_STATE));
            String word = cursor2.getString(cursor.getColumnIndexOrThrow(FlashcardsContract.FlashcardsColumns.COLUMN_NAME_WORD));
            String translation = cursor2.getString(cursor.getColumnIndexOrThrow(FlashcardsContract.FlashcardsColumns.COLUMN_NAME_TRANSLATION));
            Flashcard fl = new Flashcard(itemId, word, translation, getSentences(itemId), state);

            flashcards.add(fl);
        }
        cursor2.close();

        return flashcards;
    }

    private List getSentences(int flashcardId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                FlashcardsContract.SentencesColumns.COLUMN_NAME_SENTENCE,
                FlashcardsContract.SentencesColumns.COLUMN_NAME_TRANSLATION
        };

        String selection = FlashcardsContract.SentencesColumns.COLUMN_NAME_FLASHCARDID + " = ?";
        String[] selectionArgs = { "" + flashcardId };


        Cursor cursor = db.query(
                FlashcardsContract.SentencesColumns.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List sentences = new ArrayList<String>();
        while(cursor.moveToNext()) {
            String sentence = cursor.getString(cursor.getColumnIndexOrThrow(FlashcardsContract.SentencesColumns.COLUMN_NAME_SENTENCE));
            sentences.add(sentence);
        }
        cursor.close();

        return sentences;
    }

    public void resetFlashCards() {
        dbHelper.onDowngrade(dbHelper.getReadableDatabase(), 0, 0);
        HTMLUnitAdapter htmlUnitAdapter = new HTMLUnitAdapter();
        htmlUnitAdapter.addElementsFromFile();
    }

    public void addFlashcard(Flashcard [] flashcards) {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (Flashcard flashcard: flashcards) {
            if(flashcard == null)
                break;

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(FlashcardsContract.FlashcardsColumns.COLUMN_NAME_WORD, flashcard.word);
            values.put(FlashcardsContract.FlashcardsColumns.COLUMN_NAME_TRANSLATION, flashcard.translation);
            values.put(FlashcardsContract.FlashcardsColumns.COLUMN_NAME_STATE, 0);
            values.put(FlashcardsContract.FlashcardsColumns.COLUMN_NAME_DATE, 100);

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(FlashcardsContract.FlashcardsColumns.TABLE_NAME, null, values);

            addSentences(newRowId, flashcard.sentences);
        }

    }



    private void addSentences(long id, List sentences) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if(sentences == null)
            return;

        for(String sentence: (List<String>) (sentences)) {
            ContentValues sentValues = new ContentValues();
            sentValues.put(FlashcardsContract.SentencesColumns.COLUMN_NAME_SENTENCE, sentence);
            sentValues.put(FlashcardsContract.SentencesColumns.COLUMN_NAME_TRANSLATION, "\"\"");
            sentValues.put(FlashcardsContract.SentencesColumns.COLUMN_NAME_FLASHCARDID, id);

            long newSentenceRowId = db.insert(FlashcardsContract.SentencesColumns.TABLE_NAME, null, sentValues);
        }
    }

    private void removeSentences(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = FlashcardsContract.SentencesColumns.COLUMN_NAME_FLASHCARDID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { ""+id };
        // Issue SQL statement.
        int deletedRows = db.delete(FlashcardsContract.SentencesColumns.TABLE_NAME, selection, selectionArgs);
    }


    public void editFlashcard(Flashcard flashcard) {
        removeSentences(flashcard.id);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(FlashcardsContract.FlashcardsColumns.COLUMN_NAME_WORD, flashcard.word);
        values.put(FlashcardsContract.FlashcardsColumns.COLUMN_NAME_TRANSLATION, flashcard.translation);

        // Which row to update, based on the title
        String selection = FlashcardsContract.FlashcardsColumns._ID + " LIKE ?";
        String[] selectionArgs = { flashcard.id+"" };

        int count = db.update(
                FlashcardsContract.FlashcardsColumns.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        addSentences(flashcard.id, flashcard.sentences);
    }

    public void editStateAndDate(int id, int state) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();


        TimeZone zone = TimeZone.getTimeZone("GMT+1");
        DateFormat format = new SimpleDateFormat("YYYY-MM-DD");// DateFormat.getDateTimeInstance();
        format.setTimeZone(zone);

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, state);

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(FlashcardsContract.FlashcardsColumns.COLUMN_NAME_STATE, state);
        values.put(FlashcardsContract.FlashcardsColumns.COLUMN_NAME_DATE, format.format(calendar.getTime()));

        // Which row to update, based on the title
        String selection = FlashcardsContract.FlashcardsColumns._ID + " LIKE ?";
        String[] selectionArgs = { id+"" };

        //System.out.println(values + " " + selection + " " + selectionArgs);

        int count = db.update(
                FlashcardsContract.FlashcardsColumns.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        System.out.println(values + " " + selection + " " + selectionArgs + " " + count);
    }
}
