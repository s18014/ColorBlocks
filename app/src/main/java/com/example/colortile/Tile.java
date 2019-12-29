package com.example.colortile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

class Tile extends GameObject{
    static Float PADDING = 5f;

    public Boolean isExists = false;
    public Type type = Type.NONE;

    private Float size = 0f;

    enum Type {
        RED,
        GREEN,
        BLUE,
        NONE
    }

    public void init(Type type) {
        this.type = type;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        switch (type) {
            case RED:
                paint.setColor(Color.parseColor("#ee5555"));
                break;
            case GREEN:
                break;
            case BLUE:
                break;
            case NONE:
                break;
        }
        PointF pos = transform.getPosition();
        canvas.drawRoundRect(pos.x + PADDING, pos.y + PADDING, pos.x + size - PADDING, pos.y + size - PADDING, 10f, 10f, paint);
    }

    public void moveTo(PointF p) {
        transform.setPosition(p);
    }
}
