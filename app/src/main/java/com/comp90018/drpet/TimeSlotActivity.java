package com.comp90018.drpet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TimeSlotActivity extends AppCompatActivity {
    String doctorID;
    String date;
    public TextView DoctorID;
    public TextView Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slot);
        Intent intent = getIntent();
        doctorID = intent.getStringExtra("DoctorIDForPet");
        date = intent.getStringExtra("DateForPet");

        DoctorID = (TextView) findViewById(R.id.DoctorID);
        Date =  (TextView) findViewById(R.id.selecteddate);

        DoctorID.setText(doctorID);
        Date.setText(date);




    }
}
