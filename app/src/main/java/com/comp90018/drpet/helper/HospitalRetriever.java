package com.comp90018.drpet.helper;

import androidx.annotation.NonNull;

import com.comp90018.drpet.Doctor;
import com.comp90018.drpet.Hospital;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HospitalRetriever {

    private String doctorID;
    private DatabaseReference reff;
    private ArrayList<Hospital> hospitals;
    private Hospital hospital;
    private Doctor doctor;
    private DatabaseReference reff1;
    private String hospitalID;


    public HospitalRetriever() {

        this.reff = FirebaseDatabase.getInstance().getReference().child("Hospital");
        this.hospitals = new ArrayList<>();
    }

    public HospitalRetriever(String doctorID) {
        this.doctorID = doctorID;
        this.reff = FirebaseDatabase.getInstance().getReference().child("Hospital");
        this.reff1 = FirebaseDatabase.getInstance().getReference().child("Doctor");
        this.hospital = new Hospital();
        this.doctor = new Doctor();
    }

    public interface FirebaseCallback {
        void onCallback(ArrayList<Hospital> list);
    }
    public interface FirebaseCallback1 {
        void onCallback(Doctor doctor);
    }

    public void retrieveHospitalByDoctorID(final FirebaseCallback1 firebaseCallback1 ) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (doctorID.equals(ds.child("doctorId").getValue(String.class))){
                            doctor.setDoctorId(doctorID);
                            doctor.setDoctorFirstName(ds.child("doctorFirstName").getValue(String.class));
                            doctor.setDoctorLastName(ds.child("doctorLastName").getValue(String.class));
                            doctor.setDoctorPhone(ds.child("doctorPhone").getValue(String.class));
                            doctor.setDoctorInfo(ds.child("doctorInfo").getValue(String.class));
                            doctor.setHospitalId(ds.child("hospitalId").getValue(String.class));

                        }
                    }
                }

                firebaseCallback1.onCallback(doctor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reff1.addValueEventListener(valueEventListener);
    }



    public void retrieveData(final FirebaseCallback firebaseCallback){
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

    public void findHospitalByDoctor (String doctorID) {

        HospitalRetriever retriever = new HospitalRetriever(doctorID);
        retriever.retrieveHospitalByDoctorID(new HospitalRetriever.FirebaseCallback1() {
            @Override
            public void onCallback(Doctor doctor) {
                hospitalID = doctor.getHospitalId();
            }
        });

        retriever.retrieveData(new HospitalRetriever.FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<Hospital> list) {
                System.out.println(list.size());
                for (int i = 0; i < list.size(); i++) {
                    if (hospitalID == list.get(i).getHospitalId()){

                        System.out.println(list.get(i).getHospitalName());
                    }
                }

            }
        });

    }

}
