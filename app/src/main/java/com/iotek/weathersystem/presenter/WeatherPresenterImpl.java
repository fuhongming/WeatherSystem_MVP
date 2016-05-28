package com.iotek.weathersystem.presenter;

import android.content.Context;

import com.iotek.weathersystem.model.Result;
import com.iotek.weathersystem.net.IWeatherReq;
import com.iotek.weathersystem.net.WeatherReqImpl;
import com.iotek.weathersystem.ui.IWeatherView;

import java.util.List;

/**
 * Created by fhm on 2016/5/25.
 */
public class WeatherPresenterImpl implements IWeatherPresenter, IWeatherReq.OnFinishedListener {
    private IWeatherView mainView;
    private IWeatherReq reqData;

    public WeatherPresenterImpl(IWeatherView mainView) {
        this.mainView = mainView;
        reqData = new WeatherReqImpl();

    }

    @Override
    public void onResume() {
        if (mainView != null) {
            mainView.showProgress();
        }
        reqData.reqData(this, "苏州");
    }

    @Override
    public void onCheckedChanged(Context context, int checkedId) {
        if (mainView != null) {
//            mainView.showMessage(String.format("%d onCheckedChanged", checkedId + 1));
        }
    }

    @Override
    public void switchCity(String city) {
        reqData.reqData(this, city);
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onFinished(List<Result> result) {
        if (mainView != null) {
            mainView.showData(result);
            mainView.hideProgress();
        }
    }
}
