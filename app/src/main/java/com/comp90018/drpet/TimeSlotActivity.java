package com.comp90018.drpet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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

public class TimeSlotActivity extends AppCompatActivity {
    String doctorID;
    String date;
    public TextView DoctorID;
    public TextView Date;
    public Button btnGotoPet;
    public Spinner spinner1;
    String selecteddate;
    private boolean isSpinnerInitial = true;
    String selectedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slot);
        Intent intent = getIntent();
        doctorID = intent.getStringExtra("DoctorIDForPet");
        date = intent.getStringExtra("DateForPet");

        DoctorID = (TextView) findViewById(R.id.DoctorID);
        Date =  (TextView) findViewById(R.id.selecteddate);
        spinner1 = (Spinner) findViewById(R.id.SpinnerTime);

        DoctorID.setText(doctorID);
        Date.setText(date);

        Query queryTimeSlot = FirebaseDatabase.getInstance().getReference("TimeSlot").orderByChild("doctorID").equalTo(doctorID);

        queryTimeSlot.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    final List<String> times = new ArrayList<>();
                    for(DataSnapshot timeSlotSnapShot : dataSnapshot.getChildren()){
                        TimeSlotModel timeSlot = timeSlotSnapShot.getValue(TimeSlotModel.class);
                        if(timeSlot.getDate().equals(date) && timeSlot.getFlag().equals("0")){
                            times.add(timeSlot.getStartTime());
                        }
                    }

                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, times);
                    adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                    spinner1.setAdapter(adapter);

                    spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override

                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                selectedDate = spinner1.getSelectedItem().toString();

                            }



                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                }else{
                    Toast.makeText(TimeSlotActivity.this, "choose other dates, such as 2019/9/17", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // go to the next page
        btnGotoPet = (Button)findViewById(R.id.Finally);

        btnGotoPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimeSlotActivity.this, AppointmentAddPetActivity.class);
                intent.putExtra("DoctorIDAppointment", doctorID);
                intent.putExtra("dateforAppointment",date);
                intent.putExtra("selectedTime", selectedDate);
                startActivity(intent);
            }
        });

        //


    }
    public void loadSpinnerTimeSlot(){
        Query queryTimeSlot = FirebaseDatabase.getInstance().getReference("TimeSlot").orderByChild("doctorID").equalTo(doctorID);

        queryTimeSlot.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    final List<String> times = new ArrayList<>();
                    for(DataSnapshot timeSlotSnapShot : dataSnapshot.getChildren()){
                        TimeSlotModel timeSlot = timeSlotSnapShot.getValue(TimeSlotModel.class);
                        if(timeSlot.getDate().equals(date) && timeSlot.getFlag().equals("0")){
                            times.add(timeSlot.getStartTime());
                        }
                    }

                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, times);
                    adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                    spinner1.setAdapter(adapter);

                    spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override

                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (isSpinnerInitial) {
                                isSpinnerInitial = false;
                            } else {
                                String selectedDate = spinner1.getSelectedItem().toString();

                                Intent intent = new Intent(TimeSlotActivity.this, AppointmentAddPetActivity.class);
                                intent.putExtra("DoctorIDAppointment", doctorID);
                                intent.putExtra("dateforAppointment",date);
                                intent.putExtra("selectedTime", selectedDate);

                                startActivity(intent);
                            }
                        }


                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                }else{
                    Toast.makeText(TimeSlotActivity.this, "choose other dates, such as 2019/9/17", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
