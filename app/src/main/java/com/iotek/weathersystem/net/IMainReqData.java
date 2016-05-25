package com.iotek.weathersystem.net;

import java.util.List;

/**
 * Created by fhm on 2016/5/25.
 */
public interface IMainReqData {
    interface OnFinishedListener {
        void onFinished(List<String> items);
    }

    void reqData(OnFinishedListener listener);
}
