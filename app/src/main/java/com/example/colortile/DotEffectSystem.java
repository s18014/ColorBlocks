package com.example.colortile;

import android.graphics.Canvas;
import android.graphics.PointF;

public class DotEffectSystem extends GameObject{
    static int size = 100;

    private final DotEffect[] effects = new DotEffect[size];

    public void init() {
        for (int i = 0; i < effects.length; i++) {
            effects[i] = new DotEffect();
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

    public void draw(Canvas canvas) {
        for (int i = 0; i < effects.length; i++) {
            if (effects[i].isExists) {
                effects[i].draw(canvas);
            }
        }
    }

    public void update() {
        for (int i = 0; i < effects.length; i++) {
            if (effects[i].isExists) {
                effects[i].update();
            }
        }

    }
}
