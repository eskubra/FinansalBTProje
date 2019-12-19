package com.bestWallpapers.hamza.bestWallpaperss.activities.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();


    }


}
