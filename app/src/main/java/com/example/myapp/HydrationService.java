package com.example.myapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class HydrationService extends Service {

    private Handler handler = new Handler();
    private Runnable reminderTask;
    public static final String CHANNEL_ID = "hydration_channel";
    public static final String ACTION_START = "ACTION_START_HYDRATION";
    public static final String ACTION_STOP = "ACTION_STOP_HYDRATION";


    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();

        reminderTask = () -> {
            //Envoie une notification système
            sendHydrationNotification();
            //Diffuse un message interne
            Intent intent = new Intent("SANTE_EAU");
            intent.putExtra("msg", "Il est temps de boire de l'eau 💧");
            sendBroadcast(intent);
            handler.postDelayed(reminderTask, 20000);
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && intent.getAction() != null) {

            switch (intent.getAction()) {

                case ACTION_START:
                    handler.post(reminderTask);
                    break;

                case ACTION_STOP:
                    handler.removeCallbacks(reminderTask);
                    stopSelf();
                    break;
            }

        } else {
            // Lancement automatique (Application)
            handler.post(reminderTask);
        }

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        handler.removeCallbacks(reminderTask);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendHydrationNotification() {

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_water_drop)
                .setContentTitle("Hydratation")
                .setContentText("Il est temps de boire de l'eau 💧")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify((int) System.currentTimeMillis(), notification);
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(CHANNEL_ID, "Hydration Reminder",
                            NotificationManager.IMPORTANCE_HIGH);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
