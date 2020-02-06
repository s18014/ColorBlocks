package com.example.colortile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

public class MissEffect extends GameObject {

    private float endTime = 0.4f;
    private float time = 0f;
    public Boolean isExists = false;
    public float size = 0f;

    public MissEffect(Context context) {
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
        paint.setColor(Color.argb((int) (255f * (1f - ratio)), 150, 50, 50));
        PointF pos = getTransform().getPosition();
        paint.setStrokeWidth(size / 5);
        canvas.drawLine(pos.x - size / 2f, pos.y - size / 2f, pos.x + size / 2, pos.y + size / 2, paint);
        canvas.drawLine(pos.x + size / 2f, pos.y - size / 2f, pos.x - size / 2, pos.y + size / 2, paint);
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
    }

}
