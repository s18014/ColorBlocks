package com.example.colortile;

import android.content.Context;
import android.content.SharedPreferences;

public class Score {
    private static int score;
    private Context context;

    public Score(Context context) {
        this.context = context;
    }

    public static void initialize() {
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        SharedPreferences preferences = context.getSharedPreferences("Score", Context.MODE_PRIVATE);
        int highScore = preferences.getInt("Score", 0);
        Score.score = score;
        if (score > highScore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("Score", score);
            editor.apply();
        }
    }

    public int getHighScore() {
        SharedPreferences preferences = context.getSharedPreferences("Score", Context.MODE_PRIVATE);
        return preferences.getInt("Score", 0);
    }
}
