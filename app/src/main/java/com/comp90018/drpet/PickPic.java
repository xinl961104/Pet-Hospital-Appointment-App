package com.comp90018.drpet;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;

import java.io.IOException;
import java.util.List;

public class PickPic extends AppCompatActivity {
    Button pick,detect;
    ImageView imageView;
    FirebaseAuth firebaseAuth;
    TextView txtquestion;
    private final int REQUEST_PICTURE =1;

//    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
//    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
//    private static final int PHOTO_REQUEST_CUT = 3;// 结果
//
//    private ImageView iv_image;
//
//    /* 头像名称 */
//    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
//    private File tempFile;

//    private AppCompatButton detectBtn;
//    private AppCompatButton pickImageBtn;
//    private AppCompatImageView imageView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick);

        firebaseAuth = FirebaseAuth.getInstance();

        pick = findViewById(R.id.pick);
        imageView = findViewById(R.id.imageView);
        detect = findViewById(R.id.detect);

        txtquestion = findViewById(R.id.txtquestion);

        pick.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //intent.setType("image/*");
                System.out.println("image");
                startActivityForResult(intent,REQUEST_PICTURE);

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
                            System.out.println(info);
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




}
