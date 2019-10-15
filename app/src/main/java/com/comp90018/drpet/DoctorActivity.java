package com.comp90018.drpet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DoctorActivity extends AppCompatActivity {

    private TextView hospitalTextView;
    private ImageView hospitalImageView;
    private RecyclerView doctorRecyclerView;
    private TextView aboutTextView;
    private TextView openHoursTextView;
    private Button callButton;

    private RecyclerView.Adapter doctorAdapter;
    private ArrayList<String> doctorsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_doctor);

        hospitalTextView = findViewById(R.id.hospitalTextView);
        hospitalImageView = findViewById(R.id.hospitalImageView);
        doctorRecyclerView = findViewById(R.id.doctorRecyclerView);
        aboutTextView = findViewById(R.id.aboutTextView);
        openHoursTextView = findViewById(R.id.openHoursTextView);
        callButton = findViewById(R.id.callButton);

        initializeDoctorList();
        initializeView();
    }

    private void initializeDoctorList() {
        // supposed to fetch doctor list from firebase
        doctorsList = new ArrayList<>();
        doctorsList.add("Joaquin Phoenix");
        doctorsList.add("Robert De Niro");
        doctorsList.add("Zazie Beetz");
        doctorsList.add("Frances Conroy");
        doctorsList.add("Brett Cullen");
    }

    private void initializeView() {

        // set hospital title
        Intent intent = getIntent();
        String hospitalName = intent.getStringExtra("hospitalName");
        hospitalTextView.setText(hospitalName);

        // set hospital image
        hospitalImageView.setBackgroundResource(R.drawable.hospital_example);

        // set doctor list
        setDoctorRecyclerView();

        // set information about the hospital


        // set the opening hour


        // set call button


    }

    private void setDoctorRecyclerView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        doctorRecyclerView.setLayoutManager(layoutManager);

        doctorAdapter = new DoctorAdapter(doctorsList, this);
        doctorRecyclerView.setAdapter(doctorAdapter);
        doctorRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), doctorRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(getApplicationContext(), AppointmentActivity.class);
//                intent.putExtra("doctorID", position);
//                startActivity(intent);
//                Log.d("Name", "onItemClick: Done!");
                Toast.makeText(getApplicationContext(),doctorsList.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
            }
        }));
    }


}
