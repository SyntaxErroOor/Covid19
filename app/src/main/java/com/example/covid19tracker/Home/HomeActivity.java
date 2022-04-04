package com.example.covid19tracker.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ScrollView;

import com.example.covid19tracker.Fragments.HomeFragment;
import com.example.covid19tracker.Fragments.NewsFragment;
import com.example.covid19tracker.Fragments.ProfileFragment;
import com.example.covid19tracker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private BottomNavigationView mNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialization();
    }

    private void initialization() {
        replaceFragment(new HomeFragment());
        mNav = findViewById(R.id.bottom_navigation);
        mNav.setOnItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:

                replaceFragment(new HomeFragment());
                break;
            case R.id.nav_news:
                replaceFragment(new NewsFragment());
                break;

            case R.id.nav_profile:
                replaceFragment(new ProfileFragment());
                break;
        }
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}