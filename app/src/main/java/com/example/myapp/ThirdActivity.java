package com.example.myapp;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private TextView tvProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        progressBar = findViewById(R.id.progressBar);
        tvProgress = findViewById(R.id.tvProgress);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressStatus = 0;
        new Thread(() -> {
            while (progressStatus < 100) {
                progressStatus += 1;
                handler.post(() -> {
                    progressBar.setProgress(progressStatus);
                    tvProgress.setText("Fitness Progress: " + progressStatus + "%");
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
