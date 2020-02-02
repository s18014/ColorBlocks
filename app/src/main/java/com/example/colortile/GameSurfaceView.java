package com.example.colortile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable, View.OnTouchListener {
    private static long FPS = 60l;
    private static float WIDTH = 9f;
    private static float HEIGHT = 16f;

    private Thread thread;
    private Boolean isActive = false;

    private SceneManager sceneManager = new SceneManager();
    private float scale;

    public GameSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
        sceneManager.initialize();
        System.out.println(WIDTH / HEIGHT);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        float scaleX = getWidth() / WIDTH;
        float scaleY = getHeight() / HEIGHT;
        scale = Math.min(scaleX, scaleY);

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        sceneManager.updateWindow((int)WIDTH, (int)HEIGHT);
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
        return true;
    }

    private void draw() {
        Canvas canvas = getHolder().lockCanvas();
        if (canvas != null) {
            canvas.translate((getWidth() - WIDTH * scale) / 2f, (getHeight() - HEIGHT * scale) / 2f);
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
