package com.comp90018.drpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterActivity extends AppCompatActivity {

    EditText email, name, password;
    ImageView profile;
    Button signup;
//    Uri imageUri;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.id);
        password = findViewById(R.id.password);
        name = findViewById(R.id.username);
        profile = findViewById(R.id.img_profile);
        signup = findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

//        profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                upload();
//            }
//        });
    }

//    private void upload() {
//
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//        startActivityForResult(intent,10);
//    }

    private void signup() {
        mAuth = FirebaseAuth.getInstance();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final String uid = mAuth.getCurrentUser().getUid();
                    UserModel userModel = new UserModel();
                    userModel.name = name.getText().toString();
                    userModel.uid = uid;
                    userModel.email = email.getText().toString();
                    FirebaseDatabase.getInstance().getReference().child("users").child(userModel.name).setValue(userModel);
//                    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                    final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("users").child(uid);
//                    storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                            if (task.isSuccessful()){
//                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                    @Override
//                                    public void onSuccess(Uri uri) {
//                                        String imageurl = uri.toString();
//                                        UserModel userModel = new UserModel();
//                                        userModel.name = name.getText().toString();
//                                        userModel.uid = uid;
//                                        userModel.imageurl = imageurl;
//                                        FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel);
//                                    }
//                                });
//                            }else{
//                                Toast.makeText(RegisterActivity.this, "error", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                }else{
                    Toast.makeText(RegisterActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 10 && resultCode == RESULT_OK){
//            profile.setImageURI(data.getData());
//            imageUri = data.getData();
//        }
//    }
}
