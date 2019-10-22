package com.comp90018.drpet;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import java.net.URI;
import java.util.List;


public class PickPic extends AppCompatActivity {
    Button pick,detect,retrieve;
    ImageView imageView;
    FirebaseAuth firebaseAuth;
    TextView txtquestion;
    private final int REQUEST_PICTURE =1;


    StorageReference fileReference;
    StorageTask mUploadTask;
    DatabaseReference reff;
    StorageReference mStorageRef;
    Uri mImageUri;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick);

        firebaseAuth = FirebaseAuth.getInstance();

        detect = findViewById(R.id.detect);
        pick = findViewById(R.id.pick);
        imageView = findViewById(R.id.imageView);
        detect = findViewById(R.id.detect);
        retrieve = findViewById(R.id.retrieve);

        txtquestion = findViewById(R.id.txtquestion);

        reff = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        pick.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //intent.setType("image/*");
                //System.out.println("image");
                startActivityForResult(intent,REQUEST_PICTURE);

            }
        });

        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(PickPic.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("test");
                System.out.println("click");
                mDatabaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            System.out.println("loop");
                            Pet pet2 = postSnapshot.getValue(Pet.class);
                            System.out.println(pet2.getOwnerID());
                            System.out.println(pet2.getImageurl());
                            if(pet2.getOwnerID().equals("123")){
                                System.out.println("1");
                                Glide.with(PickPic.this)
                                        .load(pet2.getImageurl()) // the uri you got from Firebase
                                        .into(imageView);
                                System.out.println("2");
////

                            }

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(PickPic.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
//            System.out.println("ok");
//            System.out.println(resultCode);

            if(requestCode == REQUEST_PICTURE){

                Uri uri = data.getData();
                mImageUri = uri;
                //System.out.println(mImageUri);
                Bitmap bitmap = null;
                try{

                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    imageView.setImageBitmap(bitmap);

                    FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

                    FirebaseVisionImageLabeler detector = FirebaseVision.getInstance().getOnDeviceImageLabeler();

                    detector.processImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionImageLabel> firebaseVisionImageLabels) {

                            StringBuilder info = new StringBuilder();
                            info.append("image labeler");
                            String guess = " ";
                            float p = 0;
                            for(FirebaseVisionImageLabel label :firebaseVisionImageLabels){
                                info.append("Text: ").append(label.getText()).append(",\n").append("Confidence : ").append(label.getConfidence()).append(",\n");
                                if(label.getConfidence() >p){
                                    guess = label.getText();
                                    p = label.getConfidence();
                                }
                            }
                            //System.out.println(info);
                            String sen = "Is this is a "+ guess+" with p: "+p;
                            txtquestion.setText(sen);
                        }
                    });

                } catch (IOException e){
                    e.printStackTrace();
                }



            }
        }

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

                                    Toast.makeText(PickPic.this, "Upload successful", Toast.LENGTH_LONG).show();
                                    Pet pet1 = new Pet();
                                    pet1.setPetName("wangcai");
                                    pet1.setOwnerID("123");
                                    pet1.setComment("teset");
                                    pet1.setPetAge("2");
                                    pet1.setBreed("longhair");
                                    pet1.setCategory("cat");
                                    pet1.setPetID("123123123");
                                    pet1.setImageurl(uri.toString());

                                    //String uploadId = reff.push().getKey();

                                    reff.child("test").child(pet1.getPetID()).setValue(pet1);
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PickPic.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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






}
