package com.example.myflashcardsapp;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

interface StorageStrategy {
    public void execute(List flashcards, Activity context, final String filename);
}

class StorageContext {




    private final String filename = "flashcards";

    private StorageStrategy strategy;

    public void setStrategy(StorageStrategy s) {
        strategy = s;
    }

    public void executeStrategy(List flashcards, Activity context) {
        strategy.execute(flashcards, context, filename);
    }
}



class WriteStrategy implements StorageStrategy {
    @Override
    public void execute(List flashcards, Activity context, final String filename) {
        String json = new Gson().toJson(flashcards);

        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(json.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println(json);
        System.out.println((List<Flashcard>) new Gson().fromJson(json, List.class));
    }
}




class ReadStrategy implements StorageStrategy {
    @Override
    public void execute(List flashcards, Activity context, final String filename) {
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);

        String json = "";
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        }


        //flashcards = new ArrayList<Flashcard>();
//

        try {
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(json).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                flashcards.add(gson.fromJson(jsonElement, Flashcard.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}