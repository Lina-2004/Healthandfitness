package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity5 extends Activity implements View.OnClickListener {
    TextView tvName, tvAge, tvWeight, tvHeight, tvBMI, tvReco;
    Button btnClose; // permet de fermer Activity5 et retourner un résultat

    // champ pour garder l'IMC calculé et l'envoyer au clic
    private float bmiToReturn = -1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five); // charge le layout activity_five.xml

        tvName = findViewById(R.id.tvNameValue);
        tvAge = findViewById(R.id.tvAgeValue);
        tvWeight = findViewById(R.id.tvWeightValue);
        tvHeight = findViewById(R.id.tvHeightValue);
        tvBMI = findViewById(R.id.tvBMIValue);
        tvReco = findViewById(R.id.tvRecommendation);
        btnClose = findViewById(R.id.btnClose5);
        btnClose.setOnClickListener(this);

        // récupérer appel fait par Activity4 via Intent
        Intent myLocalIntent = getIntent();
        Bundle myBundle = myLocalIntent != null ? myLocalIntent.getExtras() : null;

        String name = null;
        int age = -1;
        float weight = 0f;
        int height = -1;

        if (myBundle != null) {
            name = myBundle.getString("val1");
            age = myBundle.getInt("val2", -1);
            weight = myBundle.getFloat("val3", 0f);
            height = myBundle.getInt("val4", -1);
        }

        tvName.setText(name != null ? name : "(inconnu)");
        tvAge.setText(age >= 0 ? String.valueOf(age) : "-");
        tvWeight.setText(weight > 0f ? String.valueOf(weight) : "-");
        tvHeight.setText(height > 0 ? String.valueOf(height) : "-");

        float roundedBmi = -1f;
        if (height > 0 && weight > 0f) {
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
            tvReco.setText("Taille ou poids invalides pour calcul IMC.");
        }

        // stocker l'IMC calculé dans le champ pour l'envoyer lors du clic
        bmiToReturn = roundedBmi;
    }

    // Envoi du résultat au clic
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnClose5) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("result", bmiToReturn);
            setResult(Activity.RESULT_OK, resultIntent);
            finish(); // ferme Activity5 et déclenche onActivityResult dans Activity4
        }
    }
}
