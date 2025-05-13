package com.example.findinglogs.model.model;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Weather {
    private WeatherInfo main;
    private String name;
    private List<WeatherDetail> weather = new ArrayList<>();

    private Sys sys; // classe que contém sunrise e sunset para eu por na atualização
    public Weather() {
    }

    public WeatherInfo getMain() {
        return main;
    }

    public class Sys {
        private long sunrise;
        private long sunset;

        public long getSunrise() { return sunrise; }
        public long getSunset() { return sunset; }
    }

    public Sys getSys() {
        return sys;
    }

    public void setMain(WeatherInfo main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WeatherDetail> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherDetail> weather) {
        this.weather = weather;
    }

    @NonNull
    @Override
    public String toString() {
        return "Weather{" +
                "main=" + main +
                ", name='" + name + '\'' +
                ", weather=" + weather +
                '}';
    }
}