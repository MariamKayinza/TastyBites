package com.example.tastybites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class getstarted extends AppCompatActivity {

    Button btn_get_started;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getstarted);

        // change the status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.toolbarIconColor));

        btn_get_started = findViewById(R.id.btn_get_started);
        btn_get_started.setOnClickListener(v -> {
            Intent intent = new Intent(getstarted.this, Home.class);
            startActivity(intent);
            finish();
        });

    }
}