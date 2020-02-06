package com.example.colortile;

public class Time {
    private static int frame = 0;
    private static long lastFrameTime;
    private static float deltaTime;
    private static int fpsFrame = 0;
    private static float fpsTime;

    public static void initialize() {
        lastFrameTime = System.currentTimeMillis();
    }

    public static void update() {
        if (fpsFrame > 60) {
            fpsFrame = 0;
            fpsTime = 0;
        }
        fpsFrame++;
        frame++;
        deltaTime = (System.currentTimeMillis() - lastFrameTime) / 1000f;
        fpsTime += deltaTime;
        lastFrameTime = System.currentTimeMillis();
    }

    public static float getDeltaTime() {
        return deltaTime;
    }

    public static int getFrame() {
        return frame;
    }

    public static float getFps() {
        return fpsFrame / fpsTime;
    }

    public static void onResume() {
        lastFrameTime = System.currentTimeMillis();
    }
}
