package com.example.myweather.Menu.CityManage;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class CityLab {
    private static CityLab sCityLab;
    private static ArrayList<City> mCities = new ArrayList<>();

    private CityLab(Context context) {
        mCities = CityDataHelper.getCityList(context);
    }

    public static CityLab get(Context context) {
        if (sCityLab == null) {
            sCityLab = new CityLab(context);
        }
        return sCityLab;
    }

    public static ArrayList<City> getCities() {
        return mCities;
    }

    public static void setCities(ArrayList<City> tmp) {
        mCities = tmp;
    }
}
