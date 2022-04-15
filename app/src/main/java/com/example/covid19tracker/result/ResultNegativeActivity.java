package com.example.covid19tracker.result;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.covid19tracker.Call_Doctor.call_doctor;
import com.example.covid19tracker.Home.HomeActivity;
import com.example.covid19tracker.R;
import com.example.covid19tracker.Splash.SplashActivity;

public class ResultNegativeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView homeBtn, callDoctorBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_negative);
        intialization();
    }

    private void intialization() {
        homeBtn=findViewById(R.id.home_btn);
        homeBtn.setOnClickListener(this);
        callDoctorBtn=findViewById(R.id.call_doctor_btn);
        callDoctorBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_btn:
                //return to home
                break;
            case R.id.call_doctor_btn:
                //navigate to call doctor
                startActivity(new Intent(ResultNegativeActivity.this, call_doctor.class));
                break;
        }

    }
}