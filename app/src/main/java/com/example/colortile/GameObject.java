package com.example.colortile;

import android.content.Context;
import android.graphics.Canvas;

public abstract class GameObject implements ITask {
    private Transform transform = new Transform();
    protected Context context;

    public GameObject(Context context) {
        this.context = context;
    }

    public Transform getTransform() {
        return transform;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void finalize() {

    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}
