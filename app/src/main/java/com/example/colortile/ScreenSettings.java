package com.example.colortile;

public class ScreenSettings {

    enum DISPLAY_MODE {
        NORMAL,
        DEBUG
    }

    static void setWindowSize(float w, float h) {
    }

    static float getWidth() {
        return 720f;
    }

    static float getHeight() {
        return 1280f;
    }

    static DISPLAY_MODE getDisplayMode() {
        return DISPLAY_MODE.NORMAL;
    }
}
