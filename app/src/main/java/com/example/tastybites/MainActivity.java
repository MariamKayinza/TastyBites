package com.example.tastybites;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
 public static int SPLASH_SCREEN = 3000;
 BottomNavigationView bottomNavigationView;
    public class Constants {
        public static final String API_ENDPOINT = "http://192.168.180.190:8000";
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //removing the title bar
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // got to getstarted after 5 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, getstarted.class);
                startActivity(intent);
                finish();

            }
        }, SPLASH_SCREEN);

    }

}