package com.comp90018.drpet.helper;

import androidx.annotation.NonNull;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class AppointmentDeleter {

    private String appointmentID;
    private DatabaseReference reff;


    public AppointmentDeleter(String appointmentID) {
        this.appointmentID = appointmentID;
        this.reff = FirebaseDatabase.getInstance().getReference().child("AppointmentFinal");

    }
    public interface FirebaseCallback {
        void onCallback(String message);
    }

    public void deleteData(final AppointmentDeleter.FirebaseCallback firebaseCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if(appointmentID.equals(ds.child("appointmentID").getValue(String.class))){
                            ds.getRef().removeValue();
                        }
                    }

                }
                firebaseCallback.onCallback("delete");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reff.addValueEventListener(valueEventListener);
    }
}
