package com.example.covid19tracker.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covid19tracker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class NewsFragment extends Fragment {

//    private BottomNavigationView mNav;
    public NewsFragment() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_news, container, false);



        return v;
    }
}