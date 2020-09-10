package com.example.myweather.Weather;

import java.util.HashMap;

public class CityWeatherLab {
    private static CityWeatherLab mCityWeatherLab;

    private HashMap<String, CityWeather> mCityWeathers;

    private CityWeatherLab() {
        mCityWeathers = new HashMap<String, CityWeather>();
    }

    public static CityWeatherLab getCityWeatherLab() {
        if (mCityWeatherLab == null) {
            mCityWeatherLab = new CityWeatherLab();
        }
        return mCityWeatherLab;
    }

    public void put(String CityName, CityWeather cw) {
        mCityWeathers.put(CityName, cw);
    }

    public CityWeather getCityWeather(String CityName) {
        return mCityWeathers.get(CityName);
    }
}
