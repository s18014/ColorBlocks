package com.example.colortile;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

public class Transform {
    private Transform parent;
    private List<Transform> children = new ArrayList<Transform>();
    private PointF position = new PointF(0f, 0f);
    private PointF localPosition = new PointF(0f, 0f);

    public PointF getPosition() {
        if (parent == null) return position;
        return parent.localToWorldPosition(localPosition);
    }

    public PointF getLocalPosition() {
        return localPosition;
    }

    public void setPosition(PointF p) {
        position = p;
        if (parent != null) {
            localPosition = parent.worldToLocalPosition(p);
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

    public PointF worldToLocalPosition(PointF p) {
        return new PointF(p.x - position.x, p.y - position.y);
    }

    public PointF localToWorldPosition(PointF p) {
        return new PointF(p.x + position.x, p.y + position.y);
    }


    private void addChild(Transform child) {
        this.children.add(child);
    }

    private void removeChild(Transform child) {
        this.children.remove(child);
    }
}
