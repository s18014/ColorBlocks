package com.example.colortile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import com.example.colortile.Scenes.SceneManager;


public class GameManager extends GameObject {

    static int BOARD_ROW_NUM = 13;
    static int BOARD_COLUMN_NUM = 10;

    private BoardManager board = new BoardManager(BOARD_ROW_NUM, BOARD_COLUMN_NUM, context);
    private CnvButton pauseButton;
    private float btnWidth;
    private float btnHeight;

    public GameManager(Context context) {
        super(context);
    }

    @Override
    public void initialize() {
        board.initialize();
        btnWidth = ScreenSettings.getWidth() / 8;
        btnHeight = btnWidth;
        pauseButton = new CnvButton(context, R.drawable.menu_100x100,
                ScreenSettings.getWidth() - btnWidth * 1.5f,
                btnWidth / 2,
                btnWidth,
                btnHeight
        );
        pauseButton.initialize();
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SceneManager.pushScene(SceneManager.SCENE.PAUSE);

            }
        });
        pauseButton.setText("| |", (int) Math.floor(btnWidth * 0.6), Color.WHITE);
    }

    @Override
    public void finalize() {
        board.finalize();
        pauseButton.finalize();
    }

    @Override
    public void update() {
        board.update();
        pauseButton.update();
   }

    @Override
    public void draw(Canvas canvas) {
        board.draw(canvas);
        pauseButton.draw(canvas);
    }

    @Override
    public void onPause() {
        super.onPause();
        SceneManager.pushScene(SceneManager.SCENE.PAUSE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
