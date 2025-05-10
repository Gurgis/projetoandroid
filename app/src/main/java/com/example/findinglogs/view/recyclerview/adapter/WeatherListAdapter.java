package com.example.findinglogs.view.recyclerview.adapter;



import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findinglogs.R;
import com.example.findinglogs.model.model.Weather;
import com.example.findinglogs.model.util.Logger;
import com.example.findinglogs.model.util.Utils;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.ViewHolder> {

    private final Context context;
    private final List<Weather> weathers;

    public WeatherListAdapter(Context context, List<Weather> weathers) {
        this.context = context;
        this.weathers = new ArrayList<>(weathers);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialCardView cardView;
        private final TextView name;
        private final TextView temp_current;
        private final TextView temp_max;
        private final TextView temp_min;
        private final TextView pressure;
        private final TextView humidity;
        private final ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            name = itemView.findViewById(R.id.tv_weather_name);
            temp_current = itemView.findViewById(R.id.temp_current);
            temp_max = itemView.findViewById(R.id.temp_max);
            temp_min = itemView.findViewById(R.id.temp_min);
            pressure = itemView.findViewById(R.id.pressure);
            humidity = itemView.findViewById(R.id.humidity);
            imageView = itemView.findViewById(R.id.img_view_item);
        }

        public void holdWeather(Weather weather, Context context) {
            switch (weather.getWeather().get(0).getIcon()){
                case "02d":
                    cardView.setCardBackgroundColor(context.getColor(R.color.weather_few_clouds));
                    break;
                case "02n":
                    cardView.setCardBackgroundColor(context.getColor(R.color.weather_few_clouds_dark));
                    break;
                case "03d":
                    cardView.setCardBackgroundColor(context.getColor(R.color.weather_cloudy));
                    break;
                case "03n":
                    cardView.setCardBackgroundColor(context.getColor(R.color.weather_cloudy_dark));
                    break;
                case "04d":
                    cardView.setCardBackgroundColor(context.getColor(R.color.weather_scattered_clouds));
                    break;
                case "04n":
                    cardView.setCardBackgroundColor(context.getColor(R.color.weather_scattered_clouds_dark));
                    break;
                case "09d":
                    cardView.setCardBackgroundColor(context.getColor(R.color.weather_fog));
                    break;
                case "09n":
                    cardView.setCardBackgroundColor(context.getColor(R.color.weather_fog_dark));
                    break;
                default:
                    cardView.setCardBackgroundColor(context.getColor(R.color.weather_few_clouds));
            }

            name.setText(weather.getName());
            String temp_current_value = "Temp. atual: " +
                    Utils.getCelsiusTemperatureFromKevin(weather.getMain().getTemp());
            temp_current.setText(temp_current_value);
            String temp_max_value = "Temp. máx: " +
                    Utils.getCelsiusTemperatureFromKevin(weather.getMain().getTemp_max());
            temp_max.setText(temp_max_value);
            String temp_min_value = "Temp. mín: " +
                    Utils.getCelsiusTemperatureFromKevin(weather.getMain().getTemp_min());
            temp_min.setText(temp_min_value);
            String pressure_value = "Pressão: " + weather.getMain().getPressure() + "hPa";
            pressure.setText(pressure_value);
            String humidity_value = "Umidade: " + weather.getMain().getHumidity() + "%";
            humidity.setText(humidity_value);
            Drawable resDrawIcon = Utils.getDrawable(weather.getWeather().get(0).getIcon(), context);
            imageView.setImageDrawable(resDrawIcon);
            //o problema deve estar aqui
            //o problema estava com o nome da imagem
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weather_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Weather weather = weathers.get(position);
        holder.holdWeather(weather, context);
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    public void updateWeathers(List<Weather> weathersValues) {
        weathers.clear();
        weathers.addAll(weathersValues);
        notifyDataSetChanged();
    }
}