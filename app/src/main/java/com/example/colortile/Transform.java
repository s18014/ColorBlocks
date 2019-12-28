package com.example.colortile;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

public class Transform {
    Transform parent;
    List<Transform> children = new ArrayList<Transform>();
    private PointF position = new PointF(0f, 0f);
    private PointF localPosition = new PointF(0f, 0f);

    public PointF getPosition() {
        if (parent == null) return position;
        PointF parentPos = parent.getPosition();
        return new PointF(parentPos.x + localPosition.x, parentPos.y + localPosition.y);
    }

    public PointF getLocalPosition() {
        return localPosition;
    }

    public void setPosition(PointF p) {
        position = p;
        if (parent != null) {
            PointF parentPos = parent.getPosition();
            localPosition = new PointF(p.x - parentPos.x, p.y - parentPos.y);
        }
    }

    public void setLocalPosition(PointF p) {
        localPosition = p;
    }

    public void setParent(Transform parent) {
        parent.addChild(this);
        this.parent = parent;
    }

    public void unsetParent() {
        this.parent.removeChild(this);
        this.parent = null;
    }

    private void addChild(Transform child) {
        this.children.add(child);
    }

    private void removeChild(Transform child) {
        this.children.remove(child);
    }
}
