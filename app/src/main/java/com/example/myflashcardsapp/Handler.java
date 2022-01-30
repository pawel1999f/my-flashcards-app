package com.example.myflashcardsapp;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

interface Handler {

    public void confirm(EditFlashcardActivity editFlashcardActivity);
    public void setNext(Handler next);
}

abstract class BaseHandler implements Handler {

    protected Handler next;

    public void setNext(Handler next) {
        this.next = next;
    }

    public void confirm(EditFlashcardActivity editFlashcardActivity) {
        if(next != null)
            next.confirm(editFlashcardActivity);
    }
}

class HandlerEmpty extends BaseHandler {

    @Override
    public void confirm(EditFlashcardActivity editFlashcardActivity) {
        System.out.println("word" + editFlashcardActivity.getWord());
        System.out.println("translation" + editFlashcardActivity.getTranslation());

        if(editFlashcardActivity.getTranslation().equals("") || editFlashcardActivity.getWord().equals("")) {
            Context context = editFlashcardActivity;
            CharSequence text = "Word and translation cannot be empty";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else {
            super.confirm(editFlashcardActivity);
        }
    }
}


class HandlerNew extends BaseHandler {

    @Override
    public void confirm(EditFlashcardActivity editFlashcardActivity) {

        if(editFlashcardActivity.getNew() == true) {
            Flashcard flashcard = new Flashcard(editFlashcardActivity.getId(), editFlashcardActivity.getWord(),
                    editFlashcardActivity.getTranslation(), (List)editFlashcardActivity.getSentences(), 0);


            DatabaseFacade.getInstance().addFlashcard(new Flashcard[]{flashcard});
            editFlashcardActivity.finish();
        }
        else {
            super.confirm(editFlashcardActivity);
        }
    }
}

class HandlerEdit extends BaseHandler {

    @Override
    public void confirm(EditFlashcardActivity editFlashcardActivity) {
        Flashcard flashcard = new Flashcard(editFlashcardActivity.getId(), editFlashcardActivity.getWord(),
                editFlashcardActivity.getTranslation(), (List)editFlashcardActivity.getSentences(), -1);

        DatabaseFacade.getInstance().editFlashcard(flashcard);

        Gson gson = new Gson();
        String json = gson.toJson(flashcard);
        Intent intent = new Intent();
        intent.putExtra("flashcard", json);

        editFlashcardActivity.setResult(100, intent);
        editFlashcardActivity.finish();
    }
}
