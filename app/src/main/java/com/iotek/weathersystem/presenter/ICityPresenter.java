package com.iotek.weathersystem.presenter;

import android.content.Context;


/**
 * Created by fhm on 2016/5/25.
 */
public interface ICityPresenter {

    void onResume();

    void onDestroy();

    void onCheckedChanged(Context context, int checkedId);

    void switchCity(String city);

}
