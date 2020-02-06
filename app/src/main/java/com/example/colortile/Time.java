package com.example.colortile;

public class Time {
    private static int frame = 0;
    private static long lastFrameTime;
    private static float deltaTime;
    private static float time;

    public static void initialize() {
        lastFrameTime = System.currentTimeMillis();
    }

    public static void update() {
        frame++;
        deltaTime = (System.currentTimeMillis() - lastFrameTime) / 1000f;
        time += deltaTime;
        lastFrameTime = System.currentTimeMillis();
        System.out.println("FPS: " + frame / time);
    }

    public static float getDeltaTime() {
        return deltaTime;
    }

    public static int getFrame() {
        return frame;
    }

    public static void onResume() {
        lastFrameTime = System.currentTimeMillis();
    }
}
