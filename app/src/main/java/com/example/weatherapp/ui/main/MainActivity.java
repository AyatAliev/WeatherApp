package com.example.weatherapp.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.data.entity.current.CurrentWeather;
import com.example.weatherapp.data.entity.forecast.ForecastEntity;
import com.example.weatherapp.data.internet.RetrofitBuilder;
import com.example.weatherapp.ui.base.BaseActivity;
import com.example.weatherapp.utils.DateUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.weatherapp.BuildConfig.API_KEY;

public class MainActivity extends BaseActivity {


    public static final String WEATHER = "weather";
    private final int REQUEST_CODE = 1001;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    String city;
    @BindView(R.id.image_sun)
    ImageView imageView;
    @BindView(R.id.text_cloudiness)
    TextView cloudiness;
    @BindView(R.id.text_cloudiness_value)
    TextView cloudiness_value;
    @BindView(R.id.text_data)
    TextView data_text;
    @BindView(R.id.text_humidity)
    TextView humidity;
    @BindView(R.id.text_little_cloud)
    TextView little_cloud;
    @BindView(R.id.text_humidity_value)
    TextView humidity_value;
    @BindView(R.id.text_max)
    TextView text_max;
    @BindView(R.id.text_max_gradus)
    TextView max_gradus;
    @BindView(R.id.text_min_gradus)
    TextView min_gradus;
    @BindView(R.id.text_month_and_year)
    TextView month_and_year;
    @BindView(R.id.text_min)
    TextView text_min;
    @BindView(R.id.text_now)
    TextView text_now;
    @BindView(R.id.text_now_gradus)
    TextView now_gradus;
    @BindView(R.id.text_sunset)
    TextView sunset;
    @BindView(R.id.text_sunset_value)
    TextView sunset_value;
    @BindView(R.id.text_wind)
    TextView wind;
    @BindView(R.id.text_wind_value)
    TextView wind_value;
    @BindView(R.id.text_today)
    TextView today;
    @BindView(R.id.text_pressure)
    TextView pressure;
    @BindView(R.id.text_pressure_value)
    TextView pressure_value;
    @BindView(R.id.text_sunrice)
    TextView sunrice;
    @BindView(R.id.text_sunrice_value)
    TextView sunricevalue;
    FusedLocationProviderClient fusedLocationProviderClient;
    String[] permissions = new String[2];
    private Spinner spinner;
    private CurrentWeather data;
    private RecyclerView recyclerView;
    private ForecastAdapter adapter;
    FloatingActionButton fabStart, fabStop;
    public static final String IS_SERVICE_ACTIVE = "isActivated";


    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }


    @Override
    protected int getViewLayout() {
        return R.layout.activity_main;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        initViews();
        initRecycler();
        clickFab();
        permissions();
        callPermissions();
        getCurrentCoordinate();
    }

    public void permissions() {
        permissions[0] = Manifest.permission.ACCESS_COARSE_LOCATION;
        permissions[1] = Manifest.permission.ACCESS_FINE_LOCATION;


        if (ContextCompat.checkSelfPermission(MainActivity.this, permissions[0]) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this, permissions[1]) == PackageManager.PERMISSION_GRANTED) {
            getStartLocation();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            for (int results : grantResults) {
                if (results == PackageManager.PERMISSION_GRANTED) {
                    getStartLocation();
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    void getStartLocation() {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
//                ...setText(String.valueOf(location.getLatitude()));
            }
        });
    }

    private void fetchCurrentWeather(double latitude, double longitude) {
        RetrofitBuilder.getService().coordCurrentWeather(latitude, longitude, "metric", API_KEY).
                enqueue(new Callback<CurrentWeather>() {
                    @Override
                    public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            data = response.body();
                            changeValues(data);
                            timeAndData();
                            glide(response);
                        } else {
                            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<CurrentWeather> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void fetchForecast(double latitude, double longitude) {
        RetrofitBuilder.getService().coordForCastWeather(latitude, longitude, "metric", API_KEY)
                .enqueue(new Callback<ForecastEntity>() {
                    @Override
                    public void onResponse(Call<ForecastEntity> call, Response<ForecastEntity> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            adapter.update(response.body().getForecastWeatherList());
                        }
                    }

                    @Override
                    public void onFailure(Call<ForecastEntity> call, Throwable t) {
                        Log.e("ololo", "onResponse: ");

                    }
                });
    }

    private void changeValues(CurrentWeather data) {
        city = data.getName();
        pressure_value.setText(String.valueOf(data.getMain().getPressure()));
        now_gradus.setText(String.valueOf(data.getMain().getTemp()));
        max_gradus.setText(String.valueOf(data.getMain().getTempMax()));
        min_gradus.setText(String.valueOf(data.getMain().getTempMin()));
        humidity_value.setText(String.valueOf(data.getMain().getHumidity()));
        wind_value.setText(String.valueOf(data.getWind().getSpeed()));
        cloudiness_value.setText(String.valueOf(data.getClouds().getAll()));
        little_cloud.setText(data.getWeather().get(0).getDescription());

    }

    private void timeAndData() {
        sunricevalue.setText(DateUtils.parceSunSet(data.getSys().getSunrise()));
        sunset_value.setText(DateUtils.parceSunSet(data.getSys().getSunset()));
        data_text.setText(DateUtils.parseData(data));
    }

    public void glide(Response<CurrentWeather> response) {
        Glide.with(MainActivity.this).load("http://openweathermap.org/img/wn/" + response.body().
                getWeather().get(0).getIcon() + "@2x.png").centerCrop().into(imageView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.update_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.update:
                getCurrentCoordinate();
                timeAndData();
                break;
            case R.id.map:
                startActivity(new Intent(MainActivity.this, MapActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecycler() {
        adapter = new ForecastAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        spinner = findViewById(R.id.spinner);
        fabStart = findViewById(R.id.fab_start);
        fabStop = findViewById(R.id.fab_stop);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


    }

    private void actionService(boolean isActivated) {
        Intent intent = new Intent(this, MyForegroundService.class);
        intent.putExtra(IS_SERVICE_ACTIVE, isActivated);
        startService(intent);
    }

    private void clickFab() {

        View.OnClickListener listener = v -> {
            switch (v.getId()) {
                case R.id.fab_start:
                    onClick(fabStart);
                    break;
                case R.id.fab_stop:
                    onClick(fabStop);

                    break;
            }
        };
        fabStart.setOnClickListener(listener);
        fabStop.setOnClickListener(listener);

    }

    @SuppressLint("RestrictedApi")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_start:
                actionService(true);
                fabStart.setVisibility(View.GONE);
                fabStop.setVisibility(View.VISIBLE);
                break;
            case R.id.fab_stop:
                fabStart.setVisibility(View.VISIBLE);
                fabStop.setVisibility(View.GONE);
                actionService(false);
                break;

        }
    }

    private void getCurrentCoordinate() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PermissionChecker.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PermissionChecker.PERMISSION_GRANTED) {
            FusedLocationProviderClient fusedLocationProvider = new FusedLocationProviderClient(this);
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//            locationRequest.setFastestInterval(2000);
//            locationRequest.setInterval(4000);
            fusedLocationProvider.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {

                    super.onLocationResult(locationResult);
                    double latitude = locationResult.getLastLocation().getLatitude();
                    double longitude = locationResult.getLastLocation().getLongitude();
                    fetchCurrentWeather(latitude, longitude);
                    fetchForecast(latitude, longitude);
                }
            }, getMainLooper());

        }
    }

        public void callPermissions () {
            Permissions.check(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, "Нужен доступ к местоположению",
                    new Permissions.Options().setSettingsDialogTitle("Внимание!!!").setRationaleDialogTitle("Доступ к местоположению"),
                    new PermissionHandler() {
                        @Override
                        public void onGranted() {
                            getCurrentCoordinate();
                        }

                        @Override
                        public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                            super.onDenied(context, deniedPermissions);
                            callPermissions();
                        }
                    });
        }
    }
