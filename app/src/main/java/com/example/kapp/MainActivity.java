package com.example.kapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.kapp.adapter.MainFragmentAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ViewPager vp;
    private BottomNavigationView bnv;
    private MainFragmentAdapter fadt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        vp = findViewById(R.id.main_vp);
        bnv = findViewById(R.id.nav);
        fadt = new MainFragmentAdapter(getSupportFragmentManager(),3);
        vp.setAdapter(fadt);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0: {
                        bnv.getMenu().findItem(R.id.nav_lib).setChecked(true);
                        break;
                    }
                    case 1: {
                        bnv.getMenu().findItem(R.id.nav_book).setChecked(true);
                        break;
                    }
                    case 2: {
                        bnv.getMenu().findItem(R.id.nav_acc).setChecked(true);
                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_lib:{
                        vp.setCurrentItem(0);
                        break;
                    }
                    case R.id.nav_book:{
                        vp.setCurrentItem(1);
                        break;
                    }
                    case R.id.nav_acc:{
                        vp.setCurrentItem(2);
                        break;
                    }
                }
                return false;
            }
        });
    }
}