package com.mudhales.haqdarshak.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

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
}
