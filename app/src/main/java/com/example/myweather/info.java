package com.example.myweather;

import android.database.sqlite.SQLiteDatabase;

import com.example.myweather.DataBase.DataBaseHelper;

import java.util.Set;

public class info {
    private static String lat = "0";

    private static String lon = "0";

    private static String city = "泉州";

    private static String ch = "℃";

    private static String flag = "是";

    private static String JSONStringNow;

    private static String JSONStringDaily;

    private static String JSONStringHourly;

    private static Set<String> cityset;

    public static Set<String> getCityset() {
        return cityset;
    }

    public static void setCityset(Set<String> cityset) {
        info.cityset = cityset;
    }

    public static String getJSONStringNow() {
        return JSONStringNow;
    }

    public static void setJSONStringNow(String JSONStringNow) {
        info.JSONStringNow = JSONStringNow;
    }

    public static String getJSONStringDaily() {
        return JSONStringDaily;
    }

    public static void setJSONStringDaily(String JSONStringDaily) {
        info.JSONStringDaily = JSONStringDaily;
    }

    public static String getJSONStringHourly() {
        return JSONStringHourly;
    }

    public static void setJSONStringHourly(String JSONStringHourly) {
        info.JSONStringHourly = JSONStringHourly;
    }

    public static void load() {
        //TODO: 如果数据库里有数据，就用数据库的数据初始化
        SQLiteDatabase mDataBase = new DataBaseHelper(null).getWritableDatabase();
    }

    public static void save() {
        //TODO: 保存数据到数据库
    }

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        info.city = city;
    }

    public static String getCh() {
        return ch;
    }

    public static void setCh(String ch) {
        info.ch = ch;
    }

    public static String getFlag() {
        return flag;
    }

    public static void setFlag(String flag) {
        info.flag = flag;
    }

    public static String getLat() {
        return lat;
    }

    public static void setLat(String lat) {
        info.lat = lat;
    }

    public static String getLon() {
        return lon;
    }

    public static void setLon(String lon) {
        info.lon = lon;
    }
}
