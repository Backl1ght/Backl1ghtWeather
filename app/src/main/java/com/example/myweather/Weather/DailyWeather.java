package com.example.myweather.Weather;

import android.icu.util.Calendar;
import android.icu.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DailyWeather {
    private String location;    // 地点
    private String date;        // 预报日期
    private String whatDay;     // 周几
    private String tmp_max;     // 最高温度
    private String tmp_min;     // 最低温度
    private String code_d;      // 白天天气代码
    private String code_n;      // 夜间天气代码
    private String txt_d;       // 白天天气描述
    private String txt_n;       // 夜间天气描述
    private String wind_dir;    // 风向
    private String wind_sc;     // 风力
    private String hum;         // 湿度
    private String pres;        // 压强

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWhatDay() {
        return whatDay;
    }

    public void setWhatDay(String whatDay) {
        this.whatDay = whatDay;
    }

    public String getTmp_max() {
        return tmp_max;
    }

    public void setTmp_max(String tmp_max) {
        this.tmp_max = tmp_max;
    }

    public String getTmp_min() {
        return tmp_min;
    }

    public void setTmp_min(String tmp_min) {
        this.tmp_min = tmp_min;
    }

    public String getCode_d() {
        return code_d;
    }

    public void setCode_d(String code_d) {
        this.code_d = code_d;
    }

    public String getCode_n() {
        return code_n;
    }

    public void setCode_n(String code_n) {
        this.code_n = code_n;
    }

    public String getTxt_d() {
        return txt_d;
    }

    public void setTxt_d(String txt_d) {
        this.txt_d = txt_d;
    }

    public String getTxt_n() {
        return txt_n;
    }

    public void setTxt_n(String txt_n) {
        this.txt_n = txt_n;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public String getWind_sc() {
        return wind_sc;
    }

    public void setWind_sc(String wind_sc) {
        this.wind_sc = wind_sc;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static ArrayList<DailyWeather> parseJSONList(String JSON) {
        ArrayList<DailyWeather> list = new ArrayList<DailyWeather>();

        try {
            Calendar c = Calendar.getInstance();
            c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            int day = c.get(Calendar.DAY_OF_WEEK);
            JSONObject heWeatherJson = new JSONObject(JSON);
            JSONArray HeWeatherJson = heWeatherJson.getJSONArray("HeWeather6");
            JSONObject allJson = HeWeatherJson.getJSONObject(0);
            JSONArray dailyJsonArray = allJson.getJSONArray("daily_forecast");
            JSONObject basicJson = allJson.getJSONObject("basic");

            for (int i = 0; i < dailyJsonArray.length(); i++) {
                int newDay = (day + i + 6) % 7;
                JSONObject dayJson = dailyJsonArray.getJSONObject(i);
                DailyWeather dailyWeather = new DailyWeather();

                if (i == 0) {
                    dailyWeather.setWhatDay("今天");
                } else if (i == 1) {
                    dailyWeather.setWhatDay("明天");
                } else {
                    switch (newDay) {
                        case 1:
                            dailyWeather.setWhatDay("星期一");
                            break;
                        case 2:
                            dailyWeather.setWhatDay("星期二");
                            break;
                        case 3:
                            dailyWeather.setWhatDay("星期三");
                            break;
                        case 4:
                            dailyWeather.setWhatDay("星期四");
                            break;
                        case 5:
                            dailyWeather.setWhatDay("星期五");
                            break;
                        case 6:
                            dailyWeather.setWhatDay("星期六");
                            break;
                        case 0:
                            dailyWeather.setWhatDay("星期日");
                            break;
                    }
                }
                dailyWeather.setLocation(basicJson.getString("location"));

                String t = dayJson.getString("tmp_max");
                dailyWeather.setTmp_max(t);

                t = dayJson.getString("tmp_min");
                dailyWeather.setTmp_min(t);

                dailyWeather.setTxt_d(dayJson.getString("cond_txt_d"));
                dailyWeather.setCode_d(dayJson.getString("cond_code_d"));
                dailyWeather.setWind_dir(dayJson.getString("wind_dir"));
                dailyWeather.setWind_sc(dayJson.getString("wind_sc"));
                dailyWeather.setPres(dayJson.getString("pres"));
                dailyWeather.setHum(dayJson.getString("hum"));
                dailyWeather.setDate(dayJson.getString("date"));

                list.add(dailyWeather);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
