package com.assignment.weatherapp;

import android.app.Activity;
import android.app.Application;


import com.assignment.weatherapp.di.component.DaggerApiComponent;
import com.assignment.weatherapp.di.module.ApiModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class AppController extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        DaggerApiComponent.builder()
                .application(this)
                .apiModule(new ApiModule())
                .build()
                .inject(this);
    }
}
