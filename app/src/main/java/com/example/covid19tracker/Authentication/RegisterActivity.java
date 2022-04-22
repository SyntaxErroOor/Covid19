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
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid19tracker.Home.HomeActivity;
import com.example.covid19tracker.R;
import com.example.covid19tracker.userSession.UserDataa;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvLogIn;
    private FirebaseAuth mAuth;
    UserDataa mUserDataa;
    private FloatingActionButton btnRegister;
    private EditText edtFullName, edtEmail, edtPhone, edtPassword, edtAge;
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
        edtPhone = findViewById(R.id.ed_phone);
        edtPassword = findViewById(R.id.ed_password);
        radioGroup = findViewById(R.id.radio_group);
        Pb = findViewById(R.id.pb);
        mUserDataa = new UserDataa(RegisterActivity.this);
        tvLogIn = findViewById(R.id.tv_login);
        tvLogIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                registerUser();
                break;
            case R.id.tv_login:
                Intent i = new Intent(this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
        }

    }

    private void registerUser() {
        String fullName = edtFullName.getText().toString().trim();
        String age = edtAge.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String gender = "";
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

        if (!Patterns.PHONE.matcher(phone).matches()) {
            edtPhone.setError("Please Enter a Valid Number");
            edtPhone.requestFocus();
            return;
        }

        if (phone.length() < 11) {
            edtPhone.setError("Please Enter a Valid Number");
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

        if (age.length() >= 3) {
            edtAge.setError("Please Enter A Valid Age");
            edtAge.requestFocus();
            return;
        }

        if (checkedID == -1) {
            Toast.makeText(this, "please select your gender", Toast.LENGTH_LONG).show();
            return;
        }
        switch (checkedID) {
            case R.id.radio_male:
                gender = "Male";
                break;
            case R.id.radio_female:
                gender = "Female";
                break;
        }

        Pb.setVisibility(View.VISIBLE);

        String finalGender = gender;

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        String userID = FirebaseAuth.getInstance().getUid();
                        Map<String, String> user = new HashMap<>();
                        user.put("user_image_url","");
                        user.put("fullName", fullName);
                        user.put("email", email);
                        user.put("phone", phone);
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
                                        Toast.makeText(RegisterActivity.this, "Account Created Successfully", Toast.LENGTH_LONG).show();
                                        Pb.setVisibility(View.GONE);
                                        mUserDataa.saveData(email, fullName, userID, true);
                                        Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // cant register
                        Pb.setVisibility(View.GONE);
                        if (e instanceof FirebaseNetworkException) {
                            Toast.makeText(RegisterActivity.this, "Check Your internet Connection", Toast.LENGTH_SHORT).show();
                        } else {
                            String errorCode = ((FirebaseAuthException) e).getErrorCode();
                            String error = getErrorMessage(errorCode);
                            Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private String getErrorMessage(String errorCode) {
        switch (errorCode) {
            case "ERROR_INVALID_EMAIL":
            case "ERROR_USER_NOT_FOUND":
            case "ERROR_WRONG_PASSWORD":
                return "Invalid Email Or Password";
            case "ERROR_EMAIL_ALREADY_IN_USE":
            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                return "this Email Is Already Used";
            default:
                return "Something went wrong, Please try again Later";
        }
    }
}