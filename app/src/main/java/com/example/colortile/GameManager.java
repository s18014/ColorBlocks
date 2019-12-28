package com.example.colortile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
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
    private Transform parent = new Transform();
    private Transform child1 = new Transform();

    GameManager(SurfaceView surfaceView) {
        holder = surfaceView.getHolder();
        holder.addCallback(this);
        surfaceView.setOnTouchListener(this);

        child1.setParent(parent);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isActive = false;
    }

    @Override
    public void run() {
        int frame = 0;
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

            Float sin = (float) Math.sin((double) frame / 10);
            Float cos = (float) Math.cos((double) frame / 10);
            Float length = 300f;

            child1.setLocalPosition(new PointF(sin * length, cos * length));

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
                parent.setPosition(new PointF(event.getX(), event.getY()));
                break;
        }
        return true;
    }

    private void draw() {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) return;

        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        PointF parentPos = parent.getPosition();
        canvas.drawCircle(parentPos.x, parentPos.y, 200, paint);
        PointF child1Pos = child1.getPosition();
        paint.setColor(Color.RED);
        canvas.drawCircle(child1Pos.x, child1Pos.y, 50, paint);

        holder.unlockCanvasAndPost(canvas);
    }


    public void onResume() {
        isActive = true;
    }

    public void onPause() {
        isActive = false;
    }


}
