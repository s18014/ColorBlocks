package com.example.colortile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import java.util.HashMap;

public class BoardManager extends GameObject {

    enum CheckState {
        NONE,
        CHECKED,
        EXISTS
    }

    private Float width;
    private Float height;
    private int rowNum;
    private int columnNum;

    private Float tileSize;
    private Tile[][] board;
    private DotEffectSystem dotEffectSystem = new DotEffectSystem();


    public void init(int rowNum, int columnNum) {
        this.rowNum = rowNum;
        this.columnNum = columnNum;
        dotEffectSystem.getTransform().setParent(getTransform());
        dotEffectSystem.init();
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
                if (Math.random() < 0.2) {
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
        dotEffectSystem.setSize(15f);
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

    public PointF arrayIndexToWorld(Point index) {
        PointF lp = new PointF(index.x * tileSize, index.y * tileSize);
        return getTransform().localToWorldPosition(lp);
    }

    public void onTouch(PointF p) {
        Point index = worldToArrayIndex(p);
        System.out.println(index);
        if (index == null) return;
        CheckState[][] foundTilesMap = findTiles(index.x, index.y);
        if (foundTilesMap == null) return;
        CheckState[][] deletableTilesMap = findDeletableTiles(foundTilesMap);
        if (deletableTilesMap == null) return;
        deleteTiles(deletableTilesMap, index.x, index.y);
        System.out.println(isExistsDeletableTiles());
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
        dotEffectSystem.draw(canvas);

        // TEST
        if (!isExistsDeletableTiles()) {
            paint.setColor(Color.parseColor("#999999"));
            paint.setTextSize(width / 8f);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("GAME OVER", width / 2f, height / 2f, paint);
        }
    }

    public void update() {
        dotEffectSystem.update();
    }

    private CheckState[][] findTiles(int x, int y) {
        CheckState[][] checkedMap = new CheckState[rowNum][columnNum];
        if (board[y][x].isExists) return null;
        // 開始地点から4方向に走査
        findTileOnStraightLine(x, y, 0, 1, checkedMap);
        findTileOnStraightLine(x, y, 0, -1, checkedMap);
        findTileOnStraightLine(x, y, 1, 0, checkedMap);
        findTileOnStraightLine(x, y, -1, 0, checkedMap);
        return checkedMap;
    }

    private void findTileOnStraightLine(int x, int y, int dx, int dy, CheckState[][] checkMap) {
        if (board[y][x].isExists) {
            checkMap[y][x] = CheckState.EXISTS;
        } else {
            checkMap[y][x] = CheckState.CHECKED;
        }
        if (x + dx < columnNum && x + dx > -1 && y + dy < rowNum && y + dy > -1 && !board[y][x].isExists) {
            findTileOnStraightLine(x + dx, y + dy, dx, dy, checkMap);
        }
    }

    private CheckState[][] findDeletableTiles(CheckState[][] checkedMap) {
        Boolean isExistsDeletableTiles = false;
        CheckState[][] deletableTilesMap = new CheckState[rowNum][columnNum];
        HashMap<Tile.Type, Integer> typeMap = new HashMap<>();
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < columnNum; col++) {
                if (checkedMap[row][col] == CheckState.EXISTS) {
                    Tile.Type type = board[row][col].type;
                    int value = typeMap.getOrDefault(type, 0);
                    if (value > 0) isExistsDeletableTiles = true;
                    typeMap.put(type, value + 1);
                }
            }
        }

        if (!isExistsDeletableTiles) return null;

        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < columnNum; col++) {
                if (checkedMap[row][col] == CheckState.EXISTS) {
                    Tile.Type type = board[row][col].type;
                    int value = typeMap.getOrDefault(type, 0);
                    if (value > 1) {
                        deletableTilesMap[row][col] = CheckState.CHECKED;
                    }
                }
            }
        }

        return deletableTilesMap;
    }

    private void deleteTiles(CheckState[][] deletableTilesMap, int x, int y) {
        CheckState[][] foundRouteMap = new CheckState[rowNum][columnNum];
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < columnNum; col++) {
                if (deletableTilesMap[row][col] == CheckState.CHECKED) {
                    board[row][col].isExists = false;
                    findRoute(x, y, col, row, foundRouteMap);
                }
            }
        }

        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < columnNum; col++) {
                if (foundRouteMap[row][col] == CheckState.CHECKED) {
                    PointF pos = arrayIndexToWorld(new Point(col, row));
                    pos.x += tileSize / 2f;
                    pos.y += tileSize / 2f;
                    dotEffectSystem.add(pos);
                }
            }
        }
    }

    private void findRoute(int fromX, int fromY, int toX, int toY, CheckState[][] checkMap) {
        int dist = Math.max(Math.abs(fromX - toX), Math.abs(fromY - toY));
        PointF dir = new PointF(toX - fromX, toY - fromY);
        for (int i = 0; i <= dist; i++) {
            float ratio = (float)i / dist;
            Point p = new Point((int) (dir.x * ratio) + fromX, (int) (dir.y * ratio) + fromY);
            checkMap[p.y][p.x] = CheckState.CHECKED;
        }
    }

    private Boolean isExistsDeletableTiles() {
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < columnNum; col++) {
                if (board[row][col].isExists) continue;
                CheckState[][] foundTilesMap = findTiles(col, row);
                if (foundTilesMap == null) continue;
                CheckState[][] deletableTilesMap = findDeletableTiles(foundTilesMap);
                if (deletableTilesMap == null) continue;
                return true;
            }
        }
        return false;
    }
}
