package com.example.myflashcardsapp;

final class State {


    static ButtonDescriber[] getButtons(int s) {

        ButtonDescriber [] dcs;

        switch (s) {

            case 0:
                dcs = new ButtonDescriber[2];
                dcs[0] = new ButtonDescriber("Źle", 0, 0xFFF44336);
                dcs[1] = new ButtonDescriber("Dobrze", 1, 0xFF4CAF50);
                break;

            case 1:
                dcs = new ButtonDescriber[3];
                dcs[0] = new ButtonDescriber("Źle", 0, 0xFFF44336);
                dcs[1] = new ButtonDescriber("Trudno", 1, 0xFF2196F3);
                dcs[2] = new ButtonDescriber("Latwo", 2, 0xFF4CAF50);
                break;

            case 2:
                dcs = new ButtonDescriber[3];
                dcs[0] = new ButtonDescriber("Źle", 0, 0xFFF44336);
                dcs[1] = new ButtonDescriber("Trudno", 2, 0xFF2196F3);
                dcs[2] = new ButtonDescriber("Latwo", 3, 0xFF4CAF50);
                break;

            case 3:
                dcs = new ButtonDescriber[3];
                dcs[0] = new ButtonDescriber("Źle", 0, 0xFFF44336);
                dcs[1] = new ButtonDescriber("Trudno", 3, 0xFF2196F3);
                dcs[2] = new ButtonDescriber("Latwo", 4, 0xFF4CAF50);
                break;

            default:
                dcs = new ButtonDescriber[2];
                dcs[0] = new ButtonDescriber("Źle", 0, 0xFFF44336);
                dcs[1] = new ButtonDescriber("Dobrze", 1, 0xFF4CAF50);
        }

        return dcs;
    }


    static int getDays(int s) {

        switch (s) {

            case 0:
                return 0;

            case 1:
                return 1;

            case 2:
                return 2;

            case 3:
                return 3;

            default:
                return 0;
        }



    }
}

//interface State {
//    final int state = 0;
//
//    public int getState();
//    public int getDays();
//    public ButtonDescriber [] getButtons();
//}
//
//abstract class StandardState implements State {
//
//    protected ButtonDescriber[] dcs;
//
//
//    protected int days;
//
//
//    static State createState(int st) {
//        switch (st) {
//            case 0:
//                return new State0();
//            case 1:
//                return new State1();
//        }
//        return new State0();
//    }
//
//    @Override
//    public int getState() {
//        return state;
//    }
//
//    public int getDays() {
//        return days;
//    }
//
//    @Override
//    public ButtonDescriber[] getButtons() {
//        return dcs;
//    }
//}
//
//class State0 extends StandardState {
//
//    public State0() {
//
//        ButtonDescriber [] dcs = new ButtonDescriber[2];
//        dcs[0] = new ButtonDescriber("Źle", 0, 0xFFF44336);
//        dcs[1] = new ButtonDescriber("Dobrze", 1, 0xFF4CAF50);
//
//        this.dcs = dcs;
//        this.days = 1;
//    }
//}
//
//class State1 extends StandardState {
//
//    public State1() {
//
//        ButtonDescriber[] dcs = new ButtonDescriber[3];
//        dcs[0] = new ButtonDescriber("Źle", 0, 0xFFF44336);
//        dcs[1] = new ButtonDescriber("Trudno", 1, 0xFF2196F3);
//        dcs[2] = new ButtonDescriber("Latwo", 2, 0xFF4CAF50);
//
//        this.dcs = dcs;
//        this.days = 2;
//    }
//}
//--------------------------------------------------------------------------
//package com.example.myflashcardsapp;
//
//import android.view.View;
//import android.widget.Button;
//
//
//interface State {
//
//    public ButtonDescriber[] getButtons();
//}
//
//abstract class FlashcardState implements State {
//
//    protected Flashcard flashcard; //context
//
//    public FlashcardState () {}
//
//    public FlashcardState (Flashcard con) {
//        flashcard = con;
//    }
//
//    public void setContext(Flashcard flashcard) {
//        this.flashcard = flashcard;
//    }
//
//}
////#F44336 - czerwony
////#4CAF50 - zielony
////#2196F3 - niebieski
////#9E9E9E - szary
//
//class State0 extends FlashcardState {
//
//    public State0 (Flashcard con) {
//        super();
//        flashcard = con;
//    }
//
//    @Override
//    public ButtonDescriber[] getButtons() {
//
//        ButtonDescriber [] dcs = new ButtonDescriber[2];
//        dcs[0] = new ButtonDescriber("Źle", 0, 0xFFF44336);
//        dcs[1] = new ButtonDescriber("Dobrze", 1, 0xFF4CAF50);
//
//        return dcs;
//    }
//}
//
//class State1 extends FlashcardState {
//
//    public State1 (Flashcard con) {
//        super();
//        flashcard = con;
//    }
//
//    @Override
//    public ButtonDescriber[] getButtons() {
//        ButtonDescriber [] dcs = new ButtonDescriber[3];
//        dcs[0] = new ButtonDescriber("Źle", 0, 0xFFF44336);
//        dcs[1] = new ButtonDescriber("Trudno", 1, 0xFF2196F3);
//        dcs[2] = new ButtonDescriber("Latwo", 2, 0xFF4CAF50);
//
//        return dcs;
//    }
//}
//
//class State2 extends FlashcardState {
//
//    public State2 (Flashcard con) {
//        super();
//        flashcard = con;
//    }
//
//    @Override
//    public ButtonDescriber[] getButtons() {
//        ButtonDescriber [] dcs = new ButtonDescriber[3];
//        dcs[0] = new ButtonDescriber("Źle", 0, 0x00F44336);
//        dcs[1] = new ButtonDescriber("Trudno", 2, 0x002196F3);
//        dcs[2] = new ButtonDescriber("Latwo", 3, 0x004CAF50);
//
//        return dcs;
//    }
//}
//
//class State3 extends FlashcardState {
//
//    public State3 (Flashcard con) {
//        super();
//        flashcard = con;
//    }
//
//    @Override
//    public ButtonDescriber[] getButtons() {
//        ButtonDescriber [] dcs = new ButtonDescriber[4];
//        dcs[0] = new ButtonDescriber("Źle", 0, 0x00F44336);
//        dcs[1] = new ButtonDescriber("Trudno", 2, 0x009E9E9E);
//        dcs[2] = new ButtonDescriber("Srednio", 3, 0x002196F3);
//        dcs[3] = new ButtonDescriber("Bardzo Latwo", 4, 0x004CAF50);
//
//        return dcs;
//    }
//}
//
//class State4 extends FlashcardState {
//
//    public State4 (Flashcard con) {
//        super();
//        flashcard = con;
//    }
//
//    public ButtonDescriber[] getButtons() {
//        ButtonDescriber [] dcs = new ButtonDescriber[4];
//        dcs[0] = new ButtonDescriber("Źle", 0, 0x00F44336);
//        dcs[1] = new ButtonDescriber("Trudno", 3, 0x009E9E9E);
//        dcs[2] = new ButtonDescriber("Srednio", 4, 0x002196F3);
//        dcs[3] = new ButtonDescriber("Bardzo Latwo", 5, 0x004CAF50);
//
//        return dcs;
//    }
//}
//
