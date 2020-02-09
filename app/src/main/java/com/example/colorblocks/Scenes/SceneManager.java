package com.example.colorblocks.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.colorblocks.ITask;
import com.example.colorblocks.ScreenSettings;
import com.example.colorblocks.Time;

import java.util.ArrayList;
import java.util.List;

public class SceneManager implements ITask {
    public enum SCENE {
        GAME,
        TITLE,
        RESULT,
        PAUSE
    }

    private static Context context;
    private static List<BaseScene> sceneList = new ArrayList<>();
    private static BaseScene nextPushScene;
    private static boolean popFrag = false;
    private static boolean removeAllFrag = false;

    public SceneManager(Context context, SCENE defaultScene) {
        SceneManager.context = context;
        replaceScene(defaultScene);
    }

    @Override
    public void initialize() {
        if (!sceneList.isEmpty()) {
            for (BaseScene scene: sceneList) {
                scene.initialize();
            }
        }

    }

    @Override
    public void finalize() {
        if (!sceneList.isEmpty()) {
            for (BaseScene scene: sceneList) {
                scene.finalize();
            }
        }
    }

    @Override
    public void update() {
        System.out.println(sceneList.size());
        if (removeAllFrag) {
            if (!sceneList.isEmpty()) {
                for (BaseScene scene: sceneList) {
                    scene.finalize();
                }
            }
            sceneList.clear();
            removeAllFrag = false;
        }

        if (popFrag) {
            if (!sceneList.isEmpty()) {
                sceneList.get(sceneList.size() - 1).finalize();
                sceneList.remove(sceneList.size() - 1);
            }
            popFrag = false;
        }

        if (nextPushScene != null) {
            nextPushScene.initialize();
            sceneList.add(nextPushScene);
            nextPushScene = null;
        }

        if (!sceneList.isEmpty()) {
            sceneList.get(sceneList.size() - 1).update();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (!sceneList.isEmpty()) {
            for (BaseScene scene: sceneList) {
                scene.draw(canvas);
            }
        }

        if (ScreenSettings.getDisplayMode() == ScreenSettings.DISPLAY_MODE.DEBUG) {
            Paint paint = new Paint();
            paint.setTextSize(ScreenSettings.getWidth() / 20);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("FPS: " + String.format("%.1f", Time.getFps()), ScreenSettings.getWidth() * 0.9f, ScreenSettings.getHeight(), paint);
        }
    }

    @Override
    public void onPause() {
        if (!sceneList.isEmpty()) {
            for (BaseScene scene: sceneList) {
                scene.onPause();
            }
        }
    }

    @Override
    public void onResume() {
        if (!sceneList.isEmpty()) {
            for (BaseScene scene: sceneList) {
                scene.onResume();
            }
        }
    }

    public static void replaceScene(SCENE scene) {
        popScene();
        pushScene(scene);
   }

    public static void pushScene(SCENE scene) {
        switch (scene) {
            case GAME:
                nextPushScene = new GameScene(context);
                break;
            case TITLE:
                nextPushScene = new TitleScene(context);
                break;
            case PAUSE:
                nextPushScene = new PauseScene(context);
                break;
            case RESULT:
                nextPushScene = new ResultScene(context);
                break;
        }
    }

    public static void popScene() {
        popFrag = true;
    }

    public static void removeAll() {
        removeAllFrag = true;
    }
}
