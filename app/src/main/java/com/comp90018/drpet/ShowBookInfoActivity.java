package com.comp90018.drpet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowBookInfoActivity extends AppCompatActivity {
    public String ownerComment;
    public String date;
    public String doctorFirstName;
    public String doctorLastName;
    public String doctorID;
    public String hospitalName;
    public String hospitalId;
    public String selectedPet;
    public String time;
    public String userEmail;
    public String userName;
    public String userID;

    TextView hospital;
    TextView doctorName;
    TextView dateTime;
    TextView petInfo;
    TextView comment;
    Button backToDashboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book_info);


        Intent incomingIntent = getIntent();
        ownerComment = incomingIntent.getStringExtra("commentFromOwner");
        date = incomingIntent.getStringExtra("dateForAppointment");
        doctorFirstName = incomingIntent.getStringExtra("DoctorFirstName");
        doctorLastName = incomingIntent.getStringExtra("DoctorLastName");
        doctorID = incomingIntent.getStringExtra("DoctorId");
        hospitalName = incomingIntent.getStringExtra("HospitalName");
        hospitalId = incomingIntent.getStringExtra("HospitalId");
        selectedPet = incomingIntent.getStringExtra("PetName");
        time = incomingIntent.getStringExtra("StartTime");
        userEmail = incomingIntent.getStringExtra("UserEmail");
        userName = incomingIntent.getStringExtra("UserName");
        userID = incomingIntent.getStringExtra("UserId");

        hospital = (TextView)findViewById(R.id.HospitalInfo);
        doctorName = (TextView)findViewById(R.id.DoctorName1);
        dateTime = (TextView)findViewById(R.id.DateTime1);
        petInfo = (TextView)findViewById(R.id.PetInfo);
        comment = (TextView)findViewById(R.id.Comment1);
        backToDashboard = (Button)findViewById(R.id.BackToDashboard);

        hospital.setText(hospitalName);
        petInfo.setText(selectedPet);
        doctorName.setText(hospitalName);
        dateTime.setText(date + " " + time);
        petInfo.setText(selectedPet);
        comment.setText(ownerComment);


       // hospital.setText(hospitalId);
      //  doctorName.setText(doctorFirstName);
      //  dateTime.setText(date);

       // comment.setText(ownerComment);

        backToDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowBookInfoActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}
