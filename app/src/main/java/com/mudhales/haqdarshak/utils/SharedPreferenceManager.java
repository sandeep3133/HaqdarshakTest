package com.mudhales.haqdarshak.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mudhales.haqdarshak.MainActivity;
import com.mudhales.haqdarshak.data.UserData;

public class SharedPreferenceManager {
    private static SharedPreferenceManager INSTANCE;
    private Context mContext;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunchHaqdarshak";

    public static SharedPreferenceManager with(Context mContext) {
        if (INSTANCE == null) {
            INSTANCE = new SharedPreferenceManager(mContext);
        }
        return INSTANCE;
    }

    /**
     * Private constructor for instantiating the singleton.
     */
    private SharedPreferenceManager(Context mContext) {
        //It is important to store the application context
        //in order to avoid memory leaks.
        this.mContext = mContext.getApplicationContext();
        this.sharedPreferences = mContext.getSharedPreferences(Constant.Common.SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(Constant.SharedPreferences.IS_USER_LOGGED_IN, false);
    }

    public void updateLoggedInUser(UserData user) {
        if (user == null) {
            editor.remove(Constant.SharedPreferences.LOGGED_IN_USER);
        } else {
            editor.putString(Constant.SharedPreferences.LOGGED_IN_USER, new Gson().toJson(user));
        }
        editor.commit();
    }


    public UserData getLoggedInUser() {
        String json = sharedPreferences.getString(Constant.SharedPreferences.LOGGED_IN_USER, null);
        if (json == null) {
            return null;
        }
        return new Gson().fromJson(json, new TypeToken<UserData>() {
        }.getType());
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setUserLoggedIn(boolean isUserLoggedIn) {
        editor.putBoolean(Constant.SharedPreferences.IS_USER_LOGGED_IN, isUserLoggedIn);
        editor.commit();
    }

    public void logout() {
        editor.putBoolean(Constant.SharedPreferences.IS_USER_LOGGED_IN, false);
        editor.remove(Constant.SharedPreferences.LOGGED_IN_USER);
        editor.commit();
        mContext.startActivity(new Intent(mContext, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    public long loginCount() {
        int json = sharedPreferences.getInt(Constant.SharedPreferences.INVALID_LOGIN_COUNT, 0);
        return json;
    }

    public void setLoginCount(boolean value) {
        if (value) {
            int count = sharedPreferences.getInt(Constant.SharedPreferences.INVALID_LOGIN_COUNT, 0);
            count = count + 1;
            editor.putInt(Constant.SharedPreferences.INVALID_LOGIN_COUNT, count);
            if (count>2) setLoginBlockTime();
        } else
            editor.putInt(Constant.SharedPreferences.INVALID_LOGIN_COUNT, 0);
        editor.commit();
    }
    public long getLoginBlockTime() {
        return sharedPreferences.getLong(Constant.SharedPreferences.IS_BLOCKED_TIME, 0);
    }
    public void setLoginBlockTime() {
        editor.putLong(Constant.SharedPreferences.IS_BLOCKED_TIME, System.currentTimeMillis()+300000);
        editor.commit();
    }

}
