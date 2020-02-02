package com.example.colortile;

import android.graphics.Canvas;

public interface ITask {
    void initialize();
    void finalize();
    void update();
    void draw(Canvas canvas);
}
