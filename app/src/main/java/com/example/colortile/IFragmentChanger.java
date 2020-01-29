package com.example.colortile;

import androidx.fragment.app.Fragment;

public interface IFragmentChanger {
    void changeFragment(Fragment fragment);
    void pushFragment(Fragment fragment);
}
