package com.example.colortile.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.colortile.CnvButton;
import com.example.colortile.R;
import com.example.colortile.Score;
import com.example.colortile.ScreenSettings;

public class ResultScene extends BaseScene {

    private CnvButton titleButton;
    private CnvButton retryButton;
    private float btnWidth;
    private float btnHeight;

    ResultScene(Context context) {
        super(context);
    }

    @Override
    public void initialize() {
        super.initialize();
        btnWidth = ScreenSettings.getWidth() / 2f;
        btnHeight = btnWidth * 0.25f;
        titleButton = new CnvButton(context, R.drawable.start_100x25,
                ScreenSettings.getWidth() / 2f - btnWidth / 2,
                ScreenSettings.getHeight() * 0.6f,
                btnWidth,
                btnHeight
                );
        titleButton.initialize();
        titleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SceneManager.removeAll();
                SceneManager.replaceScene(SceneManager.SCENE.TITLE);
            }
        });
        titleButton.setText("タイトルに戻る", (int) btnWidth / 7, Color.WHITE);

        retryButton = new CnvButton(context, R.drawable.start_100x25,
                ScreenSettings.getWidth() / 2f - btnWidth / 2,
                ScreenSettings.getHeight() * 0.7f,
                btnWidth,
                btnHeight
        );
        retryButton.initialize();
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SceneManager.removeAll();
                SceneManager.replaceScene(SceneManager.SCENE.GAME);
            }
        });
        retryButton.setText("やり直す", (int) btnWidth / 7, Color.WHITE);

    }

    @Override
    public void finalize() {
        super.finalize();
        titleButton.finalize();
        retryButton.finalize();
    }

    @Override
    public void update() {
        super.update();
        titleButton.update();
        retryButton.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.argb(100, 50, 50, 50));
        titleButton.draw(canvas);
        retryButton.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(ScreenSettings.getWidth() / 7f);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("GAME OVER", ScreenSettings.getWidth() / 2f, ScreenSettings.getHeight() / 4f, paint);

        Score score = new Score(context);
        paint.setTextSize(ScreenSettings.getWidth() / 10f);
        canvas.drawText("SCORE", ScreenSettings.getWidth() / 2f, ScreenSettings.getHeight() / 2.3f, paint);
        canvas.drawText(Integer.toString(score.getScore()), ScreenSettings.getWidth() / 2f, ScreenSettings.getHeight() / 2f, paint);
    }
}
