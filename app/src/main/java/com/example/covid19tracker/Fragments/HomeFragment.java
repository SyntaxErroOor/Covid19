package com.example.covid19tracker.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.covid19tracker.API.ApiInterface;
import com.example.covid19tracker.API.StatisticsResponse;
import com.example.covid19tracker.API.WebSreviceClient;
import com.example.covid19tracker.R;
import com.example.covid19tracker.userSession.UserDataa;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private BottomNavigationView mNav;
    private TextView tvHeader;
    private TextView btnCheckup;
    UserDataa mDataa;
    PieChart mPieChart;
    WebSreviceClient mretrofit;

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        intialization(view);

        return view;
    }

    private void intialization(View view) {
        mDataa = new UserDataa(getContext());
        tvHeader = view.findViewById(R.id.tv_goodmorning);
        tvHeader.setText("Hello, ");
        if (mDataa.getName() != null) {
            tvHeader.setText("Hello, " + mDataa.getName());
        }
        btnCheckup = view.findViewById(R.id.btn_checkup);
        btnCheckup.setOnClickListener(this);
        mNav = getActivity().findViewById(R.id.bottom_navigation);
        mPieChart = view.findViewById(R.id.pie_chart);
        callApi();

    }

    private void callApi() {
        ApiInterface apiInterface = WebSreviceClient.getRetrofit().create(ApiInterface.class);
        Call<StatisticsResponse> call = apiInterface.getStatistics();
        call.enqueue(new Callback<StatisticsResponse>() {
            @Override
            public void onResponse(Call<StatisticsResponse> call, Response<StatisticsResponse> response) {
                if (response.body() != null) {
                    int cases = Integer.parseInt(response.body().getTodayCases());
                    int recovered = Integer.parseInt(response.body().getTodayRecovered());
                    int deaths = Integer.parseInt(response.body().getTodayDeaths());
//                    int totalToday=cases+recovered+deaths;
//                    Log.d("TAG", totalToday+"");
                    updatePieChart(cases,recovered,deaths);
                }
            }

            @Override
            public void onFailure(Call<StatisticsResponse> call, Throwable t) {
                    Log.d("onFaliure", t.toString());
            }
        });
    }

    private void updatePieChart(int cases, int recovered, int deaths) {
        mPieChart.clearChart();
//        int totalToday=cases+recovered+deaths;
//        mPieChart.addPieSlice(new PieModel("Total",totalToday,Color.parseColor("#FFB701")));
        mPieChart.addPieSlice(new PieModel("Cases",cases,Color.parseColor("#FF4CAF50")));
        mPieChart.addPieSlice(new PieModel("Recoverd",recovered,Color.parseColor("#38ACCD")));
        mPieChart.addPieSlice(new PieModel("Deaths",deaths,Color.parseColor("#F55C47")));
        mPieChart.startAnimation();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.btn_show:
//                break;
            case R.id.btn_checkup:
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction().replace(R.id.fragment_container,
//                        new NewsFragment()).commit();
                break;
        }

    }
}