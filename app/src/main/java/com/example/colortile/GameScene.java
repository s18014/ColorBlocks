package com.example.colortile;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class GameScene extends BaseScene {

    private GameObject gameManager = new GameManager(context);

    GameScene(Context context) {
        super(context);
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
