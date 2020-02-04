package com.example.colortile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;


public class GameManager extends GameObject {

    enum GameState {
        NORMAL,
        GAME_OVER,
        PAUSE
    }

    static int BOARD_ROW_NUM = 13;
    static int BOARD_COLUMN_NUM = 10;

    private BoardManager board = new BoardManager(BOARD_ROW_NUM, BOARD_COLUMN_NUM, context);
    private ResultScreen resultScreen = new ResultScreen(context);
    private PauseScreen pauseScreen = new PauseScreen(context);
    private CnvButton pauseButton;
    private float btnWidth;
    private float btnHeight;

    private GameState gameState = GameState.NORMAL;

    GameManager(Context context) {
        super(context);
    }

    @Override
    public void initialize() {
        board.initialize();
        resultScreen.initialize();
        pauseScreen.initialize();
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
                gameState = GameState.PAUSE;
                pauseScreen.startPause();
            }
        });
        pauseButton.setText("||", (int) Math.floor(btnWidth * 0.6), Color.WHITE);
    }

    @Override
    public void finalize() {
        board.finalize();
        resultScreen.finalize();
        pauseButton.finalize();
        pauseButton.finalize();
    }

    @Override
    public void update() {
        switch (gameState) {
            case NORMAL:
                board.update();
                pauseButton.update();
                break;
            case GAME_OVER:
                resultScreen.update();
                break;
            case PAUSE:
                pauseScreen.update();
                if (!pauseScreen.isPause()) gameState = GameState.NORMAL;
                break;
        }

        if (board.isGameOver()) {
            gameState = GameState.GAME_OVER;
            Score score = new Score(context);
            score.setScore(board.getScore());
        }
    }

    @Override
    public void draw(Canvas canvas) {
        switch (gameState) {
            case NORMAL:
                board.draw(canvas);
                pauseButton.draw(canvas);
                break;
            case GAME_OVER:
                board.draw(canvas);
                resultScreen.draw(canvas);
                break;
            case PAUSE:
                board.draw(canvas);
                pauseScreen.draw(canvas);
                break;
        }
    }
}
