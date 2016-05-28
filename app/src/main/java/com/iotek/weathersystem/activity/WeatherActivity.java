package com.iotek.weathersystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iotek.weathersystem.R;
import com.iotek.weathersystem.adapter.WeatherAdapter;
import com.iotek.weathersystem.model.Result;
import com.iotek.weathersystem.presenter.IWeatherPresenter;
import com.iotek.weathersystem.presenter.WeatherPresenterImpl;
import com.iotek.weathersystem.ui.IWeatherView;
import com.iotek.weathersystem.utils.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

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
    }

    @Override
    public void showMessage(String message) {
        ToastUtils.showToast(this, message);
    }

}
