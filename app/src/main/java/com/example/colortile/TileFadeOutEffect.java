package com.example.colortile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

public class TileFadeOutEffect extends GameObject {

    private float endTime = 0.25f;
    private float time = 0f;
    public Boolean isExists = false;
    private float size = 0f;
    private float padding = 0f;
    private float edgeSize = 0f;
    private int color;

    public TileFadeOutEffect(Context context) {
        super(context);
    }

    @Override
    public void initialize() {
        super.initialize();
        time = 0f;
    }

    @Override
    public void finalize() {
        super.finalize();
    }

    public void draw(Canvas canvas) {
        float ratio = time / endTime;
        if (ratio > 1f) ratio = 1f;
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAlpha((int) (255 * (1f - ratio * 0.7f)));
        PointF pos = getTransform().getPosition();
        float extendSize = ratio * size / 5;
        canvas.drawRoundRect(
                pos.x + padding - extendSize,
                pos.y + padding - extendSize,
                pos.x + size - padding + extendSize,
                pos.y + size - padding + extendSize,
                edgeSize,
                edgeSize,
                paint);
    }

    public void update() {
        time += Time.getDeltaTime();
        if (time > endTime) isExists = false;
    }

    public void setPosition(PointF position) {
        getTransform().setPosition(position);
    }

    public void setSize(Float size) {
        this.size = size;
        this.edgeSize = size / 10f;
        this.padding = size / 15f;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
