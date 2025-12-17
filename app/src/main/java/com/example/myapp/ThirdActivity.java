package com.example.myapp;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView tvInstruction;
    private View circle;
    private Button btnFinish;

    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean isPaused = false;
    private boolean userFinished = false;

    private int elapsed = 0;
    private int totalTime = 10;  // 5s inspire + 5s expire

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        progressBar = findViewById(R.id.progressBreathing);
        tvInstruction = findViewById(R.id.tvInstruction);
        circle = findViewById(R.id.breathCircle);
        btnFinish = findViewById(R.id.btnFinish);

        progressBar.setMax(100);

        startBreathingCycle();

        btnFinish.setOnClickListener(v -> {
            userFinished = true;
            handler.removeCallbacksAndMessages(null);
            Toast.makeText(this, "Exercice terminé ! 🎉", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void animateCircle(boolean inhale) {
        float scale = inhale ? 1.6f : 1.0f;

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(circle, "scaleX", scale);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(circle, "scaleY", scale);

        scaleX.setDuration(5000);
        scaleY.setDuration(5000);

        scaleX.start();
        scaleY.start();
    }

    private void startBreathingCycle() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userFinished) return;
                if (isPaused) {
                    handler.postDelayed(this, 500);
                    return;
                }

                elapsed++;
                int progress = elapsed * 100 / totalTime;
                progressBar.setProgress(progress);

                if (elapsed == 1) {
                    tvInstruction.setText("Inspirez…");
                    animateCircle(true);
                } else if (elapsed == 6) {
                    tvInstruction.setText("Expirez…");
                    animateCircle(false);
                }

                if (elapsed < totalTime) {
                    handler.postDelayed(this, 1000);
                } else {

                    //  Exercice terminé, message final
                    tvInstruction.setText("Bien joué ✨\nRetour à l’accueil dans 5 secondes…");
                    Toast.makeText(ThirdActivity.this,
                            "Exercice terminé",
                            Toast.LENGTH_SHORT).show();

                    //  attendre 3 secondes avant retour auto
                    handler.postDelayed(() -> {
                        if (!userFinished) {   // ⭐ si l’utilisateur n’a pas cliqué
                            Toast.makeText(ThirdActivity.this,
                                    "Retour à l'accueil…",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }, 5000);

                }
            }
        }, 500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;    // pause du timer et de l’animation
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPaused = false;   // reprise
    }
}
