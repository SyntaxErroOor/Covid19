package com.example.covid19tracker.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.covid19tracker.Home.HomeActivity;
import com.example.covid19tracker.R;
import com.example.covid19tracker.userSession.UserDataa;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    UserDataa mUserDataa;
    private FloatingActionButton btnRegister;
    private EditText edtFullName, edtEmail, edtPhone,  edtPassword,edtAge;
    private FrameLayout Pb;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialization();
    }

    private void initialization() {
        mAuth = FirebaseAuth.getInstance();
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        edtFullName = findViewById(R.id.ed_fullname);
        edtAge = findViewById(R.id.ed_age);
        edtEmail = findViewById(R.id.ed_email);
        edtPhone=findViewById(R.id.ed_phone);
        edtPassword = findViewById(R.id.ed_password);
        radioGroup=findViewById(R.id.radio_group);
        Pb = findViewById(R.id.pb);
        mUserDataa=new UserDataa(RegisterActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                registerUser();
                break;
        }

    }

    private void registerUser() {
        Log.d("يارب تشتغل", "registerUser: ");
        String fullName = edtFullName.getText().toString().trim();
        String age = edtAge.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String gender="";
        int checkedID = radioGroup.getCheckedRadioButtonId();

        if (fullName.isEmpty()) {
            edtFullName.setError("Full Name Is Required");
            edtFullName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            edtEmail.setError("Email Is Required");
            edtEmail.requestFocus();
            return;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Please enter a valid email");
            edtEmail.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            edtPhone.setError("Phone Is Required");
            edtPhone.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edtPassword.setError("Password Is Required");
            edtPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            edtPassword.setError("min password length should be > 6");
            edtPassword.requestFocus();
            return;
        }

        if (age.isEmpty()) {
            edtAge.setError("Age Is Required");
            edtAge.requestFocus();
            return;
        }

        if(checkedID==-1){
            Toast.makeText(this, "please select your gender", Toast.LENGTH_LONG).show();
            return;
        }
        switch (checkedID){
            case R.id.radio_male:
                gender="Male";
                break;
            case R.id.radio_female:
                gender="Female";
                break;
        }
        Pb.setVisibility(View.VISIBLE);
        String finalGender = gender;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //start get data and send it to firestore Key and Value
                        String userID = FirebaseAuth.getInstance().getUid();
                        Map<String, String> user = new HashMap<>();
                        user.put("fullName", fullName);
                        user.put("email", email);
                        user.put("phone",phone);
                        user.put("age", age);
                        user.put("gender", finalGender);
                        user.put("date_of_last_check", "Go Check!");
                        user.put("result_of_last_check", "Go Check!");
                        user.put("symtoms_of_last_check", "Go Check!");

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("users").document(userID).set(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(RegisterActivity.this, "created successfully", Toast.LENGTH_SHORT).show();
                                        Pb.setVisibility(View.GONE);
                                        //save data to sharedPreferences
                                        mUserDataa.saveData(email,fullName,userID,true);
                                        // here redirect but later
                                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("on failure 2 ", e.toString());
                                        Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_LONG).show();

                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        // cant register
                        Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        Pb.setVisibility(View.GONE);
                        Log.d("on failure 1 ", e.toString());
                    }
                });
    }
}