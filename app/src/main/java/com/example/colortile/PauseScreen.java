package com.example.colortile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class PauseScreen extends GameObject{

    private CnvButton titleButton;
    private CnvButton retryButton;
    private CnvButton resumeButton;

    private boolean isPause = false;
    private float btnWidth;
    private float btnHeight;
    private float time;

    PauseScreen(Context context) {
        super(context);
    }

    @Override
    public void initialize() {
        super.initialize();
        btnWidth = ScreenSettings.getWidth() / 2f;
        btnHeight = btnWidth * 0.25f;
        titleButton = new CnvButton(context, R.drawable.start_100x25,
                ScreenSettings.getWidth() / 2f - btnWidth / 2,
                ScreenSettings.getHeight() * 0.5f,
                btnWidth,
                btnHeight
        );
        titleButton.initialize();
        titleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SceneManager.changeScene(SceneManager.SCENE.TITLE);
            }
        });
        titleButton.setText("タイトルに戻る", (int) btnWidth / 7, Color.WHITE);

        retryButton = new CnvButton(context, R.drawable.start_100x25,
                ScreenSettings.getWidth() / 2f - btnWidth / 2,
                ScreenSettings.getHeight() * 0.6f,
                btnWidth,
                btnHeight
        );
        retryButton.initialize();
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SceneManager.changeScene(SceneManager.SCENE.GAME);
            }
        });
        retryButton.setText("やり直す", (int) btnWidth / 7, Color.WHITE);

        resumeButton = new CnvButton(context, R.drawable.start_100x25,
                ScreenSettings.getWidth() / 2f - btnWidth / 2,
                ScreenSettings.getHeight() * 0.8f,
                btnWidth,
                btnHeight
        );
        resumeButton.initialize();
        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPause = false;
            }
        });
        resumeButton.setText("再開", (int) btnWidth / 7, Color.WHITE);

        time = 0;
        isPause = true;
    }

    @Override
    public void finalize() {
        super.finalize();
        titleButton.finalize();
        retryButton.finalize();
        resumeButton.finalize();
    }

    @Override
    public void update() {
        super.update();
        titleButton.update();
        retryButton.update();
        resumeButton.update();
        time += Time.getDeltaTime();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        float ratio = time / 0.5f;
        if (ratio > 1) ratio = 1;
        canvas.drawColor(Color.argb((int) (255 * ratio), 50, 50, 50));
        titleButton.draw(canvas);
        retryButton.draw(canvas);
        resumeButton.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(ScreenSettings.getWidth() / 7f);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("PAUSE", ScreenSettings.getWidth() / 2f, ScreenSettings.getHeight() / 4f, paint);
    }

    public boolean isPause() {
        return isPause;
    }
}
