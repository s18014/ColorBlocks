package com.example.colortile;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.preference.PreferenceManager;

public class DotEffectSystem extends GameObject{
    static int size = 100;

    private final DotEffect[] effects = new DotEffect[size];

    DotEffectSystem(Context context) {
        super(context);
    }

    @Override
    public void initialize() {
        super.initialize();
        for (int i = 0; i < effects.length; i++) {
            effects[i] = new DotEffect(context);
            effects[i].getTransform().setParent(getTransform());
        }
    }

    public void setSize(float size) {
        for (int i = 0; i < effects.length; i++) {
            effects[i].setSize(size);
        }
    }

    public void add(PointF position) {
        for (int i = 0; i < effects.length; i++) {
            if (!effects[i].isExists) {
                effects[i].isExists = true;
                effects[i].init(position);
                return;
            }
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
}
