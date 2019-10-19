package com.comp90018.drpet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewAPetActivity extends AppCompatActivity {
    String petID;
    TextView petid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_apet);


        Intent incoming = getIntent();
        petID = incoming.getStringExtra("petID");
        petid = (TextView) findViewById(R.id.Petid);
        petid.setText(petID);


    }
}
