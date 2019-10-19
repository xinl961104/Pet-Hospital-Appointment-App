package com.comp90018.drpet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPetActivity extends AppCompatActivity {
    Button AddPet;
    EditText PetName;
    EditText PetAge;
    EditText PetCategory;
    EditText PetBreed;
    EditText CommentforPet;
    ImageView ImageView;
    Button BacktoPetList;
    Button TakePhotoes;
    String petname;
    String petage;
    String petcategory;
    String petbreed;
    String commentforpet;

    String userEmail;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        Intent incomingIntent = getIntent();

        PetName = (EditText)findViewById(R.id.PetName);
        PetAge = (EditText)findViewById(R.id.PetAge);
        PetCategory = (EditText)findViewById(R.id.PetCategory);
        PetBreed = (EditText)findViewById(R.id.PetBreed);
        CommentforPet = (EditText)findViewById(R.id.CommentforPet);



        BacktoPetList = (Button)findViewById(R.id.backtoPetList);
        TakePhotoes = (Button)findViewById(R.id.takePhotoes);
        AddPet = (Button)findViewById(R.id.addPet1);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            userEmail = user.getEmail();
            userID = user.getUid();
           // userName = user.getDisplayName();
        }

        AddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                petname = PetName.getText().toString();
                petage = PetAge.getText().toString();
                petcategory = PetCategory.getText().toString();
                petbreed = PetBreed.getText().toString();
                commentforpet = CommentforPet.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Pet");
                String k =  myRef.push().getKey();
                Pet newPet = new Pet(k, petcategory, petbreed,petage,commentforpet,userID, petname);
                myRef.child(k).setValue(newPet);//this creates the reqs key-value pair
                Toast.makeText(AddPetActivity.this, "Add Success", Toast.LENGTH_SHORT).show();
            }
        });

        BacktoPetList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddPetActivity.this, ViewAllPetsActivity.class);
                startActivity(i);
            }
        });






    }
}
