package com.example.colorblocks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;

public class TileFadeOutEffectSystem extends GameObject{
    static int size = 100;

    private final TileFadeOutEffect[] effects = new TileFadeOutEffect[size];

    public TileFadeOutEffectSystem(Context context) {
        super(context);
    }

    @Override
    public void initialize() {
        super.initialize();
        for (int i = 0; i < effects.length; i++) {
            effects[i] = new TileFadeOutEffect(context);
            effects[i].getTransform().setParent(getTransform());
            effects[i].initialize();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        for (int i = 0; i < effects.length; i++) {
            if (effects[i].isExists) {
                effects[i].draw(canvas);
            }
        }
    }

    @Override
    public void update() {
        super.update();
        for (int i = 0; i < effects.length; i++) {
            if (effects[i].isExists) {
                effects[i].update();
            }
        }
    }

    public void setSize(float size) {
        for (int i = 0; i < effects.length; i++) {
            effects[i].setSize(size);
        }
    }

    public void add(PointF position, int color) {
        for (int i = 0; i < effects.length; i++) {
            if (!effects[i].isExists) {
                effects[i].initialize();
                effects[i].isExists = true;
                effects[i].setColor(color);
                effects[i].setPosition(position);
                return;
            }
        }
    }
}
