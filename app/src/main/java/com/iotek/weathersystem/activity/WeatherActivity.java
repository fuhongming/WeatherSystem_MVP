package com.iotek.weathersystem.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iotek.weathersystem.R;
import com.iotek.weathersystem.adapter.WeatherAdapter;
import com.iotek.weathersystem.db.Db;
import com.iotek.weathersystem.model.City;
import com.iotek.weathersystem.model.Result;
import com.iotek.weathersystem.presenter.IWeatherPresenter;
import com.iotek.weathersystem.presenter.WeatherPresenterImpl;
import com.iotek.weathersystem.ui.IWeatherView;
import com.iotek.weathersystem.utils.ToastUtils;
import com.iotek.weathersystem.utils.Tools;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import org.xutils.x;

import java.util.List;

public class WeatherActivity extends BaseActivity implements IWeatherView {

    @ViewInject(R.id.tvCityName)
    TextView tvCityName;

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

    private IWeatherPresenter presenter;
    WeatherAdapter weatherAdapter;
    public String city = "上海";
    private final static int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ViewUtils.inject(this);
        presenter = new WeatherPresenterImpl(this);
        weatherAdapter = new WeatherAdapter(this);
        lv.setAdapter(weatherAdapter);

    }

    @OnItemClick(R.id.lv)
    public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
//        index = position;
        Intent i = new Intent(WeatherActivity.this, LifeActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.in, R.anim.out);

    }
    @OnClick(R.id.ivShare)
    public void ivShare(View v) {

    }

    @OnClick(R.id.ivCity)
    public void ivCity(View v) {
        startCityPickerActivity();
        overridePendingTransition(R.anim.in, R.anim.out);

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

    @OnClick(R.id.title_location)
    public void title_location(View v) {
        showMessage("正在定位...");
        city = "上海";
        tvCityName.setText(city);
        presenter.switchCity(city);
    }

    @Override
    protected void onResume() {
        city = (city == null ? city = "苏州":city);
        tvCityName.setText(city);
        presenter.switchCity(city);
        presenter.onResume();

        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode==REQUEST_CODE)
        {
            if (resultCode==CityPickerActivity.RESULT_OK)
            {
                Bundle bundle=data.getExtras();
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
        if(result==null){
            ToastUtils.showToast(WeatherActivity.this,"获取不到该城市的天气信息");
            return;
        }
        String weather=result.get(0).getWeather();
        if(weather.contains("晴")){
            rlBg.setBackgroundResource(R.drawable.bg_fine_day);
        }else if(weather.contains("阴")){
            rlBg.setBackgroundResource(R.drawable.bg01);
        }else if(weather.contains("雨")) {
            rlBg.setBackgroundResource(R.drawable.bg_rain);
        }else if(weather.contains("雪")){
            rlBg.setBackgroundResource(R.drawable.bg_snow);
        }
    }

    @Override
    public void showMessage(String message) {
        ToastUtils.showToast(this, message);
    }

}
