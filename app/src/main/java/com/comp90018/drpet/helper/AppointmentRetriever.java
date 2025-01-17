package com.comp90018.drpet.helper;

import androidx.annotation.NonNull;

import com.comp90018.drpet.Appointment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AppointmentRetriever {

    private  String UID;
    private  DatabaseReference reff;
    private  ArrayList<Appointment> appointment;



    public AppointmentRetriever() {
        this.UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.reff = FirebaseDatabase.getInstance().getReference().child("AppointmentFinal");
        this.appointment = new ArrayList<>();
    }

    public interface FirebaseCallback {
        void onCallback(ArrayList<Appointment> list);
    }

    public void retrievData(final FirebaseCallback firebaseCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointment.clear();
                if (dataSnapshot.exists()) {
                    int i = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if(UID.equals(ds.child("userID").getValue(String.class))){
                            appointment.add(new Appointment());
                            appointment.get(i).setAppointmentID(ds.child("appointmentID").getValue(String.class));
                            appointment.get(i).setComment(ds.child("comment").getValue(String.class));
                            appointment.get(i).setDate(ds.child("date").getValue(String.class));
                            appointment.get(i).setDoctorID(ds.child("doctorID").getValue(String.class));
                            appointment.get(i).setPetID(ds.child("petID").getValue(String.class));
                            appointment.get(i).setPetName(ds.child("petName").getValue(String.class));
                            appointment.get(i).setStartTime(ds.child("startTime").getValue(String.class));
                            appointment.get(i).setStatus(ds.child("status").getValue(String.class));
                            appointment.get(i).setUserEmail(ds.child("userEmail").getValue(String.class));
                            appointment.get(i).setUserID(ds.child("userID").getValue(String.class));
                            appointment.get(i).setUserName(ds.child("userName").getValue(String.class));
                            appointment.get(i).setDoctorFirstName(ds.child("doctorFirstName").getValue(String.class));
                            appointment.get(i).setDoctorLastName(ds.child("doctorLastName").getValue(String.class));
                            appointment.get(i).setHospitalName(ds.child("hospitalName").getValue(String.class));
                            System.out.println(i);
                            System.out.println(appointment.get(i));
                            i++;
                        }
                    }

                }
                firebaseCallback.onCallback(appointment);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reff.addValueEventListener(valueEventListener);
    }

}
