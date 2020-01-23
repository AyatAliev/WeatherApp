package com.example.weatherapp.ui.main;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.weatherapp.data.NotificationHelper;
import static com.example.weatherapp.ui.main.MainActivity.IS_SERVICE_ACTIVE;


public class MyForegroundService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getBooleanExtra(IS_SERVICE_ACTIVE, false)) {
            startForeground(1, NotificationHelper.getNotification(getApplicationContext()));
        } else {
            stopSelf();
        }

        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();

    }

    private void coord() {

    }
}
