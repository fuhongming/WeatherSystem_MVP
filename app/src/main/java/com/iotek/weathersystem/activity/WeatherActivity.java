package com.iotek.weathersystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iotek.weathersystem.R;
import com.iotek.weathersystem.adapter.WeatherAdapter;
import com.iotek.weathersystem.model.Result;
import com.iotek.weathersystem.presenter.IWeatherPresenter;
import com.iotek.weathersystem.presenter.WeatherPresenterImpl;
import com.iotek.weathersystem.ui.IWeatherView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnRadioGroupCheckedChange;

import java.util.List;

public class WeatherActivity extends BaseActivity implements IWeatherView {

    @ViewInject(R.id.tvCityName)
    TextView tvCityName;

    @ViewInject(R.id.progress)
    private ProgressBar progressBar;

    @ViewInject(R.id.ivAddCity)
    ImageView ivAddCity;

    @ViewInject(R.id.ivShare)
    ImageView ivShare;

    @ViewInject(R.id.lv)
    ListView lv;

    private IWeatherPresenter presenter;
    public String city = "上海";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ViewUtils.inject(this);
        presenter = new WeatherPresenterImpl(this);
    }

    @OnClick(R.id.ivSearch)
    public void ivSearch(View v) {
        presenter.switchCity(city);

    }

    @OnClick(R.id.ivShare)
    public void ivShare(View v) {

    }
    @OnClick(R.id.ivAddCity)
    public void ivAddCity(View v) {
        Intent i=new Intent(WeatherActivity.this,CityActivity.class);
        startActivity(i);
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
        city = getIntent().getStringExtra("city");
        if (city == null) {
            city = "苏州";
        }
        tvCityName.setText(city);
        presenter.switchCity(city);
        presenter.onResume();

        super.onResume();
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
        WeatherAdapter weatherAdapter = new WeatherAdapter(this);
        lv.setAdapter(weatherAdapter);
        weatherAdapter.setData(result);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
