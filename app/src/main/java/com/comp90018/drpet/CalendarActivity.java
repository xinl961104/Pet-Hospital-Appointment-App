package com.comp90018.drpet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mCalendarView = (CalendarView)findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){

            public void onSelectedDayChange(CalendarView calendarView, int i , int i1, int i2){
                String date = i + "/" + i1 + "/" + i2;
                Log.d(TAG, "onselecteddatachange: date: " +  date);

                Intent intent = new Intent(CalendarActivity.this, ChooseTimeSlotActivity.class);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });
    }
}
