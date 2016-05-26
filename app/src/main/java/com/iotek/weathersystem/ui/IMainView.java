package com.iotek.weathersystem.ui;

import com.iotek.weathersystem.model.Result;

import java.util.List;

/**
 * Created by fhm on 2016/5/25.
 */
public interface IMainView {
    void showProgress();

    void hideProgress();

    void showData(Result result);

    void showMessage(String message);
}
