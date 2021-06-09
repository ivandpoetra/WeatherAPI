package com.ivanadiputra.weatherapi.retrofit;

import com.ivanadiputra.weatherapi.settergetter.MainModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiEndpoint {

    @GET("current.json?key=f25c62c89a7b4794a0a91322210806&q=Jakarta&aqi=no")
    Call<MainModel> getCurrentData();
    @GET("forecast.json?key=f25c62c89a7b4794a0a91322210806&q=Jakarta&days=3&aqi=no&alerts=no")
    Call<MainModel> getForecastData();
}
