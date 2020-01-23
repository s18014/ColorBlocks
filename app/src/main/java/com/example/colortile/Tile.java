package com.example.colortile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

class Tile extends GameObject {
    static Float PADDING = 5f;

    public Boolean isExists = false;
    public Type type = Type.NONE;

    private Float size = 0f;

    enum Type {
        NONE,
        A,
        B,
        C,
        D,
        E
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
            case A:
                paint.setColor(Color.parseColor("#ee88b7"));
                break;
            case B:
                paint.setColor(Color.parseColor("#ffd700"));
                break;
            case C:
                paint.setColor(Color.parseColor("#a0e3cd"));
                break;
            case D:
                paint.setColor(Color.parseColor("#98cde8"));
                break;
            case E:
                paint.setColor(Color.parseColor("#d3b0e8"));
                break;
            case NONE:
                break;
        }
        PointF pos = getTransform().getPosition();
        canvas.drawRoundRect(pos.x + PADDING, pos.y + PADDING, pos.x + size - PADDING, pos.y + size - PADDING, 10f, 10f, paint);
    }
}
