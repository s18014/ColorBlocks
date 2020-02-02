package com.example.colortile;

import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;

public class GameScene extends BaseScene {

    GameObject gameManager = new GameManager();

    GameScene(ISceneChanger sceneChanger) {
        super(sceneChanger);
    }

    @Override
    public void initialize() {
        super.initialize();
        gameManager.initialize();
    }

    @Override
    public void finalize() {
        super.finalize();
        gameManager.finalize();
    }

    @Override
    public void update() {
        super.update();
        gameManager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        gameManager.draw(canvas);
    }

    @Override
    public void updateWindow(int width, int height) {
        super.updateWindow(width, height);
        gameManager.updateWindow(width, height);
    }
}
