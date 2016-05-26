package com.iotek.weathersystem.model;


import com.iotek.weathersystem.utils.Tools;

/**
 * Created by fhm on 2016/5/25.
 */
public class Sk {
    private String temp;

    private String wind_direction;

    private String wind_strength;

    private String humidity;

    private String time;

    public void setTemp(String temp){
        this.temp = temp;
    }
    public String getTemp(){
        return this.temp;
    }
    public void setWind_direction(String wind_direction){
        this.wind_direction = wind_direction;
    }
    public String getWind_direction(){
        return this.wind_direction;
    }
    public void setWind_strength(String wind_strength){
        this.wind_strength = wind_strength;
    }
    public String getWind_strength(){
        return this.wind_strength;
    }
    public void setHumidity(String humidity){
        this.humidity = humidity;
    }
    public String getHumidity(){
        return this.humidity;
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getTime(){
        return this.time;
    }
}
