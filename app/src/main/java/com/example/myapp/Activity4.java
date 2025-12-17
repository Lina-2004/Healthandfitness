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

    private static final int REQ_CODE_DETAILS = 101;

    EditText etName, etAge, etWeight, etHeight;
    Button btnSend, btnBackMain;
    TextView tvResultFrom5;
    TextView lblResult;

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
        lblResult = (TextView) findViewById(R.id.lblResult);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // obtenir des valeurs de l'interface utilisateur
                String name = etName.getText().toString().trim();
                int age = Integer.parseInt(etAge.getText().toString().trim());
                Float weight = Float.parseFloat(etWeight.getText().toString().trim());
                int height = Integer.parseInt(etHeight.getText().toString().trim());


                // créer intent pour appeler Activity5
                Intent intent = new Intent(Activity4.this, Activity5.class);

                // créer un conteneur pour envoyer des données
                Bundle myData = new Bundle();

                // ajouter des éléments données <key,value> au conteneur
                myData.putString("val1",name);
                myData.putInt("val2",age);
                myData.putFloat("val3",weight);
                myData.putInt("val4",height);

                // attacher le conteneur à l'intent
                intent.putExtras(myData);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_DETAILS) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                float result = data.getFloatExtra("result", Float.MIN_VALUE);
                if (result != Float.MIN_VALUE) {
                    lblResult.setText("IMC = " + result);

                } else {
                    lblResult.setText("IMC introuvable.");
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                lblResult.setText("Calcul annulé.");
            } else {
                lblResult.setText("Aucun résultat reçu.");
            }
        }
    }
}

