package com.example.colortile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

public class Board extends GameObject {

    private Float width;
    private Float height;
    private Point pieceSize;
    private Float tileSize;

    public Float getWidth() {
        return width;
    }

    public Float getHeight() {
        return height;
    }

    public void setSize(Float width) {
        this.width = width;
        tileSize = width / pieceSize.x;
        this.height = tileSize * pieceSize.y;
    }
    public void setPieceSize(Point p) {
        pieceSize = p;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        PointF pos = transform.getPosition();
        canvas.drawRect(pos.x, pos.y, pos.x + width, pos.y +height, paint);
    }
}
