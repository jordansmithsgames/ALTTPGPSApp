package com.example.linktothepastgpsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class GanonsTower extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganons_tower);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            TextView context = findViewById(R.id.contextText3);
            TextView mission = findViewById(R.id.missionText3);

            context.setText(extras.getString("context"));
            mission.setText(extras.getString("mission"));
        }
    }
}