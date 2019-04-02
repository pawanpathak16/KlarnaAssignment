package com.assignment.weatherapp.di.component;

import android.app.Application;

import com.assignment.weatherapp.AppController;
import com.assignment.weatherapp.di.module.ActivityModule;
import com.assignment.weatherapp.di.module.ApiModule;
import com.assignment.weatherapp.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules={ApiModule.class, ViewModelModule.class, ActivityModule.class, AndroidSupportInjectionModule.class})
public interface ApiComponent {


    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder apiModule(ApiModule apiModule);



        ApiComponent build();

    }
    void inject(AppController appController);
}
