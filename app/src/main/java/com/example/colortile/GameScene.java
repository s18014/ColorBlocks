package com.example.colortile;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class GameScene extends BaseScene {

    GameObject gameManager = new GameManager();
    CnvButton titleBtn;

    GameScene(ISceneChanger sceneChanger, Context context) {
        super(sceneChanger, context);
    }

    @Override
    public void initialize() {
        super.initialize();
        gameManager.initialize();
        titleBtn = new CnvButton(context.getResources(), R.drawable.start_100x25, (int) ScreenSettings.getWidth() - 100 * 3 - 50, 50, 100 * 3, 25 * 3);
        titleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sceneChanger.changeScene(SceneManager.SCENE.TITLE);
            }
        });
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
        titleBtn.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        gameManager.draw(canvas);
        titleBtn.draw(canvas);
    }
}
