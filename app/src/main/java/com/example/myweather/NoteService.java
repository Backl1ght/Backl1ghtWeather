package com.example.myweather;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;

import java.util.concurrent.TimeUnit;

public class NoteService extends IntentService {

    // TODO: 定时更新数据，并推送，现在的实现就是随便给个温度，应该是要临时通过请求数据，然后更新本地数据，然后推送

    private static final String TAG = "NoteService";
    private static final long POLL_INTERVAL_MS = TimeUnit.MINUTES.toMillis(10);

    public NoteService() {
        super(TAG);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, NoteService.class);
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = NoteService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);
        if (isOn) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), POLL_INTERVAL_MS, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }

    public static boolean isServiceAlarmOn(Context context) {
        Intent i = NoteService.newIntent(context);
        PendingIntent pi = PendingIntent
                .getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!isNetworkAvailableAndConnected()) {
            return;
        }
        Intent i = MainActivity.newIntent(this);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //高版本需要渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //只在Android O之上需要渠道，这里的第一个参数要和下面的channelId一样
            NotificationChannel notificationChannel = new NotificationChannel("1", "name", NotificationManager.IMPORTANCE_HIGH);
            //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，通知才能正常弹出
            manager.createNotificationChannel(notificationChannel);
        }
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
        Notification notification = new NotificationCompat.Builder(this, "1")
                .setTicker("天气通知")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("天气通知")
                .setContentText("现在温度" + 7557 + info.getCh())
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();
        manager.notify(1, notification);
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }
}
