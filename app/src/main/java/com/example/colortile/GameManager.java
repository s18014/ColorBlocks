package com.example.colortile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameManager implements SurfaceHolder.Callback, View.OnTouchListener, Runnable {
    static Long FPS = 60L;

    private Thread thread = new Thread(this);
    private Boolean isActive = false;
    private SurfaceHolder holder;

    private int height;
    private int width;

    // test
    private float x = 0f;
    private float y = 0f;

    GameManager(SurfaceView surfaceView) {
        holder = surfaceView.getHolder();
        holder.addCallback(this);
        surfaceView.setOnTouchListener(this);
        thread.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void run() {
        while (true) {
            // バックグラウンド用のループ
            if (!isActive) {
                System.out.println("Running on background");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            draw();

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
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            x = event.getX();
            y = event.getY();
        }
        return true;
    }

    private void draw() {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) return;

        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawCircle(x, y, 200, paint);

        holder.unlockCanvasAndPost(canvas);
    }


    public void onResume() {
        isActive = true;
    }

    public void onPause() {
        isActive = false;
    }


}
