package com.example.myflashcardsapp;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


//interface Component {
//    public
//}

//interface FlashcardInterface {
//    public Sentence createSentence(String s);
//}


public class Flashcard {

    public int id;
    public String word;
    public int state;
    public String translation;
    public List sentences;


    public Flashcard(int st) {
        state = st;
    }

    public Flashcard(int idd, String w, String t, List sents, int st) {
        id = idd;
        word = w;
        state = st;
        translation = t;
        sentences = sents;
    }

    //metoda wytworcza
    public Sentence createSentence(String s) {
        return new StandardSentence(s);
    }

    public void showFlashcard(Activity activity) {
        ((TextView) activity.findViewById(R.id.translation)).setVisibility(View.VISIBLE);
        //((LinearLayout) activity.findViewById(R.id.sentences)).setVisibility(View.VISIBLE);
        ((LearningActivity) activity).renderButtons(getButtons());
    }

    public void changeState(int st) {
        this.state = st;
    }

//    public void ignite() {
//        state.addButtons();
//    }

    public ButtonDescriber[] getButtons() {
        return State.getButtons(state);
    }
}
