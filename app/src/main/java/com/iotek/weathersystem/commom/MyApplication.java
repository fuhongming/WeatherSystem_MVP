package com.iotek.weathersystem.commom;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;
import com.iotek.weathersystem.service.LocationService;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by wyouflf on 15/10/28.
 */
public class MyApplication extends Application {
    public LocationService locationService;
    public Vibrator mVibrator;


    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能

        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());

    }
}
