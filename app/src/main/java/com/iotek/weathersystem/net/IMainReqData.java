package com.iotek.weathersystem.net;

import com.iotek.weathersystem.model.Result;

import java.util.List;

/**
 * Created by fhm on 2016/5/25.
 */
public interface IMainReqData {
    interface OnFinishedListener {
        void onFinished(Result result);
    }

    void reqData(OnFinishedListener listener, String city);
}
