package com.example.myflashcardsapp;

import android.widget.EditText;
import android.widget.LinearLayout;

interface Command {
    public void execute();
}

class ConfirmCommand implements Command {

    EditFlashcardActivity editFlashcardActivity;


    public ConfirmCommand(EditFlashcardActivity efa) {
        editFlashcardActivity = efa;
    }

    @Override
    public void execute() {
        editFlashcardActivity.confirm();
    }
}
