package com.example.colortile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

public class DotEffect extends GameObject {
    private float endTime = 0.4f;
    private float time = 0f;
    public Boolean isExists = false;
    public float size = 0f;

    public DotEffect(Context context) {
        super(context);
    }


    public void init(PointF position) {
        getTransform().setPosition(position);
        time = 0f;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public void draw(Canvas canvas) {
        float ratio = time / endTime;
        if (ratio > 1f) ratio = 1f;
        Paint paint = new Paint();
        paint.setColor(Color.argb((int) (255f * (1f - ratio)), 150, 150, 150));
        PointF pos = getTransform().getPosition();
        canvas.drawCircle(pos.x, pos.y, size, paint);
    }

    public void update() {
        time += Time.getDeltaTime();
        if (time > endTime) isExists = false;
    }
}
