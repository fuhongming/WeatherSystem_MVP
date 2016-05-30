package com.iotek.weathersystem.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.widget.ListAdapter;

import com.google.gson.Gson;
import com.iotek.weathersystem.commom.Canstants;
import com.iotek.weathersystem.model.City;
import com.iotek.weathersystem.model.Result;
import com.iotek.weathersystem.model.Root;
import com.iotek.weathersystem.utils.FileUtils;
import com.iotek.weathersystem.utils.ToastUtils;
import com.iotek.weathersystem.utils.Tools;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.xutils.common.util.FileUtil;

import java.util.List;

/**
 * Created by fhm on 2016/5/25.
 */
public class WeatherReqImpl implements IWeatherReq {

    @Override
    public void reqData(final OnFinishedListener listener, String city, Context context) {
        //请求数据
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//            }
//        }, 3000);


        if (Tools.isNetConn(context)) {
            getDataTask("http://api.k780.com:88/?app=weather.future&weaid=" + city + "&&appkey=19106&sign=0b907c12b37b9d71933c1532b2864113&format=json", listener);

        } else {
            ToastUtils.showToast(context,"网络无连接");
            String jsonStr = FileUtils.read(Canstants.WEATHER_NAME);
            if (jsonStr != null) {
                Gson gson = new Gson();
                Root root = gson.fromJson(jsonStr, Root.class);
                listener.onFinished(root.getResult());
            }
        }
    }

    public void getDataTask(String url, final OnFinishedListener listener) {
        RequestParams params = new RequestParams("UTF-8");
//        params.addBodyParameter("key", value);
        HttpUtils http = new HttpUtils();
//        http.configDefaultHttpCacheExpiry(0);
        http.configTimeout(3000);
        http.send(HttpRequest.HttpMethod.GET, url, params, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Gson gson = new Gson();
                Root root = gson.fromJson(responseInfo.result, Root.class);
                if(root==null){
                    return;
                }
                FileUtils.save(responseInfo.result, Canstants.WEATHER_NAME);
                listener.onFinished(root.getResult());
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                listener.onFinished(null);
            }
        });
    }
}
