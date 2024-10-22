package com.comp90018.drpet;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAllPetsActivity extends AppCompatActivity {
    ListView listofPets;
    public String userEmail;
    public String userID;
    public String userName;
    Button addNewPet;
    Button backtodash;

    // Click listener for choosing different navigation tabs
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                // To show Booking
                case R.id.navigation_linear: {
                    Intent intent = new Intent(getApplicationContext(), HospitalActivity.class);
                    startActivity(intent);
                    return true;
                }
                // To show Management
                case R.id.navigation_relative: {
                    Intent intent = new Intent(getApplicationContext(), ManagementActivity.class);
                    startActivity(intent);
                    return true;
                }
                // To show My Pet
                case R.id.navigation_list: {
//                    Intent intent = new Intent(getApplicationContext(), ViewAllPetsActivity.class);
//                    startActivity(intent);
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_all_pets);

        // Setting for Navigation Bar
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_list);

        Intent incomingIntent = getIntent();
        listofPets = (ListView) findViewById(R.id.ListofPets);
        addNewPet = (Button) findViewById(R.id.AddnewPet);
        backtodash = (Button) findViewById(R.id.Backtodash);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
            userID = user.getUid();
            userName = user.getDisplayName();
        }

        Query query = FirebaseDatabase.getInstance().getReference("Pet").orderByChild("ownerID").equalTo(userID);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // doctorList.clear();
                    final List<Pet> petList = new ArrayList<>();
                    for (DataSnapshot petSnapShot : dataSnapshot.getChildren()) {
                        Pet pet = petSnapShot.getValue(Pet.class);
                        petList.add(pet);
                    }

                    PetList adapter = new PetList(ViewAllPetsActivity.this, petList);
                    listofPets.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    listofPets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent i1 = new Intent(ViewAllPetsActivity.this, ViewAPetActivity.class);
                            i1.putExtra("petID", petList.get(i).getPetID());
                            i1.putExtra("petName", petList.get(i).getPetName());
                            i1.putExtra("petAge", petList.get(i).getPetAge());
                            i1.putExtra("petBreed", petList.get(i).getBreed());
                            i1.putExtra("petCategory", petList.get(i).getCategory());
                            i1.putExtra("petComment", petList.get(i).getComment());

                            startActivity(i1);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addNewPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(ViewAllPetsActivity.this, AddPetActivity.class);
                i2.putExtra("ownerID", userID);
                startActivity(i2);
            }
        });


        backtodash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(ViewAllPetsActivity.this, HomeActivity.class);
                startActivity(i3);
            }
        });

    }
}
