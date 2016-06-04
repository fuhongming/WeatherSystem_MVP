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

import com.iotek.weathersystem.R;
import com.iotek.weathersystem.adapter.WeatherAdapter;
import com.iotek.weathersystem.model.Result;
import com.iotek.weathersystem.presenter.IWeatherPresenter;
import com.iotek.weathersystem.presenter.WeatherPresenterImpl;
import com.iotek.weathersystem.ui.IWeatherView;
import com.iotek.weathersystem.utils.ToastUtils;
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

    @ViewInject(R.id.ivShare)
    ImageView ivShare;

    @ViewInject(R.id.lv)
    ListView lv;

    @ViewInject(R.id.rlBg)

    RelativeLayout rlBg;

    @ViewInject(R.id.refreshable_view)//下拉刷新，自定义view
    RefreshableView refreshableView;

    @ViewInject(R.id.tvCityName)
    TextView tvCityName;

    @ViewInject(R.id.ivLocation)
    ImageView ivLocation;

    private IWeatherPresenter presenter;
    WeatherAdapter weatherAdapter;
    private List<Result> list = new ArrayList<>();
    private Result weather = new Result();
    private final static int REQUEST_CODE = 1;

    private static final int UPDATE_TIME = 5000;
    String citynm;
    StringBuffer sb;

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
            presenter.switchCity(weather.getCitynm());
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
        Intent i = new Intent(WeatherActivity.this, LifeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("weather", list.get(position));
        i.putExtras(bundle);
        startActivity(i);
//        overridePendingTransition(R.anim.in, R.anim.out);

    }

    @OnClick(R.id.ivShare)
    public void ivShare(View v) {

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

    @OnClick(R.id.ivLocation)
    public void ivLocation(View v) {
//        tvCityName.setText(sb.toString()+citynm);
//        showMessage("正在定位...");
//        weather.setCitynm("上海");
//        tvCityName.setText(weather.getCitynm());
        presenter.switchCity(weather.getCitynm());

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (weather.getCitynm() == null || weather.getCitynm().equals("")) {
            weather.setCitynm("苏州");
        }
        tvCityName.setText(weather.getCitynm());
        presenter.switchCity(weather.getCitynm());
        presenter.onResume();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == CityPickerActivity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                weather.setCitynm(bundle.getString("city"));
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
            ToastUtils.showToast(WeatherActivity.this, "获取不到该城市的天气信息");
            return;
        }
        list = result;
        tvCityName.setText(weather.getCitynm());
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
