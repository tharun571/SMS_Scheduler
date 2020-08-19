package com.example.sms_scheduler;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.annotation.Nullable;

import java.security.Provider;

public class HelloService extends Service {

    private static final String TAG = "HelloService";

    private boolean isRunning  = false;
    Handler handler = new Handler();


    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");

        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Service onStartCommand");

        //Creating new thread for my service
        //Always write your long running tasks in a separate thread, to avoid ANR
        new Thread(new Runnable() {
            @Override
            public void run() {


                //Your logic that service will perform will be placed here
                //In this example we are just looping and waits for 1000 milliseconds in each loop.

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                        //Get the SmsManager instance and call the sendTextMessage method to send message
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(Info.no, null, Info.mes, pi, null);



                    }
                }, Integer.parseInt(Info.time) * 1000);


                //Stop service once it finishes its task
                stopSelf();
            }
        }).start();

        return Service.START_STICKY;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {

        isRunning = false;

        Log.i(TAG, "Service onDestroy");
    }
}
