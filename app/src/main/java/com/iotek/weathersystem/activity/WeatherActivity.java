package com.iotek.weathersystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.iotek.weathersystem.R;
import com.iotek.weathersystem.adapter.WeatherAdapter;
import com.iotek.weathersystem.commom.Canstants;
import com.iotek.weathersystem.model.Result;
import com.iotek.weathersystem.model.Root;
import com.iotek.weathersystem.presenter.IWeatherPresenter;
import com.iotek.weathersystem.presenter.WeatherPresenterImpl;
import com.iotek.weathersystem.ui.IWeatherView;
import com.iotek.weathersystem.utils.FileUtils;
import com.iotek.weathersystem.utils.ToastUtils;
import com.iotek.weathersystem.utils.Tools;
import com.iotek.weathersystem.view.RefreshableView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends BaseActivity implements IWeatherView {

    @ViewInject(R.id.progress)
    private ProgressBar progressBar;

    @ViewInject(R.id.ivCity)
    ImageView ivCity;

    @ViewInject(R.id.ivShare)//分享
    ImageView ivShare;

    @ViewInject(R.id.lv)//每日天气
    ListView lv;

    @ViewInject(R.id.rlBg) //背景图片

    RelativeLayout rlBg;

    @ViewInject(R.id.refreshable_view)//下拉刷新，自定义view
    RefreshableView refreshableView;

    @ViewInject(R.id.tvCityName)  //城市名
    TextView tvCityName;

    private IWeatherPresenter presenter;
    WeatherAdapter weatherAdapter;
    private List<Result> list = new ArrayList<>();
    private String city = "苏州";
    private final static int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather);
        ViewUtils.inject(this);
        presenter = new WeatherPresenterImpl(this, this);
        weatherAdapter = new WeatherAdapter(this);
        lv.setAdapter(weatherAdapter);

        /**
         * 刷新数据信息
         */
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
                handler.sendEmptyMessage(1);
            }
        }, 0);

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            presenter.switchCity(city);
        }
    };

    /**
     * activity之间传值
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @OnItemClick(R.id.lv)
    public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position==0){
            return;
        }
        Intent i = new Intent(WeatherActivity.this, LifeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("weather", list.get(position - 1));
        i.putExtras(bundle);
        startActivity(i);

    }

    @OnClick(R.id.ivShare)

    public void ivShare(View v) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        intent.putExtra(Intent.EXTRA_TEXT, "今天："+ list.get(0).getCitynm()+" "+list.get(0).getWeather()+" "+list.get(0).getTemperature() + " " + list.get(0).getWind());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getTitle()));
    }

    @OnClick(R.id.ivCity)
    public void ivCity(View v) {
        startCityPickerActivity();
    }

    @OnClick(R.id.tvCityName)
    public void tvCityName(View v) {
        startCityPickerActivity();
    }

    private void startCityPickerActivity() {
        Intent intent = new Intent();
        intent.setClass(WeatherActivity.this, CityPickerActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        tvCityName.setText(city);
        presenter.switchCity(city);
        if (Tools.isNetConn(this)) {
            presenter.onResume();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == CityPickerActivity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                city = bundle.getString("city");
            }
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);

    }

    public void showData(List<Result> result) {
        weatherAdapter.setData(result);
        if (result == null) {
            ToastUtils.showToast(WeatherActivity.this, "查询不到该城市的天气信息");
            return;
        }
        list = result;
        city = list.get(0).getCitynm();
        tvCityName.setText(city);
        String weather = result.get(0).getWeather();
        if (weather.contains("晴")) {
            rlBg.setBackgroundResource(R.drawable.bg_fine_day);
        } else if (weather.contains("阴")) {
            rlBg.setBackgroundResource(R.drawable.bg_cloudy);
        }else if(weather.contains("雨")) {
            rlBg.setBackgroundResource(R.drawable.rain_bg);
        }else if (weather.contains("雪")) {
            rlBg.setBackgroundResource(R.drawable.bg_snow);
        } else if (weather.contains("多云")) {
            rlBg.setBackgroundResource(R.drawable.bg_cloudy);

        } else {
            rlBg.setBackgroundResource(R.drawable.bg16);
        }
    }

    @Override
    public void showMessage(String message) {
        ToastUtils.showToast(this, message);
    }

}
