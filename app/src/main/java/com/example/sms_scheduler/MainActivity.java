package com.example.sms_scheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "QWERT";
    EditText message1, number1, time1;
    String message, number, time2;
    Button done;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }

        message1 = (EditText) findViewById(R.id.editTextTextMultiLine);
        number1 = (EditText) findViewById(R.id.editTextPhone);
        time1 = (EditText) findViewById(R.id.editTextTime);
        done = (Button) findViewById(R.id.button1);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                time2 = time1.getText().toString();
                message = message1.getText().toString();
                number = number1.getText().toString();

                Info.mes=message;
                Info.no=number;
                Info.time=time2;

                if(time2.matches("")|number.matches("")|message.matches("")){
                    Toast.makeText(getApplicationContext(),
                            "Please fill all fields", Toast.LENGTH_LONG).show();
                }


                else {

                    Intent intent = new Intent(MainActivity.this, HelloService.class);
                    startService(intent);
                }

            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            time2 = time1.getText().toString();
                            message = message1.getText().toString();
                            number = number1.getText().toString();

                            Info.mes=message;
                            Info.no=number;
                            Info.time=time2;
                            int t=Integer.parseInt(time2);
                            Log.w(TAG,"LOP "+t);



                            if(time2.matches("")|number.matches("")|message.matches("")){
                                Toast.makeText(getApplicationContext(),
                                        "Please fill all fields", Toast.LENGTH_LONG).show();
                            }


                            Intent intent = new Intent(MainActivity.this, HelloService.class);
                            startService(intent);
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }
}