package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView tvProgress;
    private EditText etSteps, etTrainingTime;
    private Button btnDone;
    private Handler handler = new Handler(Looper.getMainLooper());

    private int totalSeconds = 30;
    private int elapsedSeconds = 0;
    private boolean finished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        progressBar = findViewById(R.id.progressBar);
        tvProgress = findViewById(R.id.tvProgress);
        etSteps = findViewById(R.id.etSteps);
        etTrainingTime = findViewById(R.id.etTrainingTime);
        btnDone = findViewById(R.id.btnDone);

        progressBar.setMax(100);

        startProgressBar();


        btnDone.setOnClickListener(v -> {
            finished = true;
            handler.removeCallbacksAndMessages(null);
            Toast.makeText(this, "Bravo ! Session terminée !", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void startProgressBar() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (finished) return;

                elapsedSeconds++;
                int progress = (elapsedSeconds * 100) / totalSeconds;
                if (progress > 100) progress = 100;

                progressBar.setProgress(progress);
                tvProgress.setText("Temps restant : " + (totalSeconds - elapsedSeconds) + "s");

                if (elapsedSeconds < totalSeconds) {
                    handler.postDelayed(this, 1000);
                } else {
                    Toast.makeText(ThirdActivity.this, "Temps écoulé ! Retour à l'accueil.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }, 1000);
    }
}
