package com.iotek.weathersystem.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.iotek.weathersystem.commom.Canstants;

/**
 * Created by fhm on 2016/5/25.
 */
public class Tools {
    public static void log(String msg) {
        Log.e(Canstants.TAG, msg);
    }

    public static boolean isNetConn(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info == null || !info.isAvailable()) {
            return false;
        } else {
            return true;
        }
    }
}
