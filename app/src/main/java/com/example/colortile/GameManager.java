package com.example.colortile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


public class GameManager implements SurfaceHolder.Callback, View.OnTouchListener, Runnable {
    static Long FPS = 60L;
    static Float BOARD_MAERGIN_LEFT_AND_RIGHT = 10f;
    static Point BOARD_PIECE_SIZE = new Point(10, 13);

    private Thread thread = new Thread(this);
    private Boolean isActive = false;
    private Boolean isSurfaceActive = false;
    private SurfaceHolder holder;

    private Float height;
    private Float width;

    // TEST
    private Tile tile = new Tile();
    private Board board = new Board();

    GameManager(SurfaceView surfaceView) {
        holder = surfaceView.getHolder();
        holder.addCallback(this);
        surfaceView.setOnTouchListener(this);
        tile.init(Tile.Type.RED);
        tile.getTransform().setPosition(new PointF(50f, 50f));

        thread.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isSurfaceActive = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.width = (float) width;
        this.height = (float) height;
        tile.setSize(this.width / 10f);
        board.setPieceSize(BOARD_PIECE_SIZE);
        board.setSize(this.width - BOARD_MAERGIN_LEFT_AND_RIGHT * 2f);
        Float posY = (this.height - board.getHeight()) / 2f;
        board.getTransform().setPosition(new PointF(BOARD_MAERGIN_LEFT_AND_RIGHT, posY));
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isSurfaceActive = false;
    }

    @Override
    public void run() {
        int frame = 0;
        while (true) {
            // バックグラウンド用のループ
            if (!isActive || !isSurfaceActive) {
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
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return true;
    }

    private void update() {
    }

    private void draw() {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) return;

        canvas.drawColor(Color.WHITE);
        board.draw(canvas);
        tile.draw(canvas);

        holder.unlockCanvasAndPost(canvas);
    }



    public void onResume() {
        isActive = true;
    }

    public void onPause() {
        isActive = false;
    }

}
