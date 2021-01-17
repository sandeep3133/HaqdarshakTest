package com.mudhales.haqdarshak.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Utils {
    // To check internet connection by SM
    public static boolean isInternetConnectionAvailable(Context mContext) {
        if (null == mContext) {
            return true;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        NetworkInfo netInfo = null;
        if (null != connectivityManager) {
            netInfo = connectivityManager.getActiveNetworkInfo();
        }
        return (null != netInfo && netInfo.isAvailable() && netInfo.isConnected());
    }

    public static String checkNotNull(String strValue) {
        return TextUtils.isEmpty(strValue)?"N/A":strValue;
    }
    public static String convertDateFormat(String strDate,String strInputDateFormat, String strOutputDateFormat) {
        String formattedDate;
        try {
            SimpleDateFormat sdfInput = new SimpleDateFormat(strInputDateFormat, Locale.US);
            SimpleDateFormat sdfOutput = new SimpleDateFormat(strOutputDateFormat, Locale.US);
            formattedDate = sdfOutput.format(sdfInput.parse(strDate));
        } catch (ParseException e) {
            e.printStackTrace();
            formattedDate = strDate;
        }
        Log.e("==formattedDate==",formattedDate+"===strInputDateFormat==="+strInputDateFormat+"===strOutputDateFormat==="+strOutputDateFormat);
        return formattedDate;
    }
    public static String convertTimeFormat(final long strDate) {
        long seconds = (strDate / 1000) % 60;
        long minutes = (strDate / (1000 * 60)) % 60;

        StringBuilder b = new StringBuilder();
        b.append(minutes == 0 ? "00" : minutes < 10 ? String.valueOf("0" + minutes) :
                String.valueOf(minutes));
        b.append(":");
        b.append(seconds == 0 ? "00" : seconds < 10 ? String.valueOf("0" + seconds) :
                String.valueOf(seconds));
        return b.toString();
    }
}
