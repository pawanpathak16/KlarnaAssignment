package com.assignment.weatherapp.data.remote.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonElement;

import static com.assignment.weatherapp.data.remote.api.Status.ERROR;
import static com.assignment.weatherapp.data.remote.api.Status.LOADING;
import static com.assignment.weatherapp.data.remote.api.Status.SUCCESS;

public class WeatherApiResponse {
    public final Status status;

    @Nullable
    public final JsonElement data;

    @Nullable
    public final Throwable error;

    private WeatherApiResponse(Status status, @Nullable JsonElement data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static WeatherApiResponse loading() {
        return new WeatherApiResponse(LOADING, null, null);
    }

    public static WeatherApiResponse success(@NonNull JsonElement data) {
        return new WeatherApiResponse(SUCCESS, data, null);
    }

    public static WeatherApiResponse error(@NonNull Throwable error) {
        return new WeatherApiResponse(ERROR, null, error);
    }

}
