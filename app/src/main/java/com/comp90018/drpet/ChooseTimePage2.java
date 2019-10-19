package com.comp90018.drpet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChooseTimePage2 extends AppCompatActivity {
    public Spinner spinner1;
    public String doctorID;
    public String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_time_page2);


        Intent incomingIntent = getIntent();
        doctorID = incomingIntent.getStringExtra("DoctorIDForPet");
        date = incomingIntent.getStringExtra("DateForPet");

        //have already getten the id of doctor and date
        //for the timeslot selection
        // using the selected id of the doctor and date to iddentify the available time slot
        // when the flag is equal to 0;
        // then store all the available time slot in a String arraylist
        // and then put the arraylist into a spinner
        //final List<TimeSlotModel> timeslots = new ArrayList<>();
        final List<String> times = new ArrayList<>();
        Query queryTimeSlot = FirebaseDatabase.getInstance().getReference("TimeSlot").orderByChild("doctorID").equalTo(doctorID);
        queryTimeSlot.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    for(DataSnapshot timeSlotSnapShot : dataSnapshot.getChildren()){
                        TimeSlotModel timeslot = timeSlotSnapShot.getValue(TimeSlotModel.class);
                        if(timeslot.getDate().equals(date) && timeslot.getFlag().equals("0")){
                            times.add(timeslot.getDate());
                        }else{
                            Toast.makeText(ChooseTimePage2.this, "choose other dates", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        spinner1 = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, times);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);


    }
}
