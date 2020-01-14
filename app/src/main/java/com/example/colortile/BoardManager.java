package com.example.colortile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

public class BoardManager extends GameObject {

    private Float width;
    private Float height;
    private int rowNum;
    private int columnNum;

    private Float tileSize;
    private Tile[][] board;


    public void init(int rowNum, int columnNum) {
        this.rowNum = rowNum;
        this.columnNum = columnNum;
        board = new Tile[rowNum][columnNum];
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < columnNum; col++) {
                board[row][col] = new Tile();
                board[row][col].getTransform().setParent(getTransform());
                Tile.Type type = Tile.Type.NONE;
                switch ((int) (Math.random() * 3)) {
                    case 0:
                        type = Tile.Type.RED;
                        break;
                    case 1:
                        type = Tile.Type.GREEN;
                        break;
                    case 2:
                        type = Tile.Type.BLUE;
                        break;
                }
                board[row][col].init(type);
                if (Math.random() < 0.7) {
                    board[row][col].isExists = true;
                }
            }
        }
    }

    public Float getWidth() {
        return width;
    }

    public Float getHeight() {
        return height;
    }

    public void setSize(Float width) {
        this.width = width;
        tileSize = width / columnNum;
        this.height = tileSize * rowNum;
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < columnNum; col++) {
                board[row][col].getTransform().setLocalPosition(new PointF(col * tileSize, row * tileSize));
                board[row][col].setSize(tileSize);
            }
        }
    }


    // ワールド座標から配列の位置を取得
    public Point worldToArrayIndex(PointF p) {
        PointF lp = getTransform().worldToLocalPosition(p);
        int x = (int) Math.floor((lp.x / tileSize));
        int y = (int) Math.floor((lp.y / tileSize));
        if (x >= columnNum || y >= rowNum || x < 0 || y < 0) return null;
        return new Point(x, y);
    }

    public void onTouch(PointF p) {
        Point index = worldToArrayIndex(p);
        System.out.println(index);
        if (index == null) return;
        board[index.y][index.x].isExists = false;
    }


    public void draw(Canvas canvas) {
        Paint paint = new Paint();

        // TODO: 背景は別クラスでやる
        for (int row = 0; row < rowNum + 6; row++) {
            for (int col = 0; col < columnNum + 2; col++) {
                if ((col + row) % 2 == 0) {
                    paint.setColor(Color.parseColor("#ffffff"));
                } else {
                    paint.setColor(Color.parseColor("#eeeeee"));
                }
                PointF pos = getTransform().localToWorldPosition(new PointF(col * tileSize - tileSize * 1, row * tileSize - tileSize * 3));
                canvas.drawRect(pos.x, pos.y, pos.x + tileSize, pos.y + tileSize, paint);
            }
        }


        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < columnNum; col++) {
                if (!board[row][col].isExists) continue;
                board[row][col].draw(canvas);
            }
        }
    }

    public void update() {
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < columnNum; col++) {
            }
        }
    }
}
