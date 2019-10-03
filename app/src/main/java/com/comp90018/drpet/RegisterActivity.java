package com.comp90018.drpet;

import helper.AppointmentDeleter;
import helper.AppointmentRetriever;
import helper.HospitalRetriever;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    EditText email, name, password;
    ImageView profile;
    Button signup;
//    Uri imageUri;
    private FirebaseAuth mAuth;

    DatabaseReference reff;
    Appointment appointment;
    Pet pet;
    Hospital hospital;
    TimeSlot slot;
    Doctor doctor;
    Button btntest;
    String uniqueID = UUID.randomUUID().toString().replace("-","");

//    AppointmentRetriever retriever = new AppointmentRetriever();



    public Appointment setAppointment(String appointmentID,String doctorID,String userID,String petID,
                                      String petName,String comment,String startTime, String date,String userName,
                                      String userEmail,String status){
        Appointment appointment = new Appointment();
        appointment.setAppointmentID(appointmentID);
        appointment.setDoctorID(doctorID);
        appointment.setUserID(userID);
        appointment.setPetID(petID);
        appointment.setPetName(petName);
        appointment.setComment(comment);
        appointment.setStartTime(startTime);
        appointment.setDate(date);
        appointment.setUserName(userName);
        appointment.setUserEmail(userEmail);
        appointment.setStatus(status);
        return appointment;
    }
    public Pet setPet(String petID, String category,String breed,String petAge,
                      String comment,String ownerID,String petName){
        Pet pet = new Pet();
        pet.setPetID(petID);
        pet.setCategory(category);
        pet.setBreed(breed);
        pet.setPetAge(petAge);
        pet.setComment(comment);
        pet.setOwnerID(ownerID);
        pet.setPetName(petName);
        return pet;
    }
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
        //slot = setSlot(uniqueID,"d4b1b0578e204a92b8be35bbe56e31cf","11:00","0","1","2019-06-14");
        //pet = setPet(uniqueID,"dog","sheepdog","5","he doesn't eat anything",
          //      "vzD5yGDcH8XmXITdow7iReBAhAq1","dobby");
//        pet = setPet(uniqueID,"cat","ragdoll","2","Inbreeding",
//                     "vzD5yGDcH8XmXITdow7iReBAhAq1","wangcai");
//        pet = setPet(uniqueID,"dog","retriever","4","Excessive drinking and urination",
//                     "UijkHkIVSXXaDX7spJyrPg5AXW83","vane");
//        pet = setPet(uniqueID,"cat","british long hair","3","Bad breath",
//                "UijkHkIVSXXaDX7spJyrPg5AXW83","Max");

//        pet = setPet(uniqueID,"cat","british long hair","5","Bad breath",
//                "lpM91PpV79c5W8X0AHr7xYCeXQF2","fugui");
//        appointment = setAppointment(uniqueID,"16d06bc1403f47d399b2c1ed61a2c135","vzD5yGDcH8XmXITdow7iReBAhAq1",
//                "38a47786f63043adb39f30656b45f05c","dobby","he doesn't eat anything","16:00","2019-09-12",
//                "qwe","123455@163.com","booked");
        //        appointment = setAppointment(uniqueID,"16d06bc1403f47d399b2c1ed61a2c135","vzD5yGDcH8XmXITdow7iReBAhAq1",
//                "befef5c52d6042a3bff2c356c1a68424","wangcai","Inbreeding","16:00","2019-09-13",
//                "qwe","123455@163.com","booked");

//        appointment = setAppointment("4349f9744286464dbeebda49f7ab7287","d4b1b0578e204a92b8be35bbe56e31cf","lpM91PpV79c5W8X0AHr7xYCeXQF2",
//                "70a28c3d342c408488ce31ead5ab93a9","fugui","she doesn't eat anything","16:00","2019-06-12",
//                "xiandong","xiandong@abc.com","booked");

//        appointment = setAppointment("42044cde3e0747b2b60269112af2f481","d4b1b0578e204a92b8be35bbe56e31cf","lpM91PpV79c5W8X0AHr7xYCeXQF2",
//                "70a28c3d342c408488ce31ead5ab93a9","fugui","she doesn't eat anything","11:00","2019-06-14",
//                "xiandong","xiandong@abc.com","booked");

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
                //retrieve appointment info
//                AppointmentRetriever retriever = new AppointmentRetriever("lpM91PpV79c5W8X0AHr7xYCeXQF2");
//                retriever.retrievData(new AppointmentRetriever.FirebaseCallback() {
//                    @Override
//                    public void onCallback(ArrayList<Appointment> list) {
//                        System.out.println(list.size());
//                        for(int i =0; i<list.size();i++){
//                            //if (list.get(i).getUserID().equals("lpM91PpV79c5W8X0AHr7xYCeXQF2")){
//                                System.out.println(list.get(i));
//                            //}
//
//                        }
//
//                    }
//                });


//                // delete appointment by appointmentID
//                AppointmentDeleter deleter = new AppointmentDeleter("1349f9744286464dbeebda49f7ab7287");
//                deleter.deleteData(new AppointmentDeleter.FirebaseCallback() {
//                    @Override
//                    public void onCallback(String message) {
//                        if(message.equals("delete")){
//                            System.out.println("success delete");
//                        }
//
//                    }
//                });


                HospitalRetriever retriever1 = new HospitalRetriever("0ca1dac7ebda425290d9e8f503a681d7");
                retriever1.retrieveHospitalByDoctorID(new HospitalRetriever.FirebaseCallback1() {
                    @Override
                    public void onCallback(Doctor doctor1) {
                        doctor = doctor1;
                        System.out.println(doctor1);
                    }
                });

                HospitalRetriever retriever = new HospitalRetriever();
                retriever.retrievData(new HospitalRetriever.FirebaseCallback() {
                    @Override
                    public void onCallback(ArrayList<Hospital> list) {
                        //System.out.println(list.size());
                        for(int i =0; i<list.size();i++){
                            if(doctor.getHospitalId().equals(list.get(i).getHospitalId())){
                                System.out.println(list.get(i));
                                System.out.println(doctor.getDoctorLastName()+doctor.getDoctorFirstName());
                            }
                            //if (list.get(i).getUserID().equals("lpM91PpV79c5W8X0AHr7xYCeXQF2")){
                            //System.out.println(list.get(i));
                            //}

                        }

                    }
                });





                //reff.child("Hospital").child(hospital.getHospitalId()).setValue(hospital);
                //reff.child("Doctor").child(doctor.getDoctorId()).setValue(doctor);
                //reff.child("TimeSlot").child(slot.getSlotID()).setValue(slot);
                //reff.child("Pet").child(pet.getPetID()).setValue(pet);
                //reff.child("AppointmentNew").child(appointment.getUserID()).child(appointment.getAppointmentID()).setValue(appointment);
                //reff.child("Appointmentinfo").child(appointment.getAppointmentID()).setValue(appointment);
                //reff.child("Appointment").child(appointment.getAppointmentID()).setValue(appointment);

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
