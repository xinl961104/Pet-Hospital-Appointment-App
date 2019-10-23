package com.comp90018.drpet;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;

public class AddPetActivity extends AppCompatActivity {
    Button AddPet;
    EditText PetName;
    EditText PetAge;
    EditText PetCategory;
    EditText PetBreed;
    EditText CommentforPet;
    ImageView mImageView;
    Button BacktoPetList;
    Button TakePhotoes;
    Button pick;
    String petname;
    String petage;
    String petcategory;
    String petbreed;
    String commentforpet;

    StorageReference fileReference;
    Uri mImageUri;
    StorageReference mStorageRef;
    StorageTask mUploadTask;
    DatabaseReference myRef;

    String userEmail;
    String userID;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private final int REQUEST_PICK_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        mImageView = findViewById(R.id.imageView);

        Intent incomingIntent = getIntent();

        PetName = (EditText) findViewById(R.id.PetName);
        PetAge = (EditText) findViewById(R.id.PetAge);
        PetCategory = (EditText) findViewById(R.id.PetCategory);
        PetBreed = (EditText) findViewById(R.id.PetBreed);
        CommentforPet = (EditText) findViewById(R.id.CommentforPet);


        pick = findViewById(R.id.btnpick);
        BacktoPetList = (Button) findViewById(R.id.backtoPetList);
        TakePhotoes = (Button) findViewById(R.id.takePhotoes);
        AddPet = (Button) findViewById(R.id.addPet1);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
            userID = user.getUid();
            // userName = user.getDisplayName();
        }

        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, REQUEST_PICK_IMAGE);

            }
        });

        AddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            uploadFile();
//                petname = PetName.getText().toString();
//                petage = PetAge.getText().toString();
//                petcategory = PetCategory.getText().toString();
//                petbreed = PetBreed.getText().toString();
//                commentforpet = CommentforPet.getText().toString();
//
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                myRef = database.getReference("Pet");
//                String k = myRef.push().getKey();
//
//                Pet newPet = new Pet(k, petcategory, petbreed, petage, commentforpet, userID, petname);
//                myRef.child(k).setValue(newPet);//this creates the reqs key-value pair
//                Toast.makeText(AddPetActivity.this, "Add Success", Toast.LENGTH_SHORT).show();
            }
        });

        BacktoPetList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddPetActivity.this, ViewAllPetsActivity.class);
                startActivity(i);
            }
        });

        TakePhotoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture(view);
            }
        });


    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile() {
        if (mImageUri != null) {
            fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    System.out.println(uri);

                                    petname = PetName.getText().toString();
                                    petage = PetAge.getText().toString();
                                    petcategory = PetCategory.getText().toString();
                                    petbreed = PetBreed.getText().toString();
                                    commentforpet = CommentforPet.getText().toString();

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    myRef = database.getReference("Pet");
                                    String k = myRef.push().getKey();

                                    Pet newPet = new Pet(k, petcategory, petbreed, petage, commentforpet, userID, petname, uri.toString());

                                    myRef.child(k).setValue(newPet);

                                    Toast.makeText(AddPetActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddPetActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            //mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void takePicture(View view) {
        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (imageTakeIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            mImageUri = uri;
            Bitmap bitmap = null;
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                mImageView.setImageBitmap(bitmap);

                FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

                FirebaseVisionImageLabeler detector = FirebaseVision.getInstance().getOnDeviceImageLabeler();

                detector.processImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionImageLabel> firebaseVisionImageLabels) {

                        StringBuilder info = new StringBuilder();
                        info.append("image labeler");
                        String guess = " ";
                        float p = 0;
                        for (FirebaseVisionImageLabel label : firebaseVisionImageLabels) {
                            info.append("Text: ").append(label.getText()).append(",\n").append("Confidence : ").append(label.getConfidence()).append(",\n");
                            if (label.getConfidence() > p) {
                                guess = label.getText();
                                p = label.getConfidence();
                            }
                        }
                        System.out.println(info);
                        String sen = "Is this is a " + guess + " with p: " + p;
                        showResult(sen);
                        PetCategory.setText(guess);
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void showResult(String sen) {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Picture Recognition");
        builder.setMessage(sen);

        // add a button
        builder.setPositiveButton("OK", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
