package com.example.myflashcardsapp;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

interface Sentence {
    public LinearLayout createSentenceItem(EditFlashcardActivity editFlashcardActivity);
}


class StandardSentence implements Sentence {
    private String sentence;

    public StandardSentence(String s) {
        sentence = s;
    }

    public LinearLayout createSentenceItem(EditFlashcardActivity editFlashcardActivity) {
        EditText editText = new EditText(editFlashcardActivity);
        editText.setText(sentence);

        Button bt = new Button(editFlashcardActivity);
        bt.setText("X");
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout el =  (LinearLayout) view.getParent().getParent();
                //el.removeView((View) view.getParent());
                editFlashcardActivity.removeSentence(el.indexOfChild((View)view.getParent()));
            }
        });

        LinearLayout la = new LinearLayout(editFlashcardActivity);

        la.addView(editText);
        la.addView(bt);

        return la;
    }
}