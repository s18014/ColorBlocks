package com.example.colortile;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

public class CnvButton implements ITask {
    private Paint paint = new Paint();
    private Bitmap btnBitmap;
    private RectF blindRectF;
    private boolean enable = true;
    private boolean visible = true;
    private boolean pressed = false;
    private float width;
    private float height;
    private float x;
    private float y;
    private PointF translate;
    private RectF rectF;
    private View.OnClickListener listener;

    CnvButton(Resources res, int btnImageId, float x, float y, float width, float height) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        // 当たり判定用
        rectF = new RectF(x, y, x + width, y + height);

        // 生の画像サイズ
        Bitmap beforeResizeBitmap = BitmapFactory.decodeResource(res, btnImageId);

        // リサイズ比
        // 当たり判定より大きくならないように小さい比率を取得
        float resizeScale;
        float scaleX = width / beforeResizeBitmap.getWidth();
        float scaleY = height / beforeResizeBitmap.getHeight();
        resizeScale = Math.min(scaleX, scaleY);
        btnBitmap = Bitmap.createScaledBitmap(beforeResizeBitmap,
                (int) (beforeResizeBitmap.getWidth() * resizeScale),
                (int) (beforeResizeBitmap.getHeight() * resizeScale),
                true);

        // 画像を判定内の中央に配置
        translate = new PointF((width - btnBitmap.getWidth()) / 2f, (height - btnBitmap.getHeight()) / 2f);

        blindRectF = new RectF(x + translate.x, y + translate.y, x + translate.x + btnBitmap.getWidth(), y + translate.y + btnBitmap.getHeight());
    }


    @Override
    public void initialize() {

    }

    @Override
    public void finalize() {

    }

    @Override
    public void update() {
        rectF = new RectF(x, y, x + width, y + height);
        MyMotionEvent event = Input.getEvent();
        if (event != null) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!rectF.contains(event.getX(), event.getY())) return;
                    pressed = true;
                    break;
                case MotionEvent.ACTION_UP:
                    if (rectF.contains(event.getX(), event.getY()) && pressed) {
                        if (listener != null) listener.onClick(null);
                    }
                    pressed = false;
                    break;
            }

        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(btnBitmap, translate.x + x, translate.y + y, null);
        if (pressed) {
            paint.setColor(Color.argb(20, 50, 50, 50));
            canvas.drawRect(blindRectF, paint);
        }
        if (ScreenSettings.getDisplayMode() == ScreenSettings.DISPLAY_MODE.DEBUG) {
            paint.setColor(Color.argb(50, 50, 50, 50));
            canvas.drawRect(rectF, paint);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}
