package com.example.kapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.kapp.fragment.FragmentAcc;
import com.example.kapp.fragment.FragmentCab;
import com.example.kapp.fragment.FragmentLib;

public class MainFragmentAdapter extends FragmentPagerAdapter {
    int behavior;
    public MainFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.behavior = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return new FragmentLib();
            case 1: return new FragmentCab();
            case 2: return new FragmentAcc();
        }
        return null;
    }

    @Override
    public int getCount() {
        return behavior;
    }
}
