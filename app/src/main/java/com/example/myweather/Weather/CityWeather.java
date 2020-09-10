package com.example.myweather.Weather;

import java.util.ArrayList;

public class CityWeather {
    CurrentWeather mCurrentWeather;
    ArrayList<HourlyWeather> mHourlyWeathers;
    ArrayList<DailyWeather> mDailyWeathers;

    public CityWeather(CurrentWeather cw, ArrayList<HourlyWeather> hws, ArrayList<DailyWeather> dws) {
        this.mCurrentWeather = cw;
        this.mHourlyWeathers = hws;
        this.mDailyWeathers = dws;
    }

    public CurrentWeather getCurrentWeather() {
        return mCurrentWeather;
    }

    public void setCurrentWeather(CurrentWeather mCurrentWeather) {
        this.mCurrentWeather = mCurrentWeather;
    }

    public ArrayList<HourlyWeather> getHourlyWeathers() {
        return mHourlyWeathers;
    }

    public void setHourlyWeather(ArrayList<HourlyWeather> mHourlyWeathers) {
        this.mHourlyWeathers = mHourlyWeathers;
    }

    public ArrayList<DailyWeather> getDailyWeathers() {
        return mDailyWeathers;
    }

    public void setDailyWeather(ArrayList<DailyWeather> mDailyWeathers) {
        this.mDailyWeathers = mDailyWeathers;
    }
}
