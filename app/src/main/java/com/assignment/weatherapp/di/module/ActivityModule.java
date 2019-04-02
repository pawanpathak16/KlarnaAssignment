package com.assignment.weatherapp.di.module;

import com.assignment.weatherapp.ui.WeatherActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector()
    abstract WeatherActivity contributesWeatherActivity();
}
