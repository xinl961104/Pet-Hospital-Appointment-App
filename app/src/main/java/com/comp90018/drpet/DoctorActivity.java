package com.comp90018.drpet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DoctorActivity extends AppCompatActivity {

    TextView hospitalTextView;
    ImageView hospitalImageView;
    RecyclerView doctorRecyclerView;
    TextView aboutTextView;
    TextView openHoursTextView;
    Button callButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        hospitalTextView = findViewById(R.id.hospitalTextView);
        hospitalImageView = findViewById(R.id.hospitalImageView);
        doctorRecyclerView = findViewById(R.id.doctorRecyclerView);
        aboutTextView = findViewById(R.id.aboutTextView);
        openHoursTextView = findViewById(R.id.openHoursTextView);
        callButton = findViewById(R.id.callButton);

        Intent intent = getIntent();
        String hospitalName = intent.getStringExtra("hospitalName");
        hospitalTextView.setText(hospitalName);
    }
}
