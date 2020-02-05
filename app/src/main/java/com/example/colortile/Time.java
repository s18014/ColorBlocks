package com.example.colortile;

public class Time {
    private static int frame = 0;
    private static long lastFrameTime;
    private static float deltaTime;

    public static void update() {
        frame++;
        if (frame == 1) {
            lastFrameTime = System.currentTimeMillis();
        }
        deltaTime = (System.currentTimeMillis() - lastFrameTime) / 1000f;
        lastFrameTime = System.currentTimeMillis();
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
