package com.example.colortile;

import android.graphics.Canvas;

public abstract class GameObject implements ITask {
    private Transform transform = new Transform();

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
}
