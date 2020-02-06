package com.example.colortile.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.colortile.BoardManagerTitle;
import com.example.colortile.CnvButton;
import com.example.colortile.R;
import com.example.colortile.Score;
import com.example.colortile.ScreenSettings;

public class TitleScene extends BaseScene {
    private CnvButton cnvButton;
    private BoardManagerTitle boardManagerTitle;

    TitleScene(Context context) {
        super(context);
    }

    @Override
    public void initialize() {
        super.initialize();
        boardManagerTitle = new BoardManagerTitle(13, 10, context);
        boardManagerTitle.initialize();
        float btnWidth = ScreenSettings.getWidth() / 2;
        float btnHeight = btnWidth * 0.25f;
        cnvButton = new CnvButton(context, R.drawable.start_100x25,
                ScreenSettings.getWidth() / 2f - btnWidth / 2f,
                ScreenSettings.getHeight() * 0.8f,
                btnWidth,
                btnHeight);
        cnvButton.initialize();
        cnvButton.setText("スタート",(int) btnWidth / 7, Color.WHITE);
        cnvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SceneManager.replaceScene(SceneManager.SCENE.GAME);
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
        boardManagerTitle.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        boardManagerTitle.draw(canvas);
        canvas.drawColor(Color.argb(100, 50, 50, 50));
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(ScreenSettings.getWidth() / 6f);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("COLOR TILE", ScreenSettings.getWidth() / 2f, ScreenSettings.getHeight() / 4f, paint);

        Score score = new Score(context);
        paint.setTextSize(ScreenSettings.getWidth() / 10f);
        canvas.drawText("HIGH SCORE", ScreenSettings.getWidth() / 2f, ScreenSettings.getHeight() / 2.3f, paint);
        canvas.drawText(Integer.toString(score.getHighScore()), ScreenSettings.getWidth() / 2f, ScreenSettings.getHeight() / 2f, paint);
        cnvButton.draw(canvas);
    }
}
