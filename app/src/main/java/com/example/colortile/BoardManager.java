package com.example.colortile;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardManager extends GameObject {

    enum CheckState {
        NONE,
        CHECKED,
        EXISTS,
        PUTABLE,
        PATH
    }

    private Float width;
    private Float height;
    private int rowNum;
    private int columnNum;
    private boolean isPressing = false;
    private PointF pressingPoint;

    private Float tileSize;
    private Tile[][] board;
    private DotEffectSystem dotEffectSystem = new DotEffectSystem();

    BoardManager(int rowNum, int columnNum) {
        this.rowNum = rowNum;
        this.columnNum = columnNum;
    }

    @Override
    public void initialize() {
        dotEffectSystem.getTransform().setParent(getTransform());
        dotEffectSystem.init();
        createTiles();
    }

    @Override
    public void updateWindow(int width, int height) {
        setSize((float) width);
    }

    @Override
    public void update() {
        dotEffectSystem.update();
    }

    @Override
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

    public void setSize(Float width) {
        this.width = width;
        tileSize = width / columnNum;
        dotEffectSystem.setSize(tileSize / 7f);
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

    public void onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP: {
                isPressing = false;
                PointF p = new PointF(event.getX(), event.getY());
                Point index = worldToArrayIndex(p);
                if (index == null) return;
                CheckState[][] foundTilesMap = findTiles(index.x, index.y);
                if (foundTilesMap == null) return;
                CheckState[][] deletableTilesMap = findDeletableTiles(foundTilesMap);
                if (deletableTilesMap == null) return;
                deleteTiles(deletableTilesMap, index.x, index.y);
                break;
            }
            case MotionEvent.ACTION_DOWN: {
                isPressing = true;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                pressingPoint = new PointF(event.getX(), event.getY());
                break;
            }
        }
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

    private List<Point> findPutablePoint4way(int x, int y) {
        List<Point> pointList = new ArrayList<>();
        findPutablePoint(x, y, 0, 1, 1, 2, pointList);
        findPutablePoint(x, y, 0, -1, 1, 2, pointList);
        findPutablePoint(x, y, 1, 0, 1, 2, pointList);
        findPutablePoint(x, y, -1, 0, 1, 2, pointList);
        return pointList;
    }

    private void findPutablePoint(int x, int y, int dx, int dy, int branchCount, int ignoreCount, List<Point> pointList) {
        if (!board[y][x].isExists && ignoreCount <= 0) {
            if (pointList.indexOf(new Point(x, y)) == -1) {
                pointList.add(new Point(x, y));
            }
        }
        if (branchCount > 0 && ignoreCount <= 1) {
            PointF right = rotate2D(new PointF(dx, dy), -90);
            PointF left = rotate2D(new PointF(dx, dy), 90);
            findPutablePoint(x, y, (int) right.x, (int) right.y, branchCount - 1, 1, pointList);
            findPutablePoint(x, y, (int) left.x, (int) left.y, branchCount - 1, 1, pointList);
        }
        ignoreCount -= 1;
        if (x + dx < columnNum && x + dx > -1 && y + dy < rowNum && y + dy > -1 && !board[y][x].isExists) {
            findPutablePoint(x + dx, y + dy, dx, dy, branchCount, ignoreCount, pointList);
        }
    }

    private void createTiles() {
        board = new Tile[rowNum][columnNum];
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < columnNum; col++) {
                board[row][col] = new Tile();
                board[row][col].getTransform().setParent(getTransform());
                Tile.Type type = Tile.Type.NONE;
                board[row][col].init(type);
            }
        }

        List<Point> blankPoints = new ArrayList<>();
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < columnNum; col++) {
                blankPoints.add(new Point(col, row));
            }
        }

        Map<Tile.Type, Integer> restTilePears = new HashMap<>();
        restTilePears.put(Tile.Type.A, 10);
        restTilePears.put(Tile.Type.B, 10);
        restTilePears.put(Tile.Type.C, 10);
        restTilePears.put(Tile.Type.D, 10);
        restTilePears.put(Tile.Type.E, 10);
        for (Tile.Type type : restTilePears.keySet()) {
            for (int i = 0; i < restTilePears.get(type); i++) {
                System.out.println(blankPoints.size());
                int rand = (int) (Math.random() * (blankPoints.size()-1));
                Point a = blankPoints.get(rand);
                blankPoints.remove(rand);
                rand = (int) (Math.random() * (blankPoints.size()-1));
                Point b = blankPoints.get(rand);

                List<Point> putablePoints = findPutablePoint4way(a.x, a.y);
                if (putablePoints.size() > 0) {
                    rand = (int) (Math.random() * (putablePoints.size()-1));
                    b = putablePoints.get(rand);
                    int index = blankPoints.indexOf(b);
                    blankPoints.remove(index);
                } else {
                    blankPoints.remove(rand);
                }

                board[a.y][a.x].isExists = true;
                board[b.y][b.x].isExists = true;
                board[a.y][a.x].init(type);
                board[b.y][b.x].init(type);
            }
        }
    }

    private PointF rotate2D(PointF p, double angle) {
        double red = Math.toRadians(angle);
        double cos = red == 90 || red == 270 ? 0 : Math.cos(red);
        double sin = red == 90 || red == 270 ? 0 : Math.sin(red);
        float x = (float) (p.x * cos - p.y * sin);
        float y = (float) (p.x * sin + p.y * cos);
        return new PointF(x, y);
    }

    private void showDeletableTiles() {
        if (!isPressing || pressingPoint == null) return;
        Point index = worldToArrayIndex(pressingPoint);
        if (index == null) return;
        CheckState[][] foundTilesMap = findTiles(index.x, index.y);
        if (foundTilesMap == null) return;
        CheckState[][] deletableTilesMap = findDeletableTiles(foundTilesMap);
        if (deletableTilesMap == null) return;

        CheckState[][] foundRouteMap = new CheckState[rowNum][columnNum];
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < columnNum; col++) {
                if (deletableTilesMap[row][col] == CheckState.CHECKED) {
                    findRoute(index.x, index.y, col, row, foundRouteMap);
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
}
