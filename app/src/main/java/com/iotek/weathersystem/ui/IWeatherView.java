package com.iotek.weathersystem.ui;


import com.iotek.weathersystem.model.Result;

import java.util.List;


/**
 * Created by fhm on 2016/5/25.
 */
public interface IWeatherView {
    void showProgress();

    void hideProgress();


    void showMessage(String message);

    void showData(List<Result> result);

}
