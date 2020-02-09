package com.example.colorblocks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.colorblocks.Scenes.SceneManager;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable, View.OnTouchListener {
    private static long FPS = 60l;

    private long lastFrameTime;

    private Thread thread;
    private Boolean isActive = true;

    private SceneManager sceneManager;
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
        sceneManager = new SceneManager(context, SceneManager.SCENE.TITLE);
        sceneManager.initialize();
        Time.initialize();
        lastFrameTime = System.currentTimeMillis();
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
        // ViewとScreenの画面サイズ比
        float scaleX = getWidth() / WIDTH;
        float scaleY = getHeight() / HEIGHT;
        scale = Math.min(scaleX, scaleY);
        // View / 2 = 画面中央
        // Screen * scale / 2 = Screenの中央
        translation = new PointF((getWidth() - WIDTH * scale) / 2f, (getHeight() - HEIGHT * scale) / 2f);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread = null;
    }

    @Override
    public void run() {
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

            Time.update();
            sceneManager.update();
            draw();
            Input.next();

            // 次のフレームまで停止
            try {
                long frameTime = System.currentTimeMillis() - lastFrameTime;
                // 前のフレームからの経過時間が規定より早い場合、早すぎた時間分sleep
                if (frameTime < 1000 / FPS) {
                    Thread.sleep(1000 / FPS - frameTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lastFrameTime = System.currentTimeMillis();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        event.setLocation(event.getX() / scale - translation.x / scale, event.getY() / scale - translation.y / scale);
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
        sceneManager.onResume();
        Time.onResume();
    }

    public void onPause() {
        sceneManager.onPause();
    }

}
