package com.example.colortile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.fragment.app.Fragment;


public class GameManager implements SurfaceHolder.Callback, View.OnTouchListener, Runnable {
    static Long FPS = 60L;
    static Float BOARD_MARGIN_LEFT_AND_RIGHT = 10f;
    static int BOARD_ROW_NUM = 13;
    static int BOARD_COLUMN_NUM = 10;

    private Context context;
    private Thread thread = new Thread(this);
    private Boolean isActive = false;
    private SurfaceHolder holder;

    private Float height;
    private Float width;

    private BoardManager board;
    private boolean lock = false;

    GameManager(Context context, SurfaceView surfaceView) {
        this.context = context;
        board = new BoardManager(context);
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
        while (thread != null) {
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
        board.onTouch(event);
        return true;
    }

    private void update() {
        board.update();
        if (board.isGameOver() && !lock) {
            lock = true;
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Bundle bundle = new Bundle();
                    bundle.putInt("score", board.getScore());
                    Fragment fragment = new ResultFragment();
                    fragment.setArguments(bundle);
                    ((IFragmentChanger) context).pushFragment(fragment);
                }
            }, 100);
        }
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

    public void onDestroy() {
        thread = null;
    }
}
