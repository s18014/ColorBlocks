package com.example.colorblocks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

public class Tile extends GameObject {

    public Boolean isExists = false;
    public Type type = Type.NONE;

    private float padding = 0f;
    private float edgeSize = 0f;
    private float size = 0f;

    public Tile(Context context) {
        super(context);
    }

    enum Type {
        NONE,
        A,
        B,
        C,
        D,
        E
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setSize(Float size) {
        this.size = size;
        this.edgeSize = size / 10f;
        this.padding = size / 15f;
    }

    @Override
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
        canvas.drawRoundRect(pos.x + padding, pos.y + padding, pos.x + size - padding, pos.y + size - padding, edgeSize, edgeSize, paint);
    }
}
