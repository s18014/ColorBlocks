package com.example.colortile;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable, View.OnTouchListener {
    private static long FPS = 30l;

    private Thread thread;
    private Boolean isActive = false;

    private SceneManager sceneManager = new SceneManager();
    private float WIDTH;
    private float HEIGHT;
    private PointF translation;
    private float scale;

    public GameSurfaceView(Context context) {
        super(context);
        WIDTH = ScreenSettings.getWidth();
        HEIGHT = ScreenSettings.getHeight();
        getHolder().addCallback(this);
        setOnTouchListener(this);
        sceneManager.initialize();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        float scaleX = getWidth() / WIDTH;
        float scaleY = getHeight() / HEIGHT;
        scale = Math.min(scaleX, scaleY);
        translation = new PointF((getWidth() - WIDTH * scale) / 2f, (getHeight() - HEIGHT * scale) / 2f);
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        float scaleX = getWidth() / WIDTH;
        float scaleY = getHeight() / HEIGHT;
        scale = Math.min(scaleX, scaleY);
        translation = new PointF((getWidth() - WIDTH * scale) / 2f, (getHeight() - HEIGHT * scale) / 2f);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread = null;
    }

    @Override
    public void run() {
        int frame = 0;
        while (thread != null) {
            // バックグラウンド用のループ
            if (!isActive || !getHolder().getSurface().isValid()) {
                System.out.println("Running on background");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            sceneManager.update();
            draw();
            Input.next();

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
        event.setLocation(event.getX() / scale - translation.x / scale, event.getY() / scale - translation.y / scale);
        System.out.println("X:" + event.getX() + ", Y:" + event.getY());
        Input.add(event);
        return true;
    }

    private void draw() {
        Canvas canvas = getHolder().lockCanvas();
        if (canvas != null) {
            canvas.translate(translation.x, translation.y);
            canvas.scale(scale, scale);
            canvas.drawColor(Color.GRAY);
            sceneManager.draw(canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public void onResume() {
        isActive = true;
    }

    public void onPause() {
        isActive = false;
    }

}
