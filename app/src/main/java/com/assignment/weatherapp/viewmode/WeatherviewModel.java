package com.assignment.weatherapp.viewmode;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.assignment.weatherapp.data.remote.api.WeatherApiResponse;
import com.assignment.weatherapp.repository.WeatherRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherviewModel extends ViewModel
{
    private WeatherRepository weatherRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<WeatherApiResponse> weatherLiveData = new MutableLiveData<>();

    public MutableLiveData<WeatherApiResponse> weatherResponse() {
        return weatherLiveData;
    }
@Inject
    public WeatherviewModel(WeatherRepository repository) {
        this.weatherRepository = repository;
    }

    public void getWeather(String cordinates)
    {

        disposables.add(weatherRepository.getWeatherByLocation(cordinates)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> weatherLiveData.setValue(WeatherApiResponse.loading()))
                .subscribe(
                        result -> weatherLiveData.setValue(WeatherApiResponse.success(result)),
                        throwable -> weatherLiveData.setValue(WeatherApiResponse.error(throwable))
                ));

    }



    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
