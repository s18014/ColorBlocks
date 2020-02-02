package com.example.colortile;

import android.graphics.Canvas;

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
}
