package com.example.myflashcardsapp;

import com.example.myflashcardsapp.Flashcard;

import java.util.List;

interface FlashcardsIterator {
    public Flashcard getNext();
    public Boolean hasMore();

    public List<Flashcard> getFlashcards();
    public void setCorrect(Flashcard flashcard);
}

class FlashcardsRandomIterator implements FlashcardsIterator {

    private List flashcards;

    @Override
    public List getFlashcards() {
        return flashcards;
    }

    public FlashcardsRandomIterator(List fls) {
        flashcards = fls;
    }

    public Flashcard getNext() {

        while(flashcards.size() > 0) {
            int rand = (int) (Math.random() * flashcards.size());
            return (Flashcard) flashcards.get(rand);
        }

        return null;
    }

    public void setCorrect(Flashcard flashcard) {
        flashcards.remove(flashcard);
    }

    public Boolean hasMore() {
        return (flashcards.size() > 0);
    }
}
