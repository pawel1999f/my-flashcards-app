package com.example.myflashcardsapp;

public class ButtonDescriber {

    private String label;
    private int toState;
    private int color;

    public ButtonDescriber(String lb, int toSt, int cl) {
        label = lb;
        toState = toSt;
        color = cl;
    }

    public int getColor() {
        return color;
    }

    public String getLabel() {
        return label;
    }

    public int getToState() {
        return toState;
    }
}
