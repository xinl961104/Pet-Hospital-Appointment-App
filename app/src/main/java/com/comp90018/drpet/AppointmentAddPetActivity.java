package com.comp90018.drpet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AppointmentAddPetActivity extends AppCompatActivity {
    public String doctorID;
    public String date;
    public String time;
    public Spinner spinner2;
    public String selectedPet;
    TextView show;
    public String userEmail;
    public String userID;
    public String userName;
    public String ownerID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_add_pet);
        Intent intent = getIntent();
        doctorID = intent.getStringExtra("DoctorIDAppointment");
        date = intent.getStringExtra("dateforAppointment");
        time = intent.getStringExtra("selectedTime");

        show = (TextView) findViewById(R.id.textView2);
        show.setText(doctorID + date + time);
        spinner2 = (Spinner) findViewById(R.id.PetList);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            userEmail = user.getEmail();
            userID = user.getUid();
            userName = user.getDisplayName();
        }

        Query queryPet = FirebaseDatabase.getInstance().getReference("Pet").orderByChild("ownerID").equalTo(userID);

        queryPet.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    final List<String> pets = new ArrayList<>();
                    for(DataSnapshot petSlotSnapShot : dataSnapshot.getChildren()){
                        Pet pet = petSlotSnapShot.getValue(Pet.class);
                        pets.add(pet.getPetName());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, pets);

                    adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override

                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            selectedPet = spinner2.getSelectedItem().toString();
                            Toast.makeText(AppointmentAddPetActivity.this,selectedPet , Toast.LENGTH_SHORT).show();

                        }



                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // the whole booking action is shown here




    }
}
