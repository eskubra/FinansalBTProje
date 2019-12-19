package com.bestWallpapers.hamza.bestWallpaperss.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.bestWallpapers.hamza.bestWallpaperss.R;
import com.bestWallpapers.hamza.bestWallpaperss.activities.fragments.Home_Fragment;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        displayFragment(new Home_Fragment());
    }

    private void displayFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_area, fragment)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                fragment = new Home_Fragment();
                break;
            case R.id.nav_fav:
                fragment = new Home_Fragment();
                break;
            case R.id.nav_set:
                fragment = new Home_Fragment();
                break;
            default:
                fragment = new Home_Fragment();
                break;
        }
        displayFragment(fragment);
        return true;
    }
}
