package com.example.colortile.Scenes;

import android.content.Context;
import android.graphics.Canvas;

import com.example.colortile.ITask;

public abstract class BaseScene implements ITask {

    protected Context context;

    public BaseScene(Context context) {
        this.context = context;
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
