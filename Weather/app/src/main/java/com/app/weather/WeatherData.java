package com.app.weather;

public class WeatherData {
    private String date;
    private String temp;

    public WeatherData(String date, String temp) {
        this.date = date;
        this.temp = temp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}




