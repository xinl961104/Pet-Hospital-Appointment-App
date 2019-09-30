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


    TextView a,b,c,d,e,a1,b1,c1,d1,e1;
    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    static String decodeUserEmail(String userEmail) {
        return userEmail.replace(",", ".");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        info = new AppointmentInfo();
        info.setDate("2019-09-10");
        info.setEmail("123@qq.com");
        info.setMessage("this is a cat");
        info.setPetName("bob");
        info.setPhone("0468999661");

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

        }else{
            this.email = "123";
        }
        reff = FirebaseDatabase.getInstance().getReference().child("AppointmentInfo").child(encodeUserEmail(email));

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxId = (dataSnapshot.getChildrenCount());
                    System.out.println(maxId);
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
                reff1  = FirebaseDatabase.getInstance().getReference().child("AppointmentInfo").child(encodeUserEmail(email));
                reff1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String petName = dataSnapshot.child("1").child("petName").getValue().toString();
                        String date = dataSnapshot.child("1").child("date").getValue().toString();
                        String email = dataSnapshot.child("1").child("email").getValue().toString();
                        String message = dataSnapshot.child("1").child("message").getValue().toString();
                        String phone = dataSnapshot.child("1").child("phone").getValue().toString();
                        a.setText(petName);
                        b.setText(date);
                        c.setText(email);
                        d.setText(message);
                        e.setText(phone);

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
