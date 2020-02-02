package com.example.colortile;

import android.view.MotionEvent;

public class Input {
    private static MyMotionEvent currentEvent;
    private static MyMotionEvent nextEvent;

    public static MyMotionEvent getEvent() {
        return currentEvent;
    }

    public static void add(MotionEvent event) {
        nextEvent = new MyMotionEvent(event.getAction(), event.getX(), event.getY());
    }

    public static void next() {
        currentEvent = nextEvent;
        nextEvent = null;
    }
}
