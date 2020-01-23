package com.example.weatherapp.ui.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.data.entity.current.CurrentWeather;
import com.example.weatherapp.utils.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;

public class ForecastViewHolder extends RecyclerView.ViewHolder {
    TextView tvMinTemp, tvMaxTemp, date;
    ImageView imgForecast;


    public ForecastViewHolder(@NonNull View itemView) {
        super(itemView);
        tvMinTemp = itemView.findViewById(R.id.tvMinTemp);
        tvMaxTemp = itemView.findViewById(R.id.tvMaxTemp);
        date = itemView.findViewById(R.id.text__forecast_date);
        imgForecast = itemView.findViewById(R.id.img_forecast);
    }

    public void bind(Double tempMin, Double tempMax, String timeForCast, String dateTimeForCast, int position) {
        tvMaxTemp.setText(tempMax.toString());
        tvMinTemp.setText(tempMin.toString());

        try {
            date.setText(DateUtils.forCastDate(timeForCast));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Glide.with(itemView).load("http://openweathermap.org/img/wn/" + dateTimeForCast + "@2x.png").into(imgForecast);
    }
}
