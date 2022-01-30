package com.example.myflashcardsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.example.myflashcardsapp.Flashcard;
import com.example.myflashcardsapp.DatabaseFacade;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private DatabaseFacade databaseFacade;
    private List<Flashcard> flashcards;

    private FlashcardFileHelper flashcardFileHelper;

    @Override
    protected void onResume() {
        System.out.println("on resume");

        showFlashcardNumbers();
        super.onResume();

//        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
//        Date date = new Date();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.getTimeZone("America/New_York");
//
//        TimeZone zone = TimeZone.getTimeZone("America/New_York");
//        dateFormat.setTimeZone(zone);

        TimeZone zone = TimeZone.getTimeZone("GMT+1");
        DateFormat format = new SimpleDateFormat("YYYY-MM-DD");// DateFormat.getDateTimeInstance();
        format.setTimeZone(zone);

        System.out.println(format.format(new Date()));
        //System.out.println(dateFormat.format(calendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseFacade = DatabaseFacade.getInstance();
        flashcardFileHelper = new FlashcardFileHelper(this);


//        Flashcard [] temp = new Flashcard[2];
//        temp[0] = new Flashcard(-1, "apple", "jabłko", Arrays.asList(new String[] { "aaa", "bbb", "ccc" }),  new State0());
//        temp[1] = new Flashcard(-1, "car", "samochod", Arrays.asList(new String[] { "11", "22" }), new State0());
////
//        DatabaseFacade.getInstance().addFlashcard(null, temp);

        flashcards = flashcardFileHelper.getFlashcards();
        System.out.println(flashcards);
//        WorkRequest uploadWorkRequest =
//                new OneTimeWorkRequest.Builder(FlashcardWorker.class)
//                        .build();
//
//        WorkManager
//                .getInstance(myContext)
//                .enqueue(uploadWorkRequest);
//
        showFlashcardNumbers();
    }


    private void showFlashcardNumbers() {
//        List<Flashcard> flashcards;
//        flashcards = databaseFacade.getFlashcards();

        int counterNew = flashcards.size();
        int counterReview = 0;

        for(int i = 0; i < flashcards.size(); i++) {
            System.out.println(flashcards.get(i).state);
            if(flashcards.get(i).state != 0) {
                counterNew--;
                counterReview++;
            }

            System.out.println(flashcards.get(i).state);
        }

        ((TextView)findViewById(R.id.newCounter)).setText("" + counterNew);
        ((TextView)findViewById(R.id.reviewCounter)).setText("" + counterReview+"");
    }


    public void goToLearn(View view) {
        Intent intent = new Intent(this, LearningActivity.class);
        startActivity(intent);
    }

    public void goToAddFlashcard(View view) {
        Intent intent = new Intent(this, EditFlashcardActivity.class);
        intent.putExtra("isNew", true);
        startActivity(intent);
    }

    public void resetFlashcards(View view) {
        databaseFacade.resetFlashCards();
    }




    public void nextDayDebug(View view) {

        System.out.println(flashcardFileHelper.getFlashcards());

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(getString(R.string.last_saved_date), "");
        editor.apply();

        //System.out.println(flashcardFileHelper.getFlashcards());
        flashcards = databaseFacade.getFlashcards();

//        HTMLUnitAdapter htmlUnitAdapter = new HTMLUnitAdapter();
//        htmlUnitAdapter.addElementsFromFile();
    }
}