package com.example.covid19tracker.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid19tracker.Home.HomeActivity;
import com.example.covid19tracker.R;
import com.example.covid19tracker.userSession.UserDataa;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth mAuth;

    private TextView tvRegister;
    private EditText edtEmail, edtPassword;
    private FloatingActionButton btnLogIn;
    private FrameLayout pb;
    UserDataa mUserDataa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialization();
    }

    private void initialization() {
        mAuth=FirebaseAuth.getInstance();
        tvRegister = findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(this);
        edtEmail=findViewById(R.id.ed_user);
        edtPassword=findViewById(R.id.ed_password);
        btnLogIn=findViewById(R.id.btn_login);
        btnLogIn.setOnClickListener(this);
        pb=findViewById(R.id.pb);
        mUserDataa=new UserDataa(LoginActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.btn_login:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = edtEmail.getText().toString().trim();
        String password= edtPassword.getText().toString().trim();

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

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // get id then retrive user data from firestore to use it later
                        String userID= FirebaseAuth.getInstance().getUid();
                        Log.d("TAG", userID.toString());
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("users").document(userID).get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if(documentSnapshot.exists()){
                                            //hold data into shared preferences
                                            mUserDataa.saveData(documentSnapshot.getString("email"),documentSnapshot.getString("fullName"),userID,true);
                                        }
                                    }
                                });
                        pb.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                        //redirect to home activity
                        Intent i=new Intent(LoginActivity.this, HomeActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pb.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Invalid Email Or Password", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}