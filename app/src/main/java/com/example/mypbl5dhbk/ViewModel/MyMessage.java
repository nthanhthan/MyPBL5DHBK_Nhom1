package com.example.mypbl5dhbk.ViewModel;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

public class MyMessage extends Application {
    public static final String  channelId="notification_id";

    @Override
    public void onCreate() {
        super.onCreate();
        createChanelNotification();
    }

    private void createChanelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("DEBUG","DA VO");
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

    }
}
}
