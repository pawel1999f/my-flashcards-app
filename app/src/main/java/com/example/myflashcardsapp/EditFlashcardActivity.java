package com.example.myflashcardsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EditFlashcardActivity extends AppCompatActivity {

    private int id;
    private Boolean isNew;
    private String word;
    private String translation;
    private LinkedList sentences;

    public int getId() {
        return id;
    }

    public Boolean getNew() {
        return isNew;
    }

    public String getTranslation() {
        return translation;
    }

    public String getWord() {
        return word;
    }

    public LinkedList getSentences() {
        return sentences;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flashcard);

        Intent intent = this.getIntent();
        isNew = intent.getBooleanExtra("isNew", false);
        if(isNew) {
            sentences = new LinkedList();
            return;
        }

        id = intent.getIntExtra("id", -1);
        word = intent.getStringExtra("word");
        translation = intent.getStringExtra("translation");
        sentences = new LinkedList<String>(Arrays.asList(intent.getStringArrayExtra("sentences")));

        //sentences.remove(0);
//
        System.out.println(id);
        System.out.println(isNew);
        System.out.println(word);
        System.out.println(translation);
        System.out.println(sentences);


        loadFlashcard();
    }

    //metoda wytworcza
//    private Sentence createSentence(String s) {
//        return new StandardSentence(s);
//    }

    public void loadFlashcard() {
        ((EditText)findViewById(R.id.editWord)).setText(word);
        ((EditText)findViewById(R.id.editTranslation)).setText(translation);
        LinearLayout editLayout = (LinearLayout) findViewById(R.id.editSentences);

        for(int i = 0; i < sentences.size(); i++) {
            LinearLayout la = (new StandardSentence((String)sentences.get(i))).createSentenceItem(this);
            //la.setTag(i);

            editLayout.addView(la);
        }
    }

    public void removeSentence(int index) {
        if(sentences.size() < 1)
            return;

        //System.out.println("aaaaaaaaaaaaaaaa" + sentences.size());
        //System.out.println("aaaaaaaaaaaaaaaa" + sentences);
        sentences.remove(index);

        LinearLayout editLayout = (LinearLayout) findViewById(R.id.editSentences);
        editLayout.removeView((LinearLayout)editLayout.getChildAt(index));


//        String [] newSents = new String[sentences.length-1];
//        for(int i = 0; i < index; i++)
//            newSents[i] = sentences[i];
//        for(int i = index; i < sentences.length-1; i++)
//            newSents[i] = sentences[i+1];
//
//        sentences = newSents;
    }

    public void confirm() {
        LinearLayout editLayout = (LinearLayout) findViewById(R.id.editSentences);

        word = ((EditText)findViewById(R.id.editWord)).getText().toString();
        if(word == "null")
            word = "";

        translation = ((EditText)findViewById(R.id.editTranslation)).getText().toString();
        if(translation == "null")
            translation = "";

        //sentences = new List<String>();
        for(int i = 0; i < editLayout.getChildCount(); i++) {

            String item = ((EditText)((LinearLayout)editLayout.getChildAt(i)).getChildAt(0)).getText().toString();
            sentences.set(i, item);
        }

        System.out.println("word" + word);
        System.out.println("translation" + translation);
        System.out.println(sentences);

        Handler handler1 = new HandlerEmpty();
        Handler handler2 = new HandlerNew();
        Handler handler3 = new HandlerEdit();

        handler1.setNext(handler2);
        handler2.setNext(handler3);

        handler1.confirm(this);
    }

    public void confirm_click(View view) {
        ConfirmCommand cc = new ConfirmCommand(this);
        cc.execute();
    }

    public void addSentence(View view) {
//        String [] newSents = new String[sentences.length+1];
//        sentences = newSents;
//        newSents[newSents.length-1] = "";
        sentences.add("");

        //EditText editText = new EditText(this);

        LinearLayout la = (new StandardSentence("")).createSentenceItem(this);
        //la.setTag(sentences.size()-1);

        LinearLayout editLayout = (LinearLayout) findViewById(R.id.editSentences);
        editLayout.addView(la);
    }
}