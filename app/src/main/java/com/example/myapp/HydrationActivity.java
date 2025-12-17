package com.example.myapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class HydrationActivity extends AppCompatActivity {

    private Button btnStartService, btnStopService, btnBackHome;
    private TextView txtStatus;
    private BroadcastReceiver hydrationReceiver;

    private static final int NOTIFICATION_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hydration);

        // 1) Demander la permission
        askNotificationPermission();

        // 2) Initialisation UI
        btnStartService = findViewById(R.id.btnStartHydration);
        btnStopService = findViewById(R.id.btnStopHydration);
        txtStatus = findViewById(R.id.txtHydrationStatus);
        btnBackHome = findViewById(R.id.btnBackHome);

        btnStartService.setOnClickListener(v -> {
            Intent intent = new Intent(this, HydrationService.class);
            intent.setAction(HydrationService.ACTION_START);
            startService(intent);
            txtStatus.setText("Rappel hydratation activé 💧");
        });


        btnStopService.setOnClickListener(v -> {
            Intent intent = new Intent(this, HydrationService.class);
            intent.setAction(HydrationService.ACTION_STOP);
            startService(intent);
            txtStatus.setText("Rappel hydratation arrêté");
        });


        // 3) Receiver interne
        hydrationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra("msg");
                txtStatus.setText(msg);
            }
        };

        // 4) Enregistrement Receiver
        registerReceiver(
                hydrationReceiver,
                new IntentFilter("SANTE_EAU"),
                RECEIVER_NOT_EXPORTED
        );

        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(HydrationActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });


    }

    // DEMANDE DE PERMISSION NOTIFICATION (Android 13+)
    private void askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        NOTIFICATION_PERMISSION_CODE
                );
            }
        }
    }

    // Résultat de la permission
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // OK
            } else {
                txtStatus.setText("Permission notifications refusée!!");
            }
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(hydrationReceiver);
        super.onDestroy();
    }
}
