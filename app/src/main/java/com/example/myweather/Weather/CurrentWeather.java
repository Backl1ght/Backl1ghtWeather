package com.example.myweather.Weather;

import com.example.myweather.WeatherFragment.CurrentWeatherFragment;
import com.example.myweather.info;

import org.json.JSONArray;
import org.json.JSONObject;

public class CurrentWeather {
    private String json;
    private String code;
    private String txt;
    private String tmp;
    private String time;
    private String city;

    public static CurrentWeather parseJSON(String JSON) {
        CurrentWeather cw = new CurrentWeather();
        cw.json = JSON;

        try {
            JSONObject heWeatherJson = new JSONObject(JSON);
            JSONArray HeWeatherJson = heWeatherJson.getJSONArray("HeWeather6");
            JSONObject allJson = HeWeatherJson.getJSONObject(0);
            JSONObject nowJson = allJson.getJSONObject("now");
            JSONObject basicJson = allJson.getJSONObject("basic");
            JSONObject updateJson = allJson.getJSONObject("update");

            String t = nowJson.getString("tmp");
            cw.setTmp(t);

            cw.setTmp(t);
            cw.setTxt(nowJson.getString("cond_txt"));
            cw.setCity(basicJson.getString("location"));
            cw.setCode(nowJson.getString("cond_code"));
            cw.setTime(updateJson.getString("loc"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cw;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getJson() {
        return this.json;
    }

    public void setJson(String JSON) {
        this.json = JSON;
    }
}
