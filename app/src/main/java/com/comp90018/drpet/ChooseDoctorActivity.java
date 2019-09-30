package com.comp90018.drpet;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChooseDoctorActivity extends AppCompatActivity {
    String hospitalName = "Epworth Freemasons";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_doctor);
        // the name/id of the hospital is from the map sensor.
        // Here I use a temporary Hospital name which should be replaced after map sensor implemented






        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }
}
