package com.comp90018.drpet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;
    TextView selecteddate;
    String doctorID;
    String date;
    public Button buttonNext;
    public Button buttonLast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Intent incomingIntent = getIntent();
        doctorID = incomingIntent.getStringExtra("DoctorIDForTime");

        mCalendarView = (CalendarView)findViewById(R.id.calendarView);
        selecteddate = (TextView)findViewById(R.id.CalendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){

            public void onSelectedDayChange(CalendarView calendarView, int i , int i1, int i2){
                i1= i1 + 1;
                date = i + "/" + i1 + "/" + i2;
                Log.d(TAG, "onselecteddatachange: date: " +  date);
                selecteddate.setText(date);
            }
        });

        buttonLast = (Button)findViewById(R.id.buttonLast);

        buttonLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(CalendarActivity.this, ChooseTimeSlotActivity.class);
                intent1.putExtra("doctorID", doctorID);
                startActivity(intent1);
            }
        });

        buttonNext = (Button)findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this, TimeSlotActivity.class);
                intent.putExtra("DoctorIDForPet", doctorID);
                intent.putExtra("DateForPet", date);
                startActivity(intent);

            }
        });
    }
}
