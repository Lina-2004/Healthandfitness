package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityObjectif extends Activity {

    EditText etTime, etDistance;
    Button btnSendObjectif, btnBackMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectif);

        etTime = findViewById(R.id.etTime);
        etDistance = findViewById(R.id.etDistance);
        btnSendObjectif = findViewById(R.id.btnSendObjectif);
        btnBackMain = findViewById(R.id.btnBackMain);

        btnSendObjectif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String time = etTime.getText().toString().trim();
                String distance = etDistance.getText().toString().trim();

                if (time.isEmpty() || distance.isEmpty()) {
                    Toast.makeText(ActivityObjectif.this,
                            "Veuillez remplir tous les champs",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Création de l'intent
                Intent intent = new Intent(ActivityObjectif.this,
                        ActivityObjectifResume.class);

                // Création du Bundle
                Bundle bundle = new Bundle();
                bundle.putString("time", time);
                bundle.putString("distance", distance);

                // Attacher le bundle
                intent.putExtras(bundle);

                // Lancer SANS attendre de résultat
                startActivity(intent);
            }
        });

        btnBackMain.setOnClickListener(v -> {
            startActivity(new Intent(ActivityObjectif.this, MainActivity.class));
            finish();
        });
    }
}
