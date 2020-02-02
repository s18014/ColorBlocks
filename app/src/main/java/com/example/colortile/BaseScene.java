package com.example.colortile;

import android.graphics.Canvas;

public abstract class BaseScene implements ITask {

    protected ISceneChanger sceneChanger;

    BaseScene(ISceneChanger sceneChanger) {
        this.sceneChanger = sceneChanger;
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
