package com.example.myweather.WeatherFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myweather.R;
import com.example.myweather.Weather.CityWeather;
import com.example.myweather.Weather.CityWeatherLab;

public class CityWeatherFragment extends Fragment {

    private static final String ARG_CITY_NAME = "CityName";

    private String mCityName;
    private CurrentWeatherFragment mCurrentWeatherFragment;
    private HourlyWeatherFragment mHourlyWeatherFragment;
    private DailyWeatherFragment mDailyWeatherFragment;

    public CityWeatherFragment() {
    }

    public static CityWeatherFragment newInstance(String CityName) {
        Log.d("CREATE_FRAGMENT", "newInstance: " + CityName);
        CityWeatherFragment fragment = new CityWeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY_NAME, CityName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCityName = getArguments().getString(ARG_CITY_NAME);
            Log.d("CREATE_FRAGMENT", "onCreate: " + mCityName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_weather, container, false);

        FragmentManager fm = getChildFragmentManager();
        Fragment fragment = null;

        fragment = fm.findFragmentById(R.id.fragment_current_weather);
        if (fragment == null) {
            mCurrentWeatherFragment = CurrentWeatherFragment.newInstance(mCityName);
            fm.beginTransaction().add(R.id.fragment_current_weather, mCurrentWeatherFragment).commit();
        }

        fragment = fm.findFragmentById(R.id.fragment_hourly_weather);
        if (fragment == null) {
            mHourlyWeatherFragment = HourlyWeatherFragment.newInstance(mCityName);
            fm.beginTransaction().add(R.id.fragment_hourly_weather, mHourlyWeatherFragment).commit();
        }

        fragment = fm.findFragmentById(R.id.fragment_daily_weather);
        if (fragment == null) {
            mDailyWeatherFragment = DailyWeatherFragment.newInstance(mCityName);
            fm.beginTransaction().add(R.id.fragment_daily_weather, mDailyWeatherFragment).commit();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        Log.d("HTTP", "updateUI: " + mCityName);
        CityWeather cw = CityWeatherLab.getCityWeatherLab().getCityWeather(mCityName);
        if (cw == null) return;
        mCurrentWeatherFragment.updateUI(cw.getCurrentWeather());
        mHourlyWeatherFragment.updateUI(cw.getHourlyWeathers());
        mDailyWeatherFragment.updateUI(cw.getDailyWeathers());
    }
}