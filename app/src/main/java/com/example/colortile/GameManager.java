package com.example.colortile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


public class GameManager implements SurfaceHolder.Callback, View.OnTouchListener, Runnable {
    static Long FPS = 60L;
    static Float BOARD_MARGIN_LEFT_AND_RIGHT = 10f;
    static int BOARD_ROW_NUM = 13;
    static int BOARD_COLUMN_NUM = 10;

    private Thread thread = new Thread(this);
    private Boolean isActive = false;
    private SurfaceHolder holder;

    private Float height;
    private Float width;

    private BoardManager board = new BoardManager();

    // TEST
    private PointF prePos;

    GameManager(SurfaceView surfaceView) {
        holder = surfaceView.getHolder();
        holder.addCallback(this);
        surfaceView.setOnTouchListener(this);
        board.init(BOARD_ROW_NUM, BOARD_COLUMN_NUM);

        thread.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.width = (float) width;
        this.height = (float) height;
        board.setSize(this.width - BOARD_MARGIN_LEFT_AND_RIGHT * 2f);
        Float posY = (this.height - board.getHeight()) / 2f;
        board.getTransform().setPosition(new PointF(BOARD_MARGIN_LEFT_AND_RIGHT, posY));
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public void run() {
        int frame = 0;
        while (true) {
            // バックグラウンド用のループ
            if (!isActive || !holder.getSurface().isValid()) {
                System.out.println("Running on background");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            update();
            draw();

            frame++;
            // 次のフレームまで停止
            try {
                Thread.sleep(1000 / FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                board.onTouch(new PointF(event.getX(), event.getY()));
                break;
        }
        return true;
    }

    private void update() {
        board.update();
    }

    private void draw() {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) return;

        canvas.drawColor(Color.GRAY);
        board.draw(canvas);

        holder.unlockCanvasAndPost(canvas);
    }



    public void onResume() {
        isActive = true;
    }

    public void onPause() {
        isActive = false;
    }

}
