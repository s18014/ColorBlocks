package com.example.colortile;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class SceneManager implements ITask, ISceneChanger {
    enum SCENE {
        GAME,
        TITLE,
        RESULT
    }

    private Context context;
    private BaseScene currentScene;
    private BaseScene nextScene;

    SceneManager(Context context) {
        this.context = context;
        this.currentScene = new TitleScene(this, context);
    }

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
                nextScene = new GameScene(this, context);
                break;
            case TITLE:
                nextScene = new TitleScene(this, context);
        }
    }
}
