package com.example.myflashcardsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myflashcardsapp.DatabaseFacade;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;

public class LearningActivity extends AppCompatActivity {


    private DatabaseFacade databaseFacade;
    private List flashcards;

    private Flashcard current;

    private FlashcardsIterator flashcardsIterator;

    private FlashcardFileHelper flashcardFileHelper;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null)
            return;
        Gson gson = new Gson();
        Flashcard fl= gson.fromJson(data.getStringExtra("flashcard"), Flashcard.class);
        current.sentences = fl.sentences;
        current.word = fl.word;
        current.translation = fl.translation;

        renderFlashcard();

        //flashcardFileHelper.flashcards = flashcardsIterator.getFlashcards();

        StorageContext storageContext = new StorageContext();
        storageContext.setStrategy(new WriteStrategy());
        storageContext.executeStrategy(flashcardsIterator.getFlashcards(), this);
        //flashcardFileHelper.writeToStorage();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        databaseFacade = DatabaseFacade.getInstance();
        flashcardFileHelper = new FlashcardFileHelper(this);
        flashcards = flashcardFileHelper.getFlashcards();
        System.out.println(flashcards);

        flashcardsIterator = new FlashcardsRandomIterator(flashcards);

        renderNextFlashcard();
        //renderButtons(current.getButtons());
    }

    public void editFlashcard(View view) {

        Intent intent = new Intent(this, EditFlashcardActivity.class);
        intent.putExtra("isNew", false);
        intent.putExtra("id", current.id);
        intent.putExtra("word", current.word);
        intent.putExtra("translation", current.translation);
        intent.putExtra("sentences", current.sentences.toArray(new String[0]));
        startActivityForResult(intent, 100);
    }

    public void renderNextFlashcard() {
        current = flashcardsIterator.getNext();
        if (current == null) {
            renderEndScreen();
            return;
        } else {
            renderFlashcard();
        }


    }


    private void renderFlashcard() {
        //System.out.println(current.id + " " + current.state.getState());

//        current.renderFlashcard(findViewById(R.id.word), findViewById(R.id.translation),
//                findViewById(R.id.sentences), findViewById(R.id.options));

        ((TextView) findViewById(R.id.word)).setText(current.word);
        ((TextView) findViewById(R.id.translation)).setText(current.translation);

        ((LinearLayout) findViewById(R.id.sentences)).removeAllViews();

        ((TextView) findViewById(R.id.translation)).setVisibility(View.INVISIBLE);
        ((LinearLayout) findViewById(R.id.sentences)).setVisibility(View.INVISIBLE);

        for(String sent : (List<String>)current.sentences) {
            TextView tv = new TextView(this);
            tv.setText("- " + sent);
            ((LinearLayout) findViewById(R.id.sentences)).addView(tv);
        }


        Button discover = new Button(this);
        discover.setText("Odkryj");
        discover.setBackgroundColor(0xFF9E9E9E);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );
        discover.setLayoutParams(param);

        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) findViewById(R.id.translation)).setVisibility(View.VISIBLE);
                ((LinearLayout) findViewById(R.id.sentences)).setVisibility(View.VISIBLE);
                renderButtons(current.getButtons());
                //current.showFlashcard((Activity) MyApplication.getAppContext());
            }
        });

        ((LinearLayout)findViewById(R.id.options)).removeAllViews();
        ((LinearLayout)findViewById(R.id.options)).addView(discover);


    }

    public void renderEndScreen() {

        ViewGroup vg = (ViewGroup) findViewById(R.id.word).getParent();
        vg.removeAllViews();

        TextView tv = new TextView(this);
        tv.setText("To wszystko na dziś!");
        vg.addView(tv);
    }

    public void renderButtons(ButtonDescriber [] dcs) {
        LinearLayout layout = (findViewById(R.id.options));
        layout.removeAllViews();
        Button [] bts = new Button[dcs.length];

        for(int i = 0; i < dcs.length; i++) {
            Button bt = new Button(this);
            bt.setTextColor(0xFFFFFFFF);
            bt.setBackgroundColor(dcs[i].getColor());
            bt.setText(dcs[i].getLabel());

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );

            bt.setLayoutParams(param);

            int finalI = i;
            LearningActivity temp = this;
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String cl = "State" + dcs[finalI].getToState();
                        System.out.println(cl);
                        //Class st = Class.forName("State" + dcs[finalI].getToState());
                        //State newState = (State) st.newInstance();
                        int newState = dcs[finalI].getToState();
                        current.changeState(newState);

                        if(newState != 0) {
                            ((FlashcardsRandomIterator)flashcardsIterator).setCorrect(current);

                            DatabaseFacade.getInstance().editStateAndDate(current.id, newState);

                            //flashcardFileHelper.flashcards = flashcardsIterator.getFlashcards();
                            StorageContext storageContext = new StorageContext();
                            storageContext.setStrategy(new WriteStrategy());
                            storageContext.executeStrategy(flashcardsIterator.getFlashcards(), temp);
                        }


                        
                        renderNextFlashcard();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            layout.addView(bt);
        }
    }
}