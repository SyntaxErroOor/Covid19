package com.example.covid19tracker.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    private EditText edtFullName, edtAge, edtEmail, edtPassword;
    private ProgressBar pb;

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
        edtPassword = findViewById(R.id.ed_password);
        pb = findViewById(R.id.pb);
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
        String fullName = edtFullName.getText().toString().trim();
        String age = edtAge.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (fullName.isEmpty()) {
            edtFullName.setError("Full Name Is Required");
            edtFullName.requestFocus();
            return;
        }
        if (age.isEmpty()) {
            edtAge.setError("Age Is Required");
            edtAge.requestFocus();
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

        pb.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //start get data and send it to firestore Key and Value
                        String userID = FirebaseAuth.getInstance().getUid();
                        Map<String, String> user = new HashMap<>();
                        user.put("fullName", fullName);
                        user.put("age", age);
                        user.put("email", email);
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("users").document(userID).set(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(RegisterActivity.this, "created successfully", Toast.LENGTH_SHORT).show();
                                        pb.setVisibility(View.GONE);
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
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        // cant register
                        Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                        Log.d("on failure 1 ", e.toString());
                    }
                });
    }
}