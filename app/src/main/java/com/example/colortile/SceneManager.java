package com.example.colortile;

import android.graphics.Canvas;

public class SceneManager implements ITask, ISceneChanger {
    private BaseScene currentScene;
    private BaseScene nextScene;

    @Override
    public void initialize() {

    }

    @Override
    public void finalize() {

    }

    @Override
    public void update() {
        if (nextScene != null) {
            currentScene.initialize();
            nextScene = null;
        }
        currentScene.update();
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void updateWindow(int width, int height) {

    }

}
