package com.example.linktothepastgpsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HyruleCastle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hyrule_castle);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            TextView context = findViewById(R.id.contextText);
            TextView mission = findViewById(R.id.missionText);

            context.setText(extras.getString("context"));
            mission.setText(extras.getString("mission"));
        }
    }
}