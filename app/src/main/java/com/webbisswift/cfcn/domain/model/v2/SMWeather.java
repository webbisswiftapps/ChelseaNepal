package com.webbisswift.cfcn.domain.model.v2;

import java.util.HashMap;

/**
 * Created by biswas on 29/01/2018.
 */

public class SMWeather {

    public SMWeather() {
    }

    String clouds, code, humidity, icon, type;
    Wind wind;
    Temperature temperature;

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public String getConditionDesc(){
        return conditions.get(code);
    }

    public String getTemperatureDesc(){
        return temperature.getTemp()+"° "+temperature.getUnit();
    }

    public static class Wind{

        public Wind() {
        }

        int degree;
        String speed;

        public int getDegree() {
            return degree;
        }

        public void setDegree(int degree) {
            this.degree = degree;
        }

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }
    }

    public static class Temperature{

        public Temperature() {
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        String unit;
        double temp;
    }

    private static final HashMap<String, String> conditions = new HashMap<>();

    static {
        conditions.put("clear-sky", "Clear Sky");
        conditions.put("few-clouds","Few Clouds");
        conditions.put("scattered-clouds", "Scattered Clouds");
        conditions.put("broken-clouds", "Broken Clouds");
        conditions.put("shower-rain", "Rain Showers");
        conditions.put("rain", "Rain");
        conditions.put("thunderstorm", "Thunderstorm");
        conditions.put("snow","Snow");
        conditions.put("mist","Mist");
    }
}