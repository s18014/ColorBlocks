package com.example.colortile;

import android.content.Context;
import android.graphics.Canvas;

public abstract class BaseScene implements ITask {

    protected Context context;

    BaseScene(Context context) {
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
}
