package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnGoAdvice, btnGoProgress, btnGoInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGoAdvice = findViewById(R.id.btnGoAdvice);
        btnGoProgress = findViewById(R.id.btnGoProgress);
        btnGoInput = findViewById(R.id.btnGoInput);

        btnGoAdvice.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

        btnGoProgress.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
            startActivity(intent);
        });

        btnGoInput.setOnClickListener(v -> {

            try {
                Intent intent = new Intent(MainActivity.this, Activity4.class);
                startActivity(intent);
                return;
            } catch (Throwable ignored) { }


        });
    }
}
