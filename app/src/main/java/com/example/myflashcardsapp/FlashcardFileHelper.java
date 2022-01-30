package com.example.myflashcardsapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class FlashcardFileHelper {

    private Activity context;
    public List<Flashcard> flashcards;

    public FlashcardFileHelper(Activity con) {
        context = con;
    }

    public List<Flashcard> getFlashcards() {
        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);


        String lastSaved = sharedPref.getString(context.getString(R.string.last_saved_date), "");


        TimeZone zone = TimeZone.getTimeZone("GMT+1");
        DateFormat format = new SimpleDateFormat("YYYY-MM-DD");// DateFormat.getDateTimeInstance();
        format.setTimeZone(zone);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        String date = format.format(calendar.getTime());
        System.out.println(lastSaved + " " + date);

        StorageContext storageContext = new StorageContext();

        if(!lastSaved.equals(date)) {

            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putString(context.getString(R.string.last_saved_date), date);
            editor.apply();


            flashcards = DatabaseFacade.getInstance().getFlashcards();
            //writeToStorage();
            storageContext.setStrategy(new WriteStrategy());
        } else {
            //getFromStorage();


            flashcards = new ArrayList<Flashcard>();
            storageContext.setStrategy(new ReadStrategy());
        }
        storageContext.executeStrategy(flashcards, context);
        return flashcards;
    }


//    public void writeToStorage() {
//        String json = new Gson().toJson(flashcards);
//
//        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
//            fos.write(json.getBytes());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        System.out.println(json);
//        System.out.println((List<Flashcard>) new Gson().fromJson(json, List.class));
//    }
//
//    private void getFromStorage() {
//
//        FileInputStream fis = null;
//        try {
//            fis = context.openFileInput(filename);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        InputStreamReader inputStreamReader =
//                new InputStreamReader(fis, StandardCharsets.UTF_8);
//
//        String json = "";
//        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
//            String line = reader.readLine();
//            while (line != null) {
//                json += line;
//                line = reader.readLine();
//            }
//        } catch (IOException e) {
//            // Error occurred when opening raw file for reading.
//        }
//
//
//        flashcards = new ArrayList<Flashcard>();
//        //TypeToken<List<Flashcard>> list = new TypeToken<List<Flashcard>>() {};
//
//        //flashcards = new Gson().fromJson(json, List.class);
//
//        //List<Flashcard> list = new ArrayList<Flashcard>();
//
//
//
//
////        try {
////            Gson gson = new Gson();
////            JsonArray arry = new JsonParser().parse(json).getAsJsonArray();
////            for (JsonElement jsonElement : arry) {
////
////                GsonBuilder gsonBuilder = new GsonBuilder();
////                //gsonBuilder.registerTypeAdapter(Flashcard.class, new FlashcardCreator());
////                Gson gsonn = gsonBuilder.create();
////                Flashcard fl = gsonn.fromJson(jsonElement.getAsString(), Flashcard.class);
////                System.out.println("xxxxxxxxx" + fl);
////                flashcards.add(fl);
////
//
//                try {
//                    Gson gson = new Gson();
//                    JsonArray arry = new JsonParser().parse(json).getAsJsonArray();
//                    for (JsonElement jsonElement : arry) {
//                        flashcards.add(gson.fromJson(jsonElement, Flashcard.class));
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//
//
//
//
////        JSONArray arr = null;
////        try {
////            arr = new JSONArray(json);
////
////            for (int i = 0; i < arr.length(); i++) { // Walk through the Array.
////                JSONObject obj = arr.getJSONObject(i);
////                flashcards.add(obj);
////            }
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
//    }
}


//// CourseCreator class
//class FlashcardCreator implements InstanceCreator {
//    @Override
//    public Flashcard createInstance(Type type) {
//        Flashcard fl = new Flashcard(new State0());
//        return fl;
//    }
//}