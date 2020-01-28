package com.example.weatherapp.data;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.weatherapp.R;
import com.example.weatherapp.ui.main.MainActivity;
import com.example.weatherapp.ui.main.MapActivity;

public class NotificationHelper {
    public static final String NOTIFICATION_CHANEL = "chanel_notification";
    private static final String CHANNEL_ID = "CHANNEL_ID";


    public static Notification createNotification(Context context, String title, String body) {
        createNotificationChannel(context);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,1,intent,0);
        Intent intent1 = new Intent(context, MapActivity.class);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(context,2,intent1,0);
        return new NotificationCompat.Builder(context, NOTIFICATION_CHANEL)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.ic_place,"Second",pendingIntent1)
                .build();

    }

    public static void showNotification(Context context, Notification notification){
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(1,notification );
    }


    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel name";
            String description = "channel desc";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
