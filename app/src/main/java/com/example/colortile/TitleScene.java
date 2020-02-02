package com.example.colortile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;

public class TitleScene extends BaseScene {
    TitleScene(ISceneChanger sceneChanger) {
        super(sceneChanger);
    }

    @Override
    public void initialize() {
        super.initialize();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sceneChanger.changeScene(SceneManager.SCENE.GAME);
            }
        }, 1000);
    }

    @Override
    public void finalize() {
        super.finalize();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setTextSize(200f);
        canvas.drawText("TITLE", 500f, 500f, paint);
    }
}
