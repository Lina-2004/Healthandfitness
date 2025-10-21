package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Activity4 extends Activity {


    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_AGE = "KEY_AGE";
    public static final String KEY_WEIGHT = "KEY_WEIGHT";
    public static final String KEY_HEIGHT = "KEY_HEIGHT";

    private static final int REQ_CODE_DETAILS = 2001;

    EditText etName, etAge, etWeight, etHeight;
    Button btnSend, btnBackMain;
    TextView tvResultFrom5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        btnSend = findViewById(R.id.btnSendTo5);
        btnBackMain = findViewById(R.id.btnBackMain);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString().trim();
                String sAge = etAge.getText().toString().trim();
                String sWeight = etWeight.getText().toString().trim();
                String sHeight = etHeight.getText().toString().trim();

                if (name.isEmpty() || sAge.isEmpty() || sWeight.isEmpty() || sHeight.isEmpty()) {
                    Toast.makeText(Activity4.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                int age;
                float weight;
                int height;
                try {
                    age = Integer.parseInt(sAge);
                    weight = Float.parseFloat(sWeight);
                    height = Integer.parseInt(sHeight);
                } catch (NumberFormatException e) {
                    Toast.makeText(Activity4.this, "Vérifiez les formats numériques (âge, poids, taille)", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Préparer l'intent pour Activity5
                Intent intent = new Intent(Activity4.this, Activity5.class);
                intent.putExtra(KEY_NAME, name);
                intent.putExtra(KEY_AGE, age);
                intent.putExtra(KEY_WEIGHT, weight);
                intent.putExtra(KEY_HEIGHT, height);
                intent.putExtra("KEY_TIMESTAMP", System.currentTimeMillis());

                //lance Activity5 et attend un résultat
                startActivityForResult(intent, REQ_CODE_DETAILS);
            }
        });

        //Retourne à MainActivity avec startActivity(intent) et ferme Activity4 avec finish()
        btnBackMain.setOnClickListener(v -> {
            Intent intent = new Intent(Activity4.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // callback appelé quand Activity5 renvoie un résultat (setResult...)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // vérifier que c'est bien ma requête
        if (requestCode == REQ_CODE_DETAILS) {
            if (resultCode == RESULT_OK && data != null) {

                // extra qui contient le message/valeur retournée
                String retourMsg = data.getStringExtra("KEY_RETURN_MSG");
                float returnedBmi = data.getFloatExtra("RESULT_BMI", -1f);

                // afficher ou traiter le résultat
                if (tvResultFrom5 != null) {
                    String show = (retourMsg != null ? retourMsg : "Résultat reçu");
                    if (returnedBmi > 0) show += " — IMC: " + returnedBmi;
                    tvResultFrom5.setText(show);
                } else {


                    String toast = (retourMsg != null ? retourMsg : "Données reçues");
                    if (returnedBmi > 0) toast += " (IMC: " + returnedBmi + ")";
                    Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Action annulée par l'utilisateur", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Pas de résultat reçu", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
