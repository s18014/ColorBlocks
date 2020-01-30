package com.example.colortile;

public abstract class GameObject implements ITask {
    private Transform transform = new Transform();

    public Transform getTransform() {
        return transform;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void finalize() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {

    }
}
