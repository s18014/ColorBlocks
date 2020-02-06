package com.example.colortile;

public class ScreenSettings {

    public enum DISPLAY_MODE {
        NORMAL,
        DEBUG
    }

    public static void setWindowSize(float w, float h) {
    }

    public static float getWidth() {
        return 720f;
    }

    public static float getHeight() {
        return 1280f;
    }

    public static DISPLAY_MODE getDisplayMode() {
        return DISPLAY_MODE.DEBUG;
    }
}
