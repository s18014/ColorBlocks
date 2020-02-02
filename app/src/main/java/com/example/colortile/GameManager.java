package com.example.colortile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;


public class GameManager extends GameObject {
    static int BOARD_ROW_NUM = 13;
    static int BOARD_COLUMN_NUM = 10;
    static float BOARD_MARGIN_LEFT_AND_RIGHT = 0.01f;

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

    @Override
    public void updateWindow(int width, int height) {
        board.updateWindow(width, height);
        board.setSize(width - width * BOARD_MARGIN_LEFT_AND_RIGHT * 2f);
        Float posY = (height - board.getHeight()) / 2f;
        board.getTransform().setPosition(new PointF(width * BOARD_MARGIN_LEFT_AND_RIGHT, posY));
        System.out.println(width);
    }
}
