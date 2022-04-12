package com.example.covid19tracker.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid19tracker.Authentication.LoginActivity;
import com.example.covid19tracker.R;
import com.example.covid19tracker.userSession.UserDataa;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;


public class ProfileFragment extends Fragment {

    private TextView nameTv, phoneTv, emailTv, ageTv, genderTv, dateLastTv, resultLastTv, symtomsLastTv, tvLogOut;
    private ProgressBar pb;
    private UserDataa mDataa;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        initialization(v);
        getProfileData();
        return v;
    }

    private void getProfileData() {
        String userId = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            //hold data into views
                            nameTv.setText(documentSnapshot.getString("fullName"));
                            phoneTv.setText(documentSnapshot.getString("phone"));
                            emailTv.setText(documentSnapshot.getString("email"));
                            ageTv.setText(documentSnapshot.getString("age"));
                            genderTv.setText(documentSnapshot.getString("gender"));
                            dateLastTv.setText(documentSnapshot.getString("date_of_last_check"));
                            resultLastTv.setText(documentSnapshot.getString("result_of_last_check"));
                            symtomsLastTv.setText(documentSnapshot.getString("symtoms_of_last_check"));
                            pb.setVisibility(View.GONE);

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "SomeThing wrong Please Try Again Later", Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                    }
                });
    }

    private void initialization(View v) {
        nameTv = v.findViewById(R.id.tv_name);
        phoneTv = v.findViewById(R.id.tv_phone);
        emailTv = v.findViewById(R.id.tv_email);
        ageTv = v.findViewById(R.id.tv_age);
        genderTv = v.findViewById(R.id.tv_gender);
        dateLastTv = v.findViewById(R.id.tv_date);
        resultLastTv = v.findViewById(R.id.tv_result);
        symtomsLastTv = v.findViewById(R.id.tv_symptoms);
        pb = v.findViewById(R.id.pb);
        pb.setVisibility(View.VISIBLE);
        mDataa=new UserDataa(getContext());
        tvLogOut=v.findViewById(R.id.tv_logout);
        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataa.deleteSavedData();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
    }
}