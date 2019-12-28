package com.example.colortile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {
    static int FLAGS = View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFlags();
        setMargin();
        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        gameManager = new GameManager(surfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFlags();
        gameManager.onResume();
    }

    private void setFlags() {
        getWindow().getDecorView().setSystemUiVisibility(FLAGS);
    }

    private void setMargin() {
        int height = getNavigationBarHeight();
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) surfaceView.getLayoutParams();
        params.setMargins(0, height, 0, height);
        surfaceView.setLayoutParams(params);
    }

    private int getNavigationBarHeight() {
        Display display = getWindowManager().getDefaultDisplay();
        Point viewP = new Point();
        Point realP = new Point();
        display.getSize(viewP);
        display.getRealSize(realP);
        return realP.y - viewP.y;
    }
}
