package com.example.colorblocks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.View;

public class CnvButton extends GameObject {
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
    private String text = "";
    private Integer textSize = 0;
    private int textColor = Color.BLACK;

    private SoundPool soundPool;
    private int clickSound;


    public CnvButton(Context context, int btnImageId, float x, float y, float width, float height) {
        super(context);
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        // 当たり判定用
        rectF = new RectF(x, y, x + width, y + height);

        // 生の画像サイズ
        Bitmap beforeResizeBitmap = BitmapFactory.decodeResource(context.getResources(), btnImageId);

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


        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                // USAGE_MEDIA
                // USAGE_GAME
                .setUsage(AudioAttributes.USAGE_GAME)
                // CONTENT_TYPE_MUSIC
                // CONTENT_TYPE_SPEECH, etc.
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();

        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                // ストリーム数に応じて
                .setMaxStreams(2)
                .build();


        clickSound = soundPool.load(context, R.raw.button06, 1);
    }


    @Override
    public void initialize() {

   }

    @Override
    public void finalize() {

    }

    @Override
    public void update() {
        if (!visible) return;
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
                        soundPool.play(clickSound, 1, 1, 0, 0, 1);
                    }
                    pressed = false;
                    break;
            }

        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(btnBitmap, translate.x + x, translate.y + y, null);

        if (text != null) {
            // 文字を表示
            float centerX = x + translate.x + btnBitmap.getWidth() / 2;
            float centerY = y + translate.y + btnBitmap.getHeight() / 2;

            // テキスト用ペイントの生成
            Paint textPaint = new Paint( Paint.ANTI_ALIAS_FLAG);
            textPaint.setTextSize(textSize);
            textPaint.setColor( textColor);

            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();

            // 文字列の幅を取得
            float textWidth = textPaint.measureText( text);

            // 中心にしたいX座標から文字列の幅の半分を引く
            float baseX = centerX - textWidth / 2;
            // 中心にしたいY座標からAscentとDescentの半分を引く
            float baseY = centerY - (fontMetrics.ascent + fontMetrics.descent) / 2;

            // テキストの描画
            canvas.drawText( text, baseX, baseY, textPaint);
        }

        if (pressed) {
            paint.setColor(Color.argb(30, 50, 50, 50));
            canvas.drawRect(blindRectF, paint);
        }
    }

    public void setText(String text, int size, int color) {
        this.text = text;
        this.textSize = size;
        this.textColor = color;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setVisible(boolean visible)  {
        this.visible = visible;
    }
}
