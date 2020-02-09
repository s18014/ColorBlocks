package com.example.colorblocks;

import android.view.MotionEvent;

public class Input {
    private static MyMotionEvent currentEvent;
    private static MyMotionEvent nextEvent;
    private static  boolean isActionDown;

    public static MyMotionEvent getEvent() {
        return currentEvent;
    }

    public static void add(MotionEvent event) {
        // ActionDownが飛ばされないように調整
        if (isActionDown) {
            return;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isActionDown = true;
        }
        nextEvent = new MyMotionEvent(event.getAction(), event.getX(), event.getY());
    }

    public static void next() {
        isActionDown = false;
        currentEvent = nextEvent;
        nextEvent = null;
    }
}
