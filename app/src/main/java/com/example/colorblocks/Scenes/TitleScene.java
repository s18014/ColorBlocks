package com.example.colorblocks.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.colorblocks.BoardManagerTitle;
import com.example.colorblocks.CnvButton;
import com.example.colorblocks.R;
import com.example.colorblocks.Score;
import com.example.colorblocks.ScreenSettings;

public class TitleScene extends BaseScene {
    private CnvButton cnvButton;
    private BoardManagerTitle boardManagerTitle;
    private CnvButton titleLogo;

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

        btnWidth = ScreenSettings.getWidth() * 0.8f;
        btnHeight = btnWidth * (540f / 960f);
        titleLogo = new CnvButton(context, R.drawable.title_logo_960x540,
                ScreenSettings.getWidth() / 2f - btnWidth / 2f,
                ScreenSettings.getHeight() * 0.2f,
                btnWidth,
                btnHeight);
        titleLogo.initialize();
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
        paint.setTextSize(ScreenSettings.getWidth() / 7f);
        paint.setTextAlign(Paint.Align.CENTER);

        Score score = new Score(context);
        paint.setTextSize(ScreenSettings.getWidth() / 10f);
        canvas.drawText("HIGH SCORE", ScreenSettings.getWidth() * 0.5f, ScreenSettings.getHeight() * 0.6f, paint);
        canvas.drawText(Integer.toString(score.getHighScore()), ScreenSettings.getWidth() / 2f, ScreenSettings.getHeight() * 0.68f, paint);
        cnvButton.draw(canvas);

        titleLogo.draw(canvas);
    }
}
