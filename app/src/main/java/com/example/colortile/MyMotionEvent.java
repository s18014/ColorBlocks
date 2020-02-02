package com.example.colortile;


public class MyMotionEvent {
    private int action;
    private float x;
    private float y;

    MyMotionEvent(int action, float x, float y) {
        this.action = action;
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getAction() {
        return action;
    }
}
