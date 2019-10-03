package com.comp90018.drpet.helper;

import androidx.annotation.NonNull;

import com.comp90018.drpet.Hospital;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HospitalRetriever {

    private  String UID;
    private DatabaseReference reff;
    private ArrayList<Hospital> hospitals;



    public HospitalRetriever() {

        this.reff = FirebaseDatabase.getInstance().getReference().child("Hospital");
        this.hospitals = new ArrayList<>();
    }

    public interface FirebaseCallback {
        void onCallback(ArrayList<Hospital> list);
    }



    public void retrievData(final FirebaseCallback firebaseCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hospitals.clear();
                if (dataSnapshot.exists()) {
                    int i = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        hospitals.add(new Hospital());
                        hospitals.get(i).setHospitalId(ds.child("hospitalId").getValue(String.class));
                        hospitals.get(i).setHospitalName(ds.child("hospitalName").getValue(String.class));
                        hospitals.get(i).setHospitalBackground(ds.child("hospitalBackground").getValue(String.class));
                        hospitals.get(i).setHospitalOpenhours(ds.child("hospitalOpenhours").getValue(String.class));
                        hospitals.get(i).setHospitalPhone(ds.child("hospitalPhone").getValue(String.class));
                        hospitals.get(i).setHospitalAddress(ds.child("hospitalAddress").getValue(String.class));

//                        System.out.println(i);
//                        System.out.println(appointment.get(i));
                        i++;
                    }

                }
                firebaseCallback.onCallback(hospitals);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reff.addValueEventListener(valueEventListener);
    }

}
