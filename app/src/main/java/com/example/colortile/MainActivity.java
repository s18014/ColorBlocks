package com.example.colortile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements GameFragment.OnFragmentInteractionListener, TitleFragment.OnFragmentInteractionListener {
    static int FLAGS = View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

    // private GameManager gameManager;
    private GameFragment gameFragment;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFlags();
        setMargin();
        /*
        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        gameManager = new GameManager(surfaceView);
         */
        Fragment gameFragment = new TitleFragment();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, gameFragment)
            .addToBackStack(null)
            .commit();
    }

    public void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // gameManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFlags();
        // gameManager.onResume();
    }

    private void setFlags() {
        getWindow().getDecorView().setSystemUiVisibility(FLAGS);
    }

    private void setMargin() {
        int height = getNavigationBarHeight();
        FrameLayout surfaceView = findViewById(R.id.container);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
