package com.example.colortile;

public abstract class GameObject {
    protected Transform transform = new Transform();

    public Transform getTransform() {
        return transform;
    }
}
