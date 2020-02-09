package com.example.colorblocks;

import android.graphics.Canvas;

public interface ITask {
    void initialize();
    void finalize();
    void update();
    void draw(Canvas canvas);
    void onPause();
    void onResume();
}
