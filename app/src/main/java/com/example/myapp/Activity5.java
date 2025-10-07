package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity5 extends Activity {

    TextView tvName, tvAge, tvWeight, tvHeight, tvBMI, tvReco;
    Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);

        tvName = (TextView) findViewById(R.id.tvNameValue);
        tvAge = (TextView) findViewById(R.id.tvAgeValue);
        tvWeight = (TextView) findViewById(R.id.tvWeightValue);
        tvHeight = (TextView) findViewById(R.id.tvHeightValue);
        tvBMI = (TextView) findViewById(R.id.tvBMIValue);
        tvReco = (TextView) findViewById(R.id.tvRecommendation);
        btnClose = (Button) findViewById(R.id.btnClose5);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b != null) {
            String name = b.getString("KEY_NAME", "(inconnu)");
            int age = b.getInt("KEY_AGE", -1);
            float weight = b.getFloat("KEY_WEIGHT", 0f);
            int height = b.getInt("KEY_HEIGHT", 0);

            tvName.setText(name);
            tvAge.setText(age >= 0 ? String.valueOf(age) : "-");
            tvWeight.setText(String.valueOf(weight));
            tvHeight.setText(height > 0 ? String.valueOf(height) : "-");

            if (height > 0) {
                float hMeters = height / 100.0f;
                float bmi = weight / (hMeters * hMeters);
                float roundedBmi = Math.round(bmi * 10f) / 10f;
                tvBMI.setText(String.valueOf(roundedBmi));

                String reco = "";
                if (roundedBmi < 18.5f) {
                    reco = "IMC: maigre — Reco: add calories / renforcement léger.";
                } else if (roundedBmi < 25f) {
                    reco = "IMC: normal — Reco: maintenir activité (30 min/jour).";
                } else if (roundedBmi < 30f) {
                    reco = "IMC: surpoids — Reco: cardio modéré + régime.";
                } else {
                    reco = "IMC: obésité — Reco: consulter un spécialiste / programme supervisé.";
                }
                tvReco.setText(reco);
            } else {
                tvBMI.setText("-");
                tvReco.setText("Taille invalide pour calcul IMC.");
            }
        } else {
            tvName.setText("(aucune donnée)");
            tvAge.setText("-");
            tvWeight.setText("-");
            tvHeight.setText("-");
            tvBMI.setText("-");
            tvReco.setText("Aucune donnée reçue.");
        }

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
