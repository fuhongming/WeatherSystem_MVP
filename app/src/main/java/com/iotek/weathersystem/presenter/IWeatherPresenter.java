package com.iotek.weathersystem.presenter;

import android.content.Context;
import android.view.View;


/**
 * Created by fhm on 2016/5/25.
 */
public interface IWeatherPresenter {

    void onResume();

    void onDestroy();

    void onCheckedChanged(Context context, int checkedId);

    void switchCity(String city);

}
