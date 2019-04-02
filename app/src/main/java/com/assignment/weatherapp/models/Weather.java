package com.assignment.weatherapp.models;

public class Weather
{
    Double latitude;
    Double longitude;
    String timeZone;
    CurrentWeather currently;

    public CurrentWeather getCurrently() {
        return currently;
    }

    public void setCurrently(CurrentWeather currently) {
        this.currently = currently;
    }


   // List<CurrentWeather> currently;


    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }


}


