package com.assignment.weatherapp.ui;

import androidx.lifecycle.ViewModelProviders;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.weatherapp.R;
import com.assignment.weatherapp.models.Weather;
import com.assignment.weatherapp.data.remote.api.WeatherApiResponse;
import com.assignment.weatherapp.factory.ViewModelFactory;
import com.assignment.weatherapp.viewmode.WeatherviewModel;
import com.google.gson.Gson;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class WeatherActivity extends AppCompatActivity implements LocationListener {


    private LocationManager locationManager;
    private String provider;

    Double latitude;
    Double longitude;

    @Inject
    ViewModelFactory viewModelFactory;
    WeatherviewModel viewModel;

    @BindView(R.id.textView_latitude)
    TextView mTextView_latitude;
    @BindView(R.id.textView_longitude)
    TextView mTextView_longitude;
    @BindView(R.id.textView_timezone)
    TextView mTextView_timezone;
    @BindView(R.id.textView_time)
    TextView mTextView_time;
    @BindView(R.id.textView_summary)
    TextView mTextView_summary;
    @BindView(R.id.textView_precipIntensity)
    TextView mTextView_precipIntensity;
    @BindView(R.id.textView_precipProbability)
    TextView mTextView_precipProbability;
    @BindView(R.id.textView_temperature)
    TextView mTextView_temperature;
    @BindView(R.id.textView_apparentTemperature)
    TextView mTextView_apparentTemperature;
    @BindView(R.id.textView_dewPoint)
    TextView mTextView_dewPoint;
    @BindView(R.id.textView_humidity)
    TextView mTextView_humidity;
    @BindView(R.id.textView_pressure)
    TextView mTextView_pressure;
    @BindView(R.id.textView_windSpeed)
    TextView mTextView_windSpeed;
    @BindView(R.id.textView_windGust)
    TextView mTextView_windGust;
    @BindView(R.id.textView_windBearing)
    TextView mTextView_windBearing;
    @BindView(R.id.textView_cloudCover)
    TextView mTextView_cloudCover;
    @BindView(R.id.textView_uvIndex)
    TextView mTextView_uvIndex;
    @BindView(R.id.textView_visibility)
    TextView mTextView_visibility;
    @BindView(R.id.textView_ozone)
    TextView mTextView_ozone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weather);
        ButterKnife.bind(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            Toast.makeText(getApplicationContext(), "Location not available", Toast.LENGTH_LONG).show();
        }


        viewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherviewModel.class);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherviewModel.class);

        viewModel.weatherResponse().observe(this, this::consumeResponse);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
       String cordinates=latitude+","+longitude;
        viewModel.getWeather(cordinates);
    }

    private void consumeResponse(WeatherApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
               // progressDialog.show();
                break;

            case SUCCESS:
                System.out.println("---->"+apiResponse.data);

                    Weather weather = new Gson().fromJson(apiResponse.data, Weather.class);
                    System.out.println("current weather "+weather.getLongitude()+"---"+weather.getLatitude()+"---"+weather.getCurrently().getSummary());
                    updateWeatherUi(weather);


               //

               // renderSuccessResponse(apiResponse.data);
                break;

            case ERROR:
                Log.e("error",apiResponse.error.getMessage());
               // progressDialog.dismiss();
               // Toast.makeText(LoginActivity.this,getResources().getString(R.string.errorString), Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }



    }
    public void updateWeatherUi(Weather weather)
    {
        System.out.println("Timezone "+weather.getTimeZone());
        mTextView_latitude.setText("Latitude: "+weather.getLatitude());
        mTextView_longitude.setText("Longitude: "+weather.getLongitude());
        if(weather.getTimeZone()==null) {
            String timeZone="NA";
            mTextView_timezone.setText("Time Zone: " + timeZone);
        }
        else{
            mTextView_timezone.setText("Time Zone: " + weather.getTimeZone());
        }

        String summary=weather.getCurrently().getSummary();
        Double temprature=weather.getCurrently().getTemprature();
        Double apparentTemp=weather.getCurrently().getApparentTemperature();
        Double cloudCover=weather.getCurrently().getCloudCover();
        Double dewPoint=weather.getCurrently().getDewPoint();
        Double humidity=weather.getCurrently().getHumidity();
        Double ozone=weather.getCurrently().getOzone();
        Double pressure=weather.getCurrently().getPressure();
        Integer precipIntensity=weather.getCurrently().getPrecipIntensity();
        Integer precipProbability=weather.getCurrently().getPrecipProbability();
        Integer uvIndex=weather.getCurrently().getUvIndex();
        Double visibility=weather.getCurrently().getVisibility();
        Double windBearing=weather.getCurrently().getWindBearing();
        Double windSpeed=weather.getCurrently().getWindSpeed();
        Double windDust=weather.getCurrently().getWindGust();
        Long timeinMillis=weather.getCurrently().getTime();
        String time= "NA";







        if(summary==null)
        {
            summary="NA";
        }

        if(temprature==null)
        {
            temprature=0.0;
        }
        if(apparentTemp==null)
        {
            apparentTemp=0.0;
        }
        if(cloudCover==null)
        {
            cloudCover=0.0;
        }
        if(dewPoint==null)
        {
            dewPoint=0.0;
        }
        if(humidity==null)
        {
            humidity=0.0;
        }
        if(ozone==null)
        {
            ozone=0.0;
        }
        if(pressure==null)
        {
            pressure=0.0;
        }
        if(precipIntensity==null)
        {
            precipIntensity=0;
        }
        if(precipProbability==null)
        {
            precipProbability=0;
        }
        if(uvIndex==null)
        {
            uvIndex=0;
        }
        if(visibility==null)
        {
            visibility=0.0;
        }
        if(windBearing==null)
        {
            windBearing=0.0;
        }
        if(windSpeed==null)
        {
            windSpeed=0.0;
        }
        if(windDust==null)
        {
            windDust=0.0;
        }
        if(timeinMillis!=null)
        {
            time=new Date(timeinMillis).toString();
        }






        mTextView_apparentTemperature.setText("Apparent Temprature "+apparentTemp);
        mTextView_cloudCover.setText("Cloud Cover "+cloudCover);
        mTextView_dewPoint.setText(" Dew Point :"+dewPoint);
        mTextView_humidity.setText("Humidity :"+humidity);
        mTextView_ozone.setText("Ozone :"+ozone);
        mTextView_summary.setText(" Summary :"+summary);
        mTextView_temperature.setText("Temprature :"+temprature);
        mTextView_precipIntensity.setText("PrecipIntensity :"+precipIntensity);
        mTextView_precipProbability.setText("PrecipProbability :"+precipProbability);
        mTextView_pressure.setText("Pressure :"+pressure);
        mTextView_visibility.setText("Visibility :"+visibility);
        mTextView_uvIndex.setText("UV Index :"+uvIndex);
        mTextView_windBearing.setText("Wind Bearing :"+windBearing);
        mTextView_windSpeed.setText("WindSpeed :"+windSpeed);
        mTextView_windGust.setText("Wind Dust :"+windDust);
        mTextView_time.setText("Time :"+time);










    }

    @Override
    public void onLocationChanged(Location location)
    {
        longitude=location.getLongitude();
        latitude=location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s)
    {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String s)
    {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();

    }
}
