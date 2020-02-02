package com.example.colortile;

import android.graphics.Canvas;


public class GameManager extends GameObject {
    static int BOARD_ROW_NUM = 13;
    static int BOARD_COLUMN_NUM = 10;

    private BoardManager board = new BoardManager(BOARD_ROW_NUM, BOARD_COLUMN_NUM);

    @Override
    public void initialize() {
        board.initialize();
    }

    @Override
    public void finalize() {
        board.finalize();
    }

    @Override
    public void update() {
        board.update();
    }

    @Override
    public void draw(Canvas canvas) {
        board.draw(canvas);
    }
}
