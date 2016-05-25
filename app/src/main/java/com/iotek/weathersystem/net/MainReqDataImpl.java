package com.iotek.weathersystem.net;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

/**
 * Created by fhm on 2016/5/25.
 */
public class MainReqDataImpl implements IMainReqData {
    @Override
    public void reqData(final OnFinishedListener listener) {
        // 请求数据
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataTask("", listener);
            }
        }, 3000);
        ;

    }

    public void getDataTask(String url, final OnFinishedListener listener) {
        RequestParams params = new RequestParams("UTF-8");
//        params.addBodyParameter("key", value);
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, url, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Gson gson = new Gson();
//                Root root = gson.fromJson(responseInfo.result, Root.class);
                List<String> list = null;
                listener.onFinished(list);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                List<String> list = null;
                listener.onFinished(list);
            }
        });
    }
}
