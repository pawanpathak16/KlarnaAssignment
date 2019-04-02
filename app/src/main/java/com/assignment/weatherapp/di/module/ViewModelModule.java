package com.assignment.weatherapp.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.assignment.weatherapp.factory.ViewModelFactory;
import com.assignment.weatherapp.viewmode.WeatherviewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule
{
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(WeatherviewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsWeatherviewModel(WeatherviewModel weatherViewModel);
}
