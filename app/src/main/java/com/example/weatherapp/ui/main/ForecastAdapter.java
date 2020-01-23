package com.example.weatherapp.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.data.entity.current.CurrentWeather;

import java.util.ArrayList;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastViewHolder> {

private ArrayList<CurrentWeather> currentWeathers = new ArrayList<>();
    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);
        return new ForecastViewHolder(view);
    }

    public void update(ArrayList<CurrentWeather> forecastWeatherList){
        this.currentWeathers = forecastWeatherList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        holder.bind(currentWeathers.get(position).getMain().getTempMin(),
                currentWeathers.get(position).getMain().getTempMax()
                ,currentWeathers.get(position).getDateTimeForCast()
                ,currentWeathers.get(0).getWeather().get(0).getIcon(),position);
    }

    @Override
    public int getItemCount() {
        return  currentWeathers.size();
    }
}
