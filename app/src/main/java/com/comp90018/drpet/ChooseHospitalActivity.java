package com.comp90018.drpet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ChooseHospitalActivity extends AppCompatActivity {
    private static final String TAG = "Get Hospital";
    Button selectBtn;
    // the name/id of the hospital is from the map sensor.
    // Here I use a temporary Hospital name which should be replaced after map sensor implemented
    String hospitalname = "Epworth Freemasons";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hospital);

        selectBtn = (Button)findViewById(R.id.SelectHosptial);

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadHospital();
            }
        });
    }
    public void loadHospital(){
    // query data from firebase hospital
        Query query = FirebaseDatabase.getInstance().getReference("Hospital").orderByChild("hospitalName").equalTo(hospitalname);
        // Attach a listener to read the data at our posts reference
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                HospitalModel hospital = childSnapshot.getValue(HospitalModel.class);
                        Log.d(TAG, "Get hospital" +  hospital.getHospitalName());
                        System.out.println("GEt hospital " + hospital.getHospitalBackground());
                        Intent intent = new Intent(ChooseHospitalActivity.this,ChooseDoctorActivity.class);
                        intent.putExtra("HospitalBackground", hospital.getHospitalBackground());
                        intent.putExtra("HospitalPhone", hospital.getHospitalPhone());
                        intent.putExtra("HospitalId",hospital.getHospitalId());
                        intent.putExtra("HospitalName",hospital.getHospitalName());
                        intent.putExtra("HospitalAddress", hospital.getHospitalAddress());
                        intent.putExtra("HospitalOpenHours", hospital.getHospitalOpenhours());
                        startActivity(intent);
                    }
            }}
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Fail to Get hospital");
                Toast.makeText(ChooseHospitalActivity.this, "Fail to find hospital", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
