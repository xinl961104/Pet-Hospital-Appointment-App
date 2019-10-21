package com.comp90018.drpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {

    EditText email, password;
    TextView resetpassword;
    Button signin, signup;
    FirebaseAuth mAuth;
    CheckBox isRememberBox;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.id);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        isRememberBox = (CheckBox)findViewById(R.id.isremember);
        resetpassword = findViewById(R.id.resetpassword);


        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress = email.getText().toString();
                if (!emailAddress.isEmpty()) {
                    mAuth.sendPasswordResetEmail(emailAddress);
                    Toast.makeText(SigninActivity.this, "Password reset mail was sent to your E-mail",
                            Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(SigninActivity.this, "Please enter your username!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });



        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_password",false);
        if (isRemember){
            String usn = pref.getString("name","");
            String pw = pref.getString("word","");
            email.setText(usn);
            password.setText(pw);
            isRememberBox.setChecked(true);
        } else {
            password.setText(null);
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signinEvent();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SigninActivity.this,RegisterActivity.class));
            }
        });
    }


    private void signinEvent() {
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String usn = email.getText().toString();
                            String pw = password.getText().toString();
                            editor = pref.edit();
                            if (isRememberBox.isChecked()){
                                editor.putBoolean("remember_password",true);
                                editor.putString("name",usn);
                                editor.putString("word",pw);
                            } else {
                                password.setText(null);
                                editor.clear();
                            }
                            editor.apply();

                            startActivity(new Intent(SigninActivity.this, HomeActivity.class));


                        } else {

                            Toast.makeText(SigninActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
