package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    private Button btnGoAdvice, btnGoProgress, btnGoInput,
            btnHydration, btnDailyObjective;

    // AJOUT 1 : Firebase Analytics
    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // AJOUT 2 : Initialisation Firebase Analytics
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        btnGoAdvice = findViewById(R.id.btnGoAdvice);
        btnGoProgress = findViewById(R.id.btnGoProgress);
        btnGoInput = findViewById(R.id.btnGoInput);
        btnHydration = findViewById(R.id.btnHydration);
        btnDailyObjective = findViewById(R.id.btnDailyObjective);

        // AJOUT 3 : Événement "écran affiché"
        logScreenEvent("MainActivity");

        btnGoAdvice.setOnClickListener(v -> {
            logButtonClick("btn_advice");
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        });

        btnGoProgress.setOnClickListener(v -> {
            logButtonClick("btn_progress");
            startActivity(new Intent(MainActivity.this, ThirdActivity.class));
        });

        btnGoInput.setOnClickListener(v -> {
            logButtonClick("btn_input");
            startActivity(new Intent(MainActivity.this, Activity4.class));
        });

        btnHydration.setOnClickListener(v -> {
            logButtonClick("btn_hydration");
            startActivity(new Intent(MainActivity.this, HydrationActivity.class));
        });

        btnDailyObjective.setOnClickListener(v -> {
            logButtonClick("btn_daily_objective");
            startActivity(new Intent(MainActivity.this, ActivityObjectif.class));
        });
    }

    // AJOUT 4 : Méthode événement écran
    private void logScreenEvent(String screenName) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName);
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity");

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }

    // AJOUT 5 : Méthode clic bouton
    private void logButtonClick(String buttonName) {
        Bundle bundle = new Bundle();
        bundle.putString("button_name", buttonName);

        firebaseAnalytics.logEvent("menu_button_click", bundle);
    }
}
