package com.example.dadac.datatransimittebetweenprocess;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import modelofobserver.MyObserver;

/**
 * @ Create by dadac on 2018/12/14.
 * @Function:
 * @Return:
 */
public class BroadcastActivity extends AppCompatActivity {

    private TextView DC_TextViewReceiveFromActivityBundle;
    private TextView DC_TextViewReceiveFromActivityBroadcast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        DC_TextViewReceiveFromActivityBundle = (TextView) findViewById(R.id.DC_TextViewReceiveFromActivityBundle);
        DC_TextViewReceiveFromActivityBroadcast = (TextView) findViewById(R.id.DC_TextViewReceiveFromActivityBroadcast);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, 1, 1);
        Intent a = getIntent();
        String b = a.getStringExtra(IStatus.IStatus_Act2ActKey);
        Log.i("dachen", "接受来自Activity的数据" + ",b:" + b);
    }

    private Handler myhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x51:

                    break;

            }
        }
    };


}
