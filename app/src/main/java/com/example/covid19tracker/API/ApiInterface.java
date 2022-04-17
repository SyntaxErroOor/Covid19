package com.example.covid19tracker.API;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("v3/covid-19/all")
    Call<StatisticsResponse> getStatistics();
}
