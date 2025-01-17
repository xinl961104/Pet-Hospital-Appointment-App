package com.comp90018.drpet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ChooseTimeSlotActivity extends AppCompatActivity {
    private static final String TAG = "Get Hospital";
    public TextView theDate;
    public Button btnGoCalendar;
    public String doctorID;
    public TextView DoctorName;
    public TextView DoctorBackgroundInfo;
    public Button btnChooseTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time_slot);
        Intent intent = getIntent();
        doctorID = intent.getStringExtra("doctorID");
        loadhospital();

       // theDate = (TextView) findViewById(R.id.SelectedDate);
        btnGoCalendar = (Button) findViewById(R.id.btnGoCalendar);
        //Intent incomingIntent = getIntent();
      //  date = incomingIntent.getStringExtra("date");
       // theDate.setText(date);
        btnGoCalendar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(ChooseTimeSlotActivity.this, CalendarActivity.class);
                intent.putExtra("DoctorIDForTime", doctorID);
                startActivity(intent);
            }
        });



      /*  btnChooseTime = (Button) findViewById(R.id.GotoAddTime);
        btnChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Intent intent1 = new Intent(ChooseTimeSlotActivity.this, ChooseTimePage2.class);

              //  intent1.putExtra("DoctorIDForTime", doctorID);
               // intent1.putExtra("ChosenTime",date);
                startActivity(intent1);
            }
        });*/

    }

    public void loadhospital(){
        Query query = FirebaseDatabase.getInstance().getReference("Doctor").orderByChild("doctorId").equalTo(doctorID);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot childSnapShot: dataSnapshot.getChildren()){
                        DoctorModel doctor = childSnapShot.getValue(DoctorModel.class);
                        DoctorName = (TextView) findViewById(R.id.TheDoctorName);
                        DoctorBackgroundInfo = (TextView) findViewById(R.id.DoctorInfo);
                        DoctorName.setText(doctor.getDoctorFirstName()+ " " + doctor.getDoctorLastName());
                        DoctorBackgroundInfo.setText(doctor.getDoctorInfo());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

