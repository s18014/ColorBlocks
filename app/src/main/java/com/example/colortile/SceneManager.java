package com.example.colortile;

import android.graphics.Canvas;

public class SceneManager implements ITask, ISceneChanger {
    enum SCENE {
        GAME,
        TITLE,
        RESULT
    }

    private BaseScene currentScene = new GameScene(this);
    private BaseScene nextScene;

    @Override
    public void initialize() {
        currentScene.initialize();
    }

    @Override
    public void finalize() {
        currentScene.finalize();
    }

    @Override
    public void update() {
        if (nextScene != null) {
            currentScene.finalize();
            currentScene = nextScene;
            currentScene.initialize();
            nextScene = null;
        }
        currentScene.update();
    }

    @Override
    public void draw(Canvas canvas) {
        currentScene.draw(canvas);
    }

    @Override
    public void changeScene(SceneManager.SCENE scene) {
        switch (scene) {
            case GAME:
                nextScene = new GameScene(this);
        }
    }
}
