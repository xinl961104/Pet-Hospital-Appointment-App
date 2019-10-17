package com.comp90018.drpet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AppointmentAddPetActivity extends AppCompatActivity {
    public String doctorID;
    public String date;
    public String time;
    public Spinner spinner2;
    public String selectedPet;
    TextView show;
    public String userEmail;
    public String userID;
    public String userName;
    public String ownerID;
    public String doctorFirstName;
    public String doctorLastName;
    public String hospitalId = null;
    public String hospitalName;
    Button book;
    public EditText ownerComment;
    public String content;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_add_pet);
        final Intent intent = getIntent();
        doctorID = intent.getStringExtra("DoctorIDAppointment");
        date = intent.getStringExtra("dateforAppointment");
        time = intent.getStringExtra("selectedTime");

        show = (TextView) findViewById(R.id.textView2);
        show.setText(doctorID + date + time);
        spinner2 = (Spinner) findViewById(R.id.PetList);
        book = (Button) findViewById(R.id.BookAppointment);
        ownerComment = (EditText) findViewById(R.id.AddPetComment);
        content = ownerComment.getText().toString(); //gets you the contents of edit text

        // query to correct user for appointment
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            userEmail = user.getEmail();
            userID = user.getUid();
            userName = user.getDisplayName();
        }
        // query all doctors' information for the appointment
        Query queryDoctor = FirebaseDatabase.getInstance().getReference("Doctor").orderByChild("doctorId").equalTo(doctorID);
        queryDoctor.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot DoctorSnapShot:dataSnapshot.getChildren() ){
                        DoctorModel doctor = DoctorSnapShot.getValue(DoctorModel.class);
                        doctorFirstName = doctor.getDoctorFirstName();
                        doctorLastName = doctor.getDoctorLastName();
                        hospitalId = doctor.getHospitalId();
                        Query queryHospital = FirebaseDatabase.getInstance().getReference("Hospital").orderByChild("hospitalId").equalTo(hospitalId);
                        queryHospital.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot HospitalSnapShot: dataSnapshot.getChildren()){
                                    HospitalModel Hospital = HospitalSnapShot.getValue(HospitalModel.class);
                                    hospitalName = Hospital.getHospitalName();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //query the important hospital info from the database for the appointment

        // query all pets' from the correct owner from database
        Query queryPet = FirebaseDatabase.getInstance().getReference("Pet").orderByChild("ownerID").equalTo(userID);

        queryPet.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    final List<String> pets = new ArrayList<>();
                    for(DataSnapshot petSlotSnapShot : dataSnapshot.getChildren()){
                        Pet pet = petSlotSnapShot.getValue(Pet.class);
                        pets.add(pet.getPetName());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, pets);

                    adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override

                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            selectedPet = spinner2.getSelectedItem().toString();
                            Toast.makeText(AppointmentAddPetActivity.this,selectedPet , Toast.LENGTH_SHORT).show();

                        }



                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // the whole booking action is shown here
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // add a new row to the appointmentFinal database;

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("AppointmentFinal");
                String key =  myRef.push().getKey();
                Appointment app = new Appointment(key, doctorID, userID, "123", selectedPet, content, time, date, "xiandong", userEmail, "booked", doctorFirstName, doctorLastName,hospitalName);
                myRef.child(key).setValue(app);//this creates the reqs key-value pair
            //    myRef.child(key).child("title").setValue("Announcing COBOL");//this creates the reqs key-value pair

                // update the timedslot flag in the timeslot table;


                Intent appointment = new Intent(AppointmentAddPetActivity.this, ShowBookInfoActivity.class);

                appointment.putExtra("commentFromOwner", content);
                appointment.putExtra("dateForAppointment", date);
                appointment.putExtra("DoctorFirstName",doctorFirstName );
                appointment.putExtra("DoctorLastName",doctorLastName);
                appointment.putExtra("DoctorId", doctorID);
                appointment.putExtra("HospitalName", hospitalName);
                appointment.putExtra("HospitalId",hospitalId);
                appointment.putExtra("PetName", selectedPet);
                appointment.putExtra("StartTime", time);
                appointment.putExtra("UserEmail", userEmail);
                appointment.putExtra("UserName",userName);
                appointment.putExtra("UserId", userID);

                startActivity(appointment);
            }
        });



    }


}
