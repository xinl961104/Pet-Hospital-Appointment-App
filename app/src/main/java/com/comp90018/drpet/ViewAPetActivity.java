package com.comp90018.drpet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewAPetActivity extends AppCompatActivity {

    public String petID;

    public String petName;
    public String petAge;
    public String petBreed;
    public String petCategory;
    public String petComment;
    public String userID;

    EditText PetsName;
    EditText PetsAge;
    EditText PetsCategory;
    EditText PetsBreed;
    EditText PetsComment;

    Button cancel;
    Button UpdatePetInfo;
    Button DeletePet;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_apet);


        Intent incoming = getIntent();
        petID = incoming.getStringExtra("petID");
        petName = incoming.getStringExtra("petName");
        petAge = incoming.getStringExtra("petAge");
        petBreed = incoming.getStringExtra("petBreed");
        petCategory = incoming.getStringExtra("petCategory");
        petComment = incoming.getStringExtra("petComment");

        PetsName = (EditText) findViewById(R.id.PetName);
        PetsAge = (EditText) findViewById(R.id.PetAge);
        PetsCategory = (EditText) findViewById(R.id.PetsCategory);
        PetsBreed = (EditText) findViewById(R.id.PetsBreed);
        PetsComment = (EditText) findViewById(R.id.PetsComment);

        cancel = (Button) findViewById(R.id.cancel);
        UpdatePetInfo = (Button) findViewById(R.id.UpdatePetInfo);
        DeletePet = (Button) findViewById(R.id.DeletePet);

        imageView = findViewById(R.id.imageView3);

        PetsName.setText(petName);
        PetsAge.setText(petAge);
        PetsBreed.setText(petBreed);
        PetsCategory.setText(petCategory);
        PetsComment.setText(petComment);
        showPic();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(ViewAPetActivity.this, ViewAllPetsActivity.class);
                startActivity(i1);
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //  userEmail = user.getEmail();
            userID = user.getUid();
            // userName = user.getDisplayName();
        }


        UpdatePetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                petName = PetsName.getText().toString();
                petAge = PetsAge.getText().toString();
                petCategory = PetsCategory.getText().toString();
                petBreed = PetsBreed.getText().toString();
                petComment = PetsComment.getText().toString();

                FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                DatabaseReference myref2 = database1.getReference();
                //Pet newPet = new Pet(petID, petCategory, petBreed, petAge, petComment, userID, petName);
                //myref2.child("Pet").child(petID).setValue(newPet);


                Toast.makeText(ViewAPetActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
            }
        });


        DeletePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                DatabaseReference myref3 = database2.getReference();
                // Pet newPet = new Pet(petID, petCategory, petBreed,petAge,petComment,userID, petName);
                myref3.child("Pet").child(petID).removeValue();

                Toast.makeText(ViewAPetActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void showPic() {

        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Pet");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Pet pet2 = postSnapshot.getValue(Pet.class);
//                    System.out.println(pet2.getPetID());
//                    System.out.println(pet2.getImageurl());
                    if (pet2.getPetID().equals(petID)) {
                        //System.out.println("1");
                        Glide.with(ViewAPetActivity.this)
                                .load(pet2.getImageurl()) // the uri you got from Firebase
                                .into(imageView);
                        //System.out.println("2");

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ViewAPetActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }



}
