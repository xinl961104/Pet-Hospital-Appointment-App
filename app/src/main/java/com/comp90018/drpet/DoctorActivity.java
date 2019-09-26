package com.comp90018.drpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DoctorActivity extends AppCompatActivity {

    TextView hospitalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        hospitalTextView = findViewById(R.id.hospitalTextView);

        Intent intent = getIntent();
        String hospitalName = intent.getStringExtra("hospitalName");
        hospitalTextView.setText(hospitalName);
    }
}
