package com.comp90018.drpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    TextView usernameTextView;

    public void makeAppointment(View view) {
        Intent intent = new Intent(getApplicationContext(), HospitalActivity.class);
        startActivity(intent);
    }

    public void manageAppointment(View view) {
        Intent intent = new Intent(getApplicationContext(), ManagementActivity.class);
        startActivity(intent);
    }

    public void managePets(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void logout(View view) {
        Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        usernameTextView = findViewById(R.id.usernameTextView);

        String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        usernameTextView.setText(username);
    }
}
