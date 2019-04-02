package com.assignment.weatherapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.assignment.weatherapp.data.remote.api.WeatherApiService;
import com.google.gson.JsonElement;


import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


@Singleton
public class WeatherRepository {

 private WeatherApiService weatherApiService;
@Inject
 public WeatherRepository(WeatherApiService weatherApiService)
 {
     this.weatherApiService=weatherApiService;
 }

 public Observable<JsonElement> getWeatherByLocation(String cordinates)
 {
     return weatherApiService.fetchWeatherByLocation(cordinates);
 }

}
