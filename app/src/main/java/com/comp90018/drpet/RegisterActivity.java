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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    EditText email, name, password;
    ImageView profile;
    Button signup;
//    Uri imageUri;
    private FirebaseAuth mAuth;


    DatabaseReference reff;
    Hospital hospital;
    TimeSlot slot;
    Doctor doctor;
    Button btntest;
    String uniqueID = UUID.randomUUID().toString().replace("-","");

    public TimeSlot setSlot(String slotID,String doctorID,String startTime,String flag,String period,String date){
        TimeSlot slot = new TimeSlot();
        slot.setSlotID(slotID);
        slot.setDoctorID(doctorID);
        slot.setStartTime(startTime);
        slot.setFlag(flag);
        slot.setPeriod(period);
        slot.setDate(date);
        return slot;
    }
    public Hospital setHospital(String id,String name,String background,String openhours,
                                String phone,String address){
        Hospital hospital = new Hospital();
        hospital.setHospitalId(id);
        hospital.setHospitalName(name);
        hospital.setHospitalBackground(background);
        hospital.setHospitalOpenhours(openhours);
        hospital.setHospitalPhone(phone);
        hospital.setHospitalAddress(address);
        return hospital;
    }
    public Doctor createDoctor(String doctorId,String hospitalId,
                               String firstName,String lastName,String info,String phone){
        Doctor doctor = new Doctor();
        doctor.setDoctorId(doctorId);
        doctor.setHospitalId(hospitalId);
        doctor.setDoctorFirstName(firstName);
        doctor.setDoctorLastName(lastName);
        doctor.setDoctorInfo(info);
        doctor.setDoctorPhone(phone);
        return doctor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.id);
        password = findViewById(R.id.password);
        name = findViewById(R.id.username);
        profile = findViewById(R.id.img_profile);
        signup = findViewById(R.id.signup);

//        hospital = setHospital(uniqueID,"Epworth Freemasons",
//                "Friends & family are able to visit at anytime throughout the day",
//                "6:00-22:00","(03)94188188",
//                "320 Victoria Parade, East Melbourne VIC 3002");
//        hospital = setHospital(uniqueID,"Vincent's Hospital Melbourne",
//                "Vincent's Hospital is a major hospital in Fitzroy, Melbourne, Australia.",
//                "0:00-24:00","(03)92312211",
//                "41 Victoria Parade, Fitzroy VIC 3065");
//        doctor = createDoctor("0ca1dac7ebda425290d9e8f503a681d7","734b988831344270973e0a11cce4776f",
//                "Saul","Metzstein","An anesthesiologist keeps a patient comfortable, " +
//                        "safe and pain-free during surgery by administering local or general anesthetic.","(03)86637000");
//        doctor = createDoctor("d4b1b0578e204a92b8be35bbe56e31cf","734b988831344270973e0a11cce4776f",
//                "Steven","Moffat","A cardiologist specializes in finding, treating, and preventing diseases that affect the heart," +
//                        " the arteries, and the veins.","(03)94195992");
//        doctor = createDoctor(16d06bc1403f47d399b2c1ed61a2c135,"cc108bb0dfcc4035b3e350a49491552e",
//                "John","Phillips","A cardiothoracic surgeon specializes in surgical procedures inside the thorax (the chest), which may involve the heart," +
//                        " lungs, esophagus, and other organs in the chest.","(03)94171769");
//        doctor = createDoctor("a22d185378934fc587ff174e345eeb71","cc108bb0dfcc4035b3e350a49491552e",
//                "Stanley","Peter","A chiropractor, or doctor of chiropractic medicine, specializes in diagnosing and treating disorders of " +
//                        "the musculoskeletal and nervous system, especially in the spine.","(03)96399600");

        //slot = setSlot(uniqueID,"0ca1dac7ebda425290d9e8f503a681d7","8:00","0","1","2019-08-12");
        //slot = setSlot(uniqueID,"16d06bc1403f47d399b2c1ed61a2c135","16:00","0","1","2019-09-13");
        //slot = setSlot(uniqueID,"a22d185378934fc587ff174e345eeb71","10:00","0","1","2019-08-12");
        slot = setSlot(uniqueID,"d4b1b0578e204a92b8be35bbe56e31cf","11:00","0","1","2019-06-14");
        reff = FirebaseDatabase.getInstance().getReference();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

        btntest = findViewById(R.id.btntest);
        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reff.child("Hospital").child(hospital.getHospitalId()).setValue(hospital);
                //reff.child("Doctor").child(doctor.getDoctorId()).setValue(doctor);
                reff.child("TimeSlot").child(slot.getSlotID()).setValue(slot);
                Toast.makeText(RegisterActivity.this, "success", Toast.LENGTH_SHORT).show();
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
