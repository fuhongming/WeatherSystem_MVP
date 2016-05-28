package com.iotek.weathersystem.net;

import com.iotek.weathersystem.model.Result;

import java.util.List;

/**
 * Created by fhm on 2016/5/28.
 */
public interface ICityReq {
    interface OnFinishedListener {
        void onFinished(List<Result> result);
    }

    void reqData(OnFinishedListener listener, String city);
}
