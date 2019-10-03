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

    private static String UID;
    private static DatabaseReference reff;
    private static ArrayList<Appointment> appointment = new ArrayList<>();


    public static ArrayList<Appointment> retrieveAppointments() {


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            UID = user.getUid();

        }

        reff  = FirebaseDatabase.getInstance().getReference().child("AppointmentNew").child(UID);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int i = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
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
                        System.out.println(i);
                        System.out.println(appointment.get(i));
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return appointment;

    }

}
