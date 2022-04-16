package com.example.covid19tracker.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.covid19tracker.Authentication.LoginActivity;
import com.example.covid19tracker.Home.HomeActivity;
import com.example.covid19tracker.R;
import com.example.covid19tracker.userSession.UserDataa;
import com.example.covid19tracker.DetectorActivity;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvStart;
    UserDataa mUserDataa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initialization();
    }

    private void initialization() {
        tvStart = findViewById(R.id.tv_start);
        tvStart.setOnClickListener(this);
        mUserDataa = new UserDataa(SplashActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start:
//                if (mUserDataa.isLogin())
//                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
//                else
//                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                startActivity(new Intent(SplashActivity.this, DetectorActivity.class));
//
                break;
        }
    }
}