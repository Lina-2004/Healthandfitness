package com.example.myapp;

import android.app.Application;
import android.content.Intent;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Lancer le service automatiquement
        Intent serviceIntent = new Intent(this, HydrationService.class);
        startService(serviceIntent);
    }
}
