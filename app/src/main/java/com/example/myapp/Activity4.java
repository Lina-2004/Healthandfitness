package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity4 extends Activity {

    EditText etName, etAge, etWeight, etHeight;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);

        etName = (EditText) findViewById(R.id.etName);
        etAge = (EditText) findViewById(R.id.etAge);
        etWeight = (EditText) findViewById(R.id.etWeight);
        etHeight = (EditText) findViewById(R.id.etHeight);
        btnSend = (Button) findViewById(R.id.btnSendTo5);

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

                int age = 0;
                float weight = 0f;
                int height = 0;
                try {
                    age = Integer.parseInt(sAge);
                    weight = Float.parseFloat(sWeight);
                    height = Integer.parseInt(sHeight);
                } catch (NumberFormatException e) {
                    Toast.makeText(Activity4.this, "Vérifiez les formats numériques (âge, poids, taille)", Toast.LENGTH_SHORT).show();
                    return;
                }


                Intent intent = new Intent(Activity4.this, Activity5.class);
                Bundle b = new Bundle();
                b.putString("KEY_NAME", name);
                b.putInt("KEY_AGE", age);
                b.putFloat("KEY_WEIGHT", weight);
                b.putInt("KEY_HEIGHT", height);

                b.putLong("KEY_TIMESTAMP", System.currentTimeMillis());

                intent.putExtras(b);

                startActivity(intent);
            }
        });
        Button btnBackMain = findViewById(R.id.btnBackMain);
        btnBackMain.setOnClickListener(v -> {
            Intent intent = new Intent(Activity4.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }


}

