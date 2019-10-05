package com.comp90018.drpet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.comp90018.drpet.helper.AppointmentDeleter;

public class DetailsActivity extends AppCompatActivity {

    TextView hospitalNameTextView;
    TextView doctorNameTextView;
    TextView dateTimeTextView;
    TextView petNameTextView;
    TextView commentTextView;
    Button cancelButton;

    String appintmentID;
    String hospitalName;
    String doctorName;
    String dateTime;
    String petName;
    String comment;
    String status;

    public void cancelAppointment(View view) {

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Cancel Appointment")
                .setMessage("Are you sure to cancel this appointment?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // delete the record and go back to management
                        // finish();
                        AppointmentDeleter deleter = new AppointmentDeleter(appintmentID);
                        deleter.deleteData(new AppointmentDeleter.FirebaseCallback() {
                            @Override
                            public void onCallback(String message) {
                                Intent intent = new Intent(getApplicationContext(), ManagementActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Toast.makeText(getApplicationContext(), "Nothing Happened", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        hospitalNameTextView = findViewById(R.id.hospitalNameTextView);
        doctorNameTextView = findViewById(R.id.doctorNameTextView);
        dateTimeTextView = findViewById(R.id.dateTimeTextView);
        petNameTextView = findViewById(R.id.petNameTextView);
        commentTextView = findViewById(R.id.commentTextView);
        cancelButton = findViewById(R.id.cancelButton);

        initializeView();
    }

    private void initializeView() {
        Intent intent = getIntent();
        appintmentID = intent.getStringExtra("appintmentID");
        hospitalName = intent.getStringExtra("hospitalName");
        doctorName = intent.getStringExtra("doctorFirstName") + intent.getStringExtra("doctorLastName");
        dateTime = intent.getStringExtra("time") + intent.getStringExtra("date");
        petName = intent.getStringExtra("petName");
        comment = intent.getStringExtra("comment");
        status = intent.getStringExtra("status");

        hospitalNameTextView.setText(hospitalName);
        doctorNameTextView.setText(doctorName);
        dateTimeTextView.setText(dateTime);
        petNameTextView.setText(petName);
        commentTextView.setText(comment);

        if (status.equals("finished")) {
            cancelButton.setEnabled(false);
        } else if (status.equals("booked")) {
            cancelButton.setEnabled(true);
        }
    }

}
