package com.comp90018.drpet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChooseDoctorActivity extends AppCompatActivity {

    TextView hospitalName;
    ImageView hospitalImage;
    TextView hospitalInfo;
    TextView placeholderDoctor;
    TextView hospitalOpenHours;
    TextView hospitalPhone;
    String id;
    String phone;

    ListView listofDoctors;


    DatabaseReference databaseDoctors;

    public void callHospital(View view) {
        if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG", "Permission is granted");

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));
            startActivity(callIntent);
        } else {

            Log.v("TAG", "Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_choose_doctor);

        Intent incomingIntent = getIntent();
        id = incomingIntent.getStringExtra("HospitalId");
        String name = incomingIntent.getStringExtra("HospitalName");
        String address = incomingIntent.getStringExtra("HospitalAddress");
        phone = incomingIntent.getStringExtra("HospitalPhone");
        String background = incomingIntent.getStringExtra("HospitalBackground");
        String openhour = incomingIntent.getStringExtra("HospitalOpenHours");

        hospitalName = (TextView) findViewById(R.id.HospitalName);
        hospitalInfo = (TextView) findViewById(R.id.HospitalInfo);
        placeholderDoctor = (TextView) findViewById(R.id.PlaceholderDoctor);
        hospitalOpenHours = (TextView) findViewById(R.id.HospitalOpenHours);
        hospitalPhone = (TextView) findViewById(R.id.HospitalPhone);
        listofDoctors = (ListView) findViewById(R.id.ListofDoctors);

        //databaseDoctors = FirebaseDatabase.getInstance().getReference("Doctor");
        Query query = FirebaseDatabase.getInstance().getReference("Doctor").orderByChild("hospitalId").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    // doctorList.clear();
                    final List<DoctorModel> doctorList = new ArrayList<>();
                    for (DataSnapshot doctorSnapShot : dataSnapshot.getChildren()) {
                        DoctorModel doctor = doctorSnapShot.getValue(DoctorModel.class);
                        doctorList.add(doctor);
                    }

                    DoctorList adapter = new DoctorList(ChooseDoctorActivity.this, doctorList);
                    listofDoctors.setAdapter(adapter);
                    listofDoctors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent1 = new Intent(ChooseDoctorActivity.this, ChooseTimeSlotActivity.class);
                            intent1.putExtra("doctorID", doctorList.get(i).getDoctorId());
                            startActivity(intent1);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        hospitalName.setText(name);
        hospitalOpenHours.setText(openhour);
        hospitalInfo.setText(background);
        hospitalPhone.setText(phone + "   " + address);
    }

}
