package com.iotek.weathersystem.net;

import android.os.Handler;
import android.util.Log;
import android.widget.ListAdapter;

import com.google.gson.Gson;
import com.iotek.weathersystem.activity.MainActivity;
import com.iotek.weathersystem.model.Root;
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
    ListAdapter adapter;

    @Override
    public void reqData(final OnFinishedListener listener, String city) {
        // 请求数据
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//            }
//        }, 3000);
        getDataTask("http://v.juhe.cn/weather/index?format=2&cityname=" + city + "&key=9e5f02e00952d4ed3579e62545d63531", listener);

    }

    public void getDataTask(String url, final OnFinishedListener listener) {
        RequestParams params = new RequestParams("UTF-8");
//        params.addBodyParameter("key", value);
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, url, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Gson gson = new Gson();
                Root root = gson.fromJson(responseInfo.result, Root.class);
                listener.onFinished(root.getResult());
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                List<String> list = null;
                listener.onFinished(null);
            }
        });
    }
}
