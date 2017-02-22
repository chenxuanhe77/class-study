package com.example.chenxuanhe.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.chenxuanhe.myapplication.utils.MarkSerializable;

import java.util.ArrayList;

public class MyMarkService extends Service {


    public MyMarkService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        ArrayList<MarkSerializable> list = (ArrayList<MarkSerializable>) intent.
                getSerializableExtra("Mark_failed");
        for (int i = 0; i < list.size(); i++) {
            MarkSerializable markSerializable = list.get(i);
            String course = markSerializable.getCourse();
            Log.d("Test", "" + course);

            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("目前还未及格，请加油复习呦")
                    .setContentText(course.trim())
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setAutoCancel(true)
                    .build();
            manager.notify(i, notification);

        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
        Log.d("Test", "服务已经停止");
    }
}
