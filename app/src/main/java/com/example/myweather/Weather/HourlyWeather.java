package com.example.myweather.Weather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HourlyWeather {
    private String time;       //预报时间
    private String tmp;        //温度
    private String code;       //天气状况代码

    public static ArrayList<HourlyWeather> parseJSONList(String JSON) {
        ArrayList<HourlyWeather> list = new ArrayList<HourlyWeather>();
        try {
            JSONObject heWeatherJson = new JSONObject(JSON);
            JSONArray HeWeatherJson = heWeatherJson.getJSONArray("HeWeather6");
            JSONObject allJson = HeWeatherJson.getJSONObject(0);
            JSONArray hourlyJsonArray = allJson.getJSONArray("hourly");
            for (int i = 0; i < 8; i++) {
                JSONObject hourJson = hourlyJsonArray.getJSONObject(i);
                HourlyWeather hw = new HourlyWeather();
                String hour = hourJson.getString("time");
                String[] hours = hour.split(" ");
                hw.setTime(hours[1]);
                hw.setCode(hourJson.getString("cond_code"));
                String t = hourJson.getString("tmp");
                hw.setTmp(t);
                list.add(hw);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
