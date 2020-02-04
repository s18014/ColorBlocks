package com.example.colortile;

import android.content.Context;
import android.graphics.Canvas;

public class SceneManager implements ITask {
    enum SCENE {
        GAME,
        TITLE,
        RESULT
    }

    private static Context context;
    private static BaseScene currentScene;
    private static BaseScene nextScene;

    SceneManager(Context context, SCENE defaultScene) {
        SceneManager.context = context;
        changeScene(defaultScene);
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

    public static void changeScene(SceneManager.SCENE scene) {
        switch (scene) {
            case GAME:
                nextScene = new GameScene(context);
                break;
            case TITLE:
                nextScene = new TitleScene(context);
                break;
        }

        if (currentScene == null) {
            currentScene = nextScene;
            nextScene = null;
        }
    }
}
