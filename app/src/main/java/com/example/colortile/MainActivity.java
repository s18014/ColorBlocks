package com.example.colortile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements
        IFragmentChanger,
        GameFragment.OnFragmentInteractionListener,
        TitleFragment.OnFragmentInteractionListener,
        ResultFragment.OnFragmentInteractionListener
{
    static int FLAGS = View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFlags();
        setMargin();
        changeFragment(new TitleFragment());
    }

    @Override
    public void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment).commit();
    }

    // TODO: フラグメントをポップアップ表示したい
    @Override
    public void pushFragment(Fragment fragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container,fragment).commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFlags();
    }

    private void setFlags() {
        getWindow().getDecorView().setSystemUiVisibility(FLAGS);
    }

    private void setMargin() {
        int height = getNavigationBarHeight();
        FrameLayout frameLayout = findViewById(R.id.container);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) frameLayout.getLayoutParams();
        params.setMargins(0, height, 0, height);
        frameLayout.setLayoutParams(params);
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
