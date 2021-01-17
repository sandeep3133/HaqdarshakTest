package com.mudhales.haqdarshak.utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.mudhales.haqdarshak.interfaces.CountStatusInterface;

public class MyCountDownTimer {
    private long millisInFuture;
    private long countDownInterval;
    private boolean status;
    private Context mContext;
    private CountStatusInterface statusInterface;
    public MyCountDownTimer(Context mContext,CountStatusInterface statusInterface, long pMillisInFuture, long pCountDownInterval) {
        this.millisInFuture = pMillisInFuture;
        this.countDownInterval = pCountDownInterval;
        status = false;
        this.mContext = mContext;
        this.statusInterface = statusInterface;
        Initialize();
    }

    public void Stop() {
        status = false;
    }

    public long getCurrentTime() {
        return millisInFuture;
    }

    public void Start() {
        status = true;
    }
    public void Initialize()
    {
        final Handler handler = new Handler();
        Log.v("status", "starting");
        final Runnable counter = new Runnable(){

            public void run(){
                long sec = millisInFuture/1000;
                if(status) {
                    if(millisInFuture <= 0) {
                       // Log.v("status", "done");
                        Toast.makeText(mContext, "Done", Toast.LENGTH_SHORT).show();
                        SharedPreferenceManager.with(mContext).setLoginCount(false);
                        statusInterface.onDone();
                    } else {
                       // Log.v("status", Long.toString(sec) + " seconds remain");
                        Toast.makeText(mContext, Long.toString(sec) + " seconds remain", Toast.LENGTH_SHORT).show();
                        millisInFuture -= countDownInterval;
                        handler.postDelayed(this, countDownInterval);
                    }
                } else {
                   // Log.v("status", Long.toString(sec) + " seconds remain and timer has stopped!");
                    handler.postDelayed(this, countDownInterval);
                }
            }
        };

        handler.postDelayed(counter, countDownInterval);
    }
}