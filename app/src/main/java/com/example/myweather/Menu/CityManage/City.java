package com.example.myweather.Menu.CityManage;

public class City {
    private String CityName;
    private boolean isCurrent;

    City() {  }

    City(String CityName, boolean isCurrent) {
        this.CityName = CityName;
        this.isCurrent = isCurrent;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }
}
