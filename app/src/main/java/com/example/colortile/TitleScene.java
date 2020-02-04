package com.example.colortile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class TitleScene extends BaseScene {
    CnvButton cnvButton;
    TitleScene(Context context) {
        super(context);
    }

    @Override
    public void initialize() {
        super.initialize();
        cnvButton = new CnvButton(context.getResources(), R.drawable.start_100x25, ScreenSettings.getWidth() / 2 - (100 * 4 / 2), ScreenSettings.getHeight() / 2, 100 * 4, 25 * 4);
        cnvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SceneManager.changeScene(SceneManager.SCENE.GAME);
            }
        });
    }

    @Override
    public void finalize() {
        super.finalize();
    }

    @Override
    public void update() {
        super.update();
        cnvButton.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setTextSize(ScreenSettings.getWidth() / 6f);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("TITLE", ScreenSettings.getWidth() / 2f, ScreenSettings.getHeight() / 4f, paint);
        cnvButton.draw(canvas);
    }
}
