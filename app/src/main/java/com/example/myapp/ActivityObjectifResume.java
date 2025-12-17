package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ActivityObjectifResume extends Activity {

    TextView tvTime, tvDistance, tvMessage;
    Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectif_resume);

        tvTime = findViewById(R.id.tvTimeValue);
        tvDistance = findViewById(R.id.tvDistanceValue);
        tvMessage = findViewById(R.id.tvMessage);
        btnClose = findViewById(R.id.btnCloseResume);

        // Récupération des données
        Intent intent = getIntent();
        Bundle bundle = intent != null ? intent.getExtras() : null;

        String time = "-";
        String distance = "-";

        if (bundle != null) {
            time = bundle.getString("time", "-");
            distance = bundle.getString("distance", "-");
        }

        tvTime.setText(time + " minutes");
        tvDistance.setText(distance + " km");

        tvMessage.setText("Objectif enregistré ✔\nBonne marche ! 🚶‍♂️");

        btnClose.setOnClickListener(v -> {
            finish();
        });
    }
}
