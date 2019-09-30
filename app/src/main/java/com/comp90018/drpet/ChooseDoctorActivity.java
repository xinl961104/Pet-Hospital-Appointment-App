package com.comp90018.drpet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseDoctorActivity extends AppCompatActivity {

    TextView hospitalName;
    ImageView hospitalImage;
    TextView hospitalInfo;
    TextView placeholderDoctor;
    TextView hospitalOpenHours;
    TextView hospitalPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_doctor);

        Intent incomingIntent = getIntent();
        String id = incomingIntent.getStringExtra("HospitalId");
        String name = incomingIntent.getStringExtra("HospitalName");
        String address = incomingIntent.getStringExtra("HospitalAddress");
        String phone = incomingIntent.getStringExtra("HospitalPhone");
        String background = incomingIntent.getStringExtra("HospitalBackground");
        String openhour = incomingIntent.getStringExtra("HospitalOpenHours");

        hospitalName = (TextView) findViewById(R.id.HospitalName);
        hospitalInfo = (TextView) findViewById(R.id.HospitalInfo);
        placeholderDoctor = (TextView) findViewById(R.id.PlaceholderDoctor);
        hospitalOpenHours = (TextView) findViewById(R.id.HospitalOpenHours);
        hospitalPhone = (TextView) findViewById(R.id.HospitalPhone);

        hospitalName.setText(name);
        hospitalOpenHours.setText(openhour);
        hospitalInfo.setText(background);
        hospitalPhone.setText(phone +"   "+ address);
    }
}
