package com.comp90018.drpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class updateActivity extends AppCompatActivity {
    Button btnretrive, btnupdate;
    FirebaseAuth mAuth;
    DatabaseReference reff,reff1;
    String email;
    AppointmentInfo info;
    long maxId = 0;
    String UID;
    Appointment[] appointment;

    TextView a,b,c,d,e,a1,b1,c1,d1,e1;
    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    static String decodeUserEmail(String userEmail) {
        return userEmail.replace(",", ".");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        info = new AppointmentInfo();
        info.setDate("2019-09-10");
        info.setEmail("123@qq.com");
        info.setMessage("this is a cat");
        info.setPetName("bob");
        info.setPhone("0468999661");
        info.setCustomerName("bob's dad");
        info.setHospital("melbourne");
        info.setDoctorName("dobby");

        btnupdate=(Button)findViewById(R.id.btnupdate);
        btnretrive=(Button)findViewById(R.id.btnretrive);
        a =(TextView)findViewById(R.id.a);
        b =(TextView)findViewById(R.id.b);
        c =(TextView)findViewById(R.id.c);
        d =(TextView)findViewById(R.id.d);
        e =(TextView)findViewById(R.id.e);
        a1 =(TextView)findViewById(R.id.a1);
        b1 =(TextView)findViewById(R.id.b1);
        c1 =(TextView)findViewById(R.id.c1);
        d1 =(TextView)findViewById(R.id.d1);
        e1 =(TextView)findViewById(R.id.e1);

       // System.out.println("0");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Name, email address, and profile photo Url
            this.email = user.getEmail();
            this.UID = user.getUid();
//            System.out.println(UID);

        }else{
            this.email = "123";
        }
        reff = FirebaseDatabase.getInstance().getReference().child("AppointmentInfo").child(encodeUserEmail(email));

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxId = (dataSnapshot.getChildrenCount());
//                    System.out.println(maxId);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //System.out.println("2");
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                reff.child(String.valueOf(maxId+1)).setValue(info);
                Toast.makeText(updateActivity.this, "success connect", Toast.LENGTH_LONG ).show();
            }
        });


        btnretrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                reff1  = FirebaseDatabase.getInstance().getReference().child("AppointmentNew").child(UID);
                //reff1  = FirebaseDatabase.getInstance().getReference().child("AppointmentInfo").child(encodeUserEmail(email));
                reff1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long times;
                        if (dataSnapshot.exists()) {
                            times = (dataSnapshot.getChildrenCount());
                            //System.out.println(times);
                            appointment = new Appointment[(int)times];
                            //System.out.println(appointment.length);

                            int i = 0;
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                appointment[i] = new Appointment();
                                String date = ds.child("date").getValue(String.class);
                                String appointmentID = ds.child("appointmentID").getValue(String.class);
                                appointment[i].setAppointmentID(appointmentID);
                                appointment[i].setComment(ds.child("comment").getValue(String.class));appointment[i].setDate(ds.child("date").getValue(String.class));
                                appointment[i].setDoctorID(ds.child("doctorID").getValue(String.class));
                                appointment[i].setPetID(ds.child("petID").getValue(String.class));
                                appointment[i].setPetName(ds.child("petName").getValue(String.class));
                                appointment[i].setStartTime(ds.child("startTime").getValue(String.class));
                                appointment[i].setStatus(ds.child("status").getValue(String.class));
                                appointment[i].setUserEmail(ds.child("userEmail").getValue(String.class));
                                appointment[i].setUserID(ds.child("userID").getValue(String.class));
                                appointment[i].setUserName(ds.child("userName").getValue(String.class));
//                            if(date.equals("2019-09-12")){
                                // //update value
//                                ds.child("email").getRef().setValue("123455@163.com");
                                // // delete value
//                                //ds.getRef().removeValue();
//                            }
                                System.out.println(i);
                                System.out.println(appointment[i]);
                                //System.out.println(date);
                                //System.out.println(appointment[i].getAppointmentID());
                                i++;
                            }
//                        String petName = dataSnapshot.child("1").child("petName").getValue().toString();
//                        String date = dataSnapshot.child("1").child("date").getValue().toString();
//                        String email = dataSnapshot.child("1").child("email").getValue().toString();
//                        String message = dataSnapshot.child("1").child("message").getValue().toString();
//                        String phone = dataSnapshot.child("1").child("phone").getValue().toString();
//                        a.setText(petName);
//                        b.setText(date);
//                        c.setText(email);
//                        d.setText(message);
//                        e.setText(phone);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //reff.child(String.valueOf(maxId+1)).setValue(info);
                Toast.makeText(updateActivity.this, "success connect", Toast.LENGTH_LONG ).show();
            }
        });




    }
}
