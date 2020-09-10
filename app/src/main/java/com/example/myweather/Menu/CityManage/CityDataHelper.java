package com.example.myweather.Menu.CityManage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.myweather.info;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class CityDataHelper {
    private static final String PREF_NAME = "CITY_SET_PREF";
    private static final String KEY = "CITY_SET";

    public static ArrayList<City> getCityList(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        Set<String> city_set = sharedPreferences.getStringSet(KEY, new HashSet<String>());

        info.setCityset(city_set);
        ArrayList<City> city_list = new ArrayList<>();

        for (String name : city_set) {
            city_list.add(new City(name, false));
            Log.i("CITY", name);
        }

        Log.i("CITY", "LOAD SIZE " + city_list.size());
        return city_list;
    }

    public static void addCity(Context context, String name) {
        Log.i("CITY", "ADD " + name);

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        Set<String> city_set = sharedPreferences.getStringSet(KEY, new HashSet<String>());
        city_set = new HashSet<String>(city_set);
        city_set.add(name);

        ArrayList<City> city_list = new ArrayList<>();
        for (String str : city_set) {
            city_list.add(new City(str, false));
        }

        CityLab.get(null).setCities(city_list);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(KEY, city_set);
        editor.apply();
    }

    public static void removeCity(Context context, String name) {
        Log.i("CITY", "REMOVE " + name);

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        Set<String> city_set = sharedPreferences.getStringSet(KEY, new HashSet<String>());
        city_set = new HashSet<String>(city_set);
        city_set.remove(name);

        ArrayList<City> city_list = new ArrayList<>();
        for (String str : city_set) {
            city_list.add(new City(str, false));
        }

        CityLab.get(null).setCities(city_list);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(KEY, city_set);
        editor.apply();
    }
}
