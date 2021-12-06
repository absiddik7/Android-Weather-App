package com.app.weather;

public class WeatherData {
    private String date;
    private String temp;
    private String img;

    public WeatherData(String date, String temp, String img) {
        this.date = date;
        this.temp = temp;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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




