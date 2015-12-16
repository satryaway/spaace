package com.jixstreet.spaace.fragment;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.jixstreet.spaace.utils.CommonConstants;

/**
 * Created by satryaway on 12/15/2015.
 * Singleton for application
 */
public class SpaaceApplication extends Application {
    private static SpaaceApplication instance;
    private SharedPreferences preferences;
    private String token;

    public synchronized static SpaaceApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        preferences = getSharedPreferences(CommonConstants.SPAACE, Context.MODE_PRIVATE);
    }

    public SharedPreferences getSharedPreferences() {
        return preferences;
    }

    public String getToken() {
        return preferences.getString(CommonConstants.ACCESS_TOKEN, "");
    }

    public void setToken(String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CommonConstants.ACCESS_TOKEN, token);
        editor.apply();
    }
}
