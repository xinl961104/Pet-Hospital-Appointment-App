package com.comp90018.drpet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AppointmentAddPetActivity extends AppCompatActivity {
    public String doctorID;
    public String date;
    public String time;
    TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_add_pet);
        Intent intent = getIntent();
        doctorID = intent.getStringExtra("DoctorIDAppointment");
        date = intent.getStringExtra("dateforAppointment");
        time = intent.getStringExtra("selectedTime");

        show = (TextView) findViewById(R.id.textView2);
        show.setText(doctorID + date + time);

    }
}
