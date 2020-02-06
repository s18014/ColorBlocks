package com.example.colortile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.view.View;


public class GameManager extends GameObject {

    enum GameState {
        NORMAL,
        GAME_OVER,
        PAUSE
    }

    static int BOARD_ROW_NUM = 13;
    static int BOARD_COLUMN_NUM = 10;

    private SoundPool soundPool;
    private int gameOverSound;

    private BoardManager board = new BoardManager(BOARD_ROW_NUM, BOARD_COLUMN_NUM, context);
    private ResultScreen resultScreen = new ResultScreen(context);
    private PauseScreen pauseScreen = new PauseScreen(context);
    private CnvButton pauseButton;
    private float btnWidth;
    private float btnHeight;
    private boolean isOnPause = false;

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
                pauseScreen.initialize();
            }
        });
        pauseButton.setText("| |", (int) Math.floor(btnWidth * 0.6), Color.WHITE);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                // USAGE_MEDIA
                // USAGE_GAME
                .setUsage(AudioAttributes.USAGE_GAME)
                // CONTENT_TYPE_MUSIC
                // CONTENT_TYPE_SPEECH, etc.
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();

        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                // ストリーム数に応じて
                .setMaxStreams(2)
                .build();

        gameOverSound = soundPool.load(context, R.raw.crrect_answer3, 1);
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
                if (board.isGameOver()) {
                    gameState = GameState.GAME_OVER;
                    Score score = new Score(context);
                    score.setScore(board.getScore());
                    soundPool.play(gameOverSound, 1, 1, 0, 0, 1);
                }
                if (isOnPause) {
                    gameState = GameState.PAUSE;
                    pauseScreen.initialize();
                    isOnPause = false;
                }
                break;
            case GAME_OVER:
                resultScreen.update();
                break;
            case PAUSE:
                pauseScreen.update();
                if (!pauseScreen.isPause()) gameState = GameState.NORMAL;
                break;
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

    @Override
    public void onPause() {
        super.onPause();
        isOnPause = true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
