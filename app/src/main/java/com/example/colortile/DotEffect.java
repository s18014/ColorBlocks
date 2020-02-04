package com.example.colortile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

public class DotEffect extends GameObject {
    static Long endTime = 400l;
    private Long startTime = 0L;
    public Boolean isExists = false;
    public float size = 0f;

    DotEffect(Context context) {
        super(context);
    }


    public void init(PointF position) {
        getTransform().setPosition(position);
        this.startTime = System.currentTimeMillis();
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public void draw(Canvas canvas) {
        Long time = System.currentTimeMillis() - startTime;
        float ratio = (float) time / endTime;
        if (ratio > 1f) ratio = 1f;
        Paint paint = new Paint();
        paint.setColor(Color.argb((int) (255f * (1 -ratio)), 150, 150, 150));
        PointF pos = getTransform().getPosition();
        canvas.drawCircle(pos.x, pos.y, size, paint);
    }

    public void update() {
        Long time = System.currentTimeMillis() - startTime;
        if (time > endTime) isExists = false;
    }
}
