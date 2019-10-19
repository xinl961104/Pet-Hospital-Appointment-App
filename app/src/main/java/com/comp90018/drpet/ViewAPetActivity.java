package com.comp90018.drpet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewAPetActivity extends AppCompatActivity {

    String petID;
    EditText nameEditText;
    EditText ageEditText;
    EditText categoryEditText;
    EditText breedEditText;
    EditText commentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_apet);

        nameEditText = findViewById(R.id.nameEditText);
        ageEditText = findViewById(R.id.ageEditText);
        categoryEditText = findViewById(R.id.categoryEditText);
        breedEditText = findViewById(R.id.breedEditText);
        commentEditText = findViewById(R.id.commentEditText);

        Intent incoming = getIntent();
        petID = incoming.getStringExtra("petID");
        String name = incoming.getStringExtra("name");
        String age = incoming.getStringExtra("age");
        String breed = incoming.getStringExtra("breed");
        String category = incoming.getStringExtra("category");
        String comment = incoming.getStringExtra("comment");

        nameEditText.setText(name);
        ageEditText.setText(age);
        categoryEditText.setText(category);
        breedEditText.setText(breed);
        commentEditText.setText(comment);

    }
}
