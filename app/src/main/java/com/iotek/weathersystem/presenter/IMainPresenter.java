package com.iotek.weathersystem.presenter;

import android.view.View;

/**
 * Created by fhm on 2016/5/25.
 */
public interface IMainPresenter {

    void onResume();

    void onDestroy();

    void onCheckedChanged(int checkedId);

    void switchCity(String city);

}
