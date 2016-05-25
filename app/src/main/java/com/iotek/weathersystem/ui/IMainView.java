package com.iotek.weathersystem.ui;

import java.util.List;

/**
 * Created by fhm on 2016/5/25.
 */
public interface IMainView {
    void showProgress();

    void hideProgress();

    void setItems(List<String> items);

    void showMessage(String message);
}
