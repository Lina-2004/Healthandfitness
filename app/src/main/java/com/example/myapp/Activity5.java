package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity5 extends Activity {

    TextView tvName, tvAge, tvWeight, tvHeight, tvBMI, tvReco;
    Button btnClose; //permet de fermer Activity5 et retourner un résultat

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);//charge le layout activity_five.xml

        tvName = findViewById(R.id.tvNameValue);
        tvAge = findViewById(R.id.tvAgeValue);
        tvWeight = findViewById(R.id.tvWeightValue);
        tvHeight = findViewById(R.id.tvHeightValue);
        tvBMI = findViewById(R.id.tvBMIValue);
        tvReco = findViewById(R.id.tvRecommendation);
        btnClose = findViewById(R.id.btnClose5);

        Intent intent = getIntent();
        if (intent == null) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }


        String name = intent.getStringExtra("KEY_NAME");
        int age = intent.getIntExtra("KEY_AGE", -1);
        float weight = intent.getFloatExtra("KEY_WEIGHT", 0f);
        int height = intent.getIntExtra("KEY_HEIGHT", 0);

        tvName.setText(name != null ? name : "(inconnu)");
        tvAge.setText(age >= 0 ? String.valueOf(age) : "-");
        tvWeight.setText(String.valueOf(weight));
        tvHeight.setText(height > 0 ? String.valueOf(height) : "-");

        float roundedBmi = -1f;
        if (height > 0) {
            float hMeters = height / 100.0f;
            float bmi = weight / (hMeters * hMeters);
            roundedBmi = Math.round(bmi * 10f) / 10f;
            tvBMI.setText(String.valueOf(roundedBmi));

            String reco;
            if (roundedBmi < 18.5f) {
                reco = "IMC: maigre — Reco: augmenter l'apport calorique.";
            } else if (roundedBmi < 25f) {
                reco = "IMC: normal — Reco: maintenir l'activité (30 min/jour).";
            } else if (roundedBmi < 30f) {
                reco = "IMC: surpoids — Reco: cardio modéré + régime.";
            } else {
                reco = "IMC: obésité — Reco: consulter un spécialiste.";
            }
            tvReco.setText(reco);
        } else {
            tvBMI.setText("-");
            tvReco.setText("Taille invalide pour calcul IMC.");
        }


        float finalBmiToReturn = roundedBmi;
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent resultIntent = new Intent();
                resultIntent.putExtra("KEY_RETURN_MSG", "Détails reçus avec succès");
                if (finalBmiToReturn > 0) {
                    resultIntent.putExtra("RESULT_BMI", finalBmiToReturn);
                }

                setResult(RESULT_OK, resultIntent);
                finish(); // ferme Activity5 et déclenche onActivityResult dans Activity4
            }
        });
    }
}
