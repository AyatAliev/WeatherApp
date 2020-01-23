package com.example.weatherapp.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.weatherapp.R;
import com.example.weatherapp.data.entity.forecast.ForecastEntity;
import com.example.weatherapp.data.internet.RetrofitBuilder;
import com.example.weatherapp.ui.base.BaseActivity;
import com.example.weatherapp.ui.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.weatherapp.BuildConfig.API_KEY;
import static com.example.weatherapp.ui.main.MainActivity.WEATHER;

public class SplashActivity extends BaseActivity {


    @Override
    protected int getViewLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    public  void startForSplash(ForecastEntity forecastEntity) {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra(WEATHER, forecastEntity);
////        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//
//    }


//    }
//    private void initLaunch() {
//        if (PreferenceHelper.getIsNotFirstLaunch()) {
//            MainActivity.start(this);
//        } else {
//            OnBoardActivity.start(this);
//            PreferenceHelper.setIsFirstLaunch();
//        }
//
//        finish();
//    }
    }
