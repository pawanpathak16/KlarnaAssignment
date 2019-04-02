package com.assignment.weatherapp.data.remote.api;

import com.google.gson.JsonElement;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherApiService
{
    @GET("forecast/2bb07c3bece89caf533ac9a5d23d8417/{cordinates}/")
    Observable<JsonElement> fetchWeatherByLocation(@Path ("cordinates") String cordinates);
}
