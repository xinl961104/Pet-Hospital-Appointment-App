package com.comp90018.drpet.helper;

import android.util.Log;

import androidx.annotation.NonNull;

import com.comp90018.drpet.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserRetriever {

    private String UID;
    private DatabaseReference reff;
    private UserModel user;

    public UserRetriever() {
        this.UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.reff = FirebaseDatabase.getInstance().getReference().child("users");
        this.user = new UserModel();
    }

    public interface FirebaseCallback {
        void onCallback(UserModel user);
    }

    public void retrieveData(final UserRetriever.FirebaseCallback firebaseCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if(UID.equals(ds.child("uid").getValue(String.class))){
                            user.email = ds.child("email").getValue(String.class);
                            user.name = ds.child("name").getValue(String.class);
                        }
                    }
                }
                firebaseCallback.onCallback(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reff.addValueEventListener(valueEventListener);
    }

}
