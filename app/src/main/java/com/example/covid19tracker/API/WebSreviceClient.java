package com.example.covid19tracker.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebSreviceClient {

    private static Retrofit retrofit = null;
    public static final String BASE_URL = "https://disease.sh/";
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
