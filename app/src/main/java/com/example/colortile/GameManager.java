package com.example.colortile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


public class GameManager implements ITask {
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
        if (canvas == null) return;
        canvas.drawColor(Color.GRAY);
        board.draw(canvas);
    }

    @Override
    public void updateWindow(int width, int height) {
        board.updateWindow(width, height);
    }
}
