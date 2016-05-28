package com.iotek.weathersystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.iotek.weathersystem.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by fhm on 2016/5/27.
 */
public class SelectWeatherActivity extends Activity {
    @ViewInject(R.id.ivSearch)
    ImageView ivSearch;
    @ViewInject(R.id.etSearch)
    EditText etSearch;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_weather);
    }

    @OnClick(R.id.ivSearch)
    public void ivSearch(View v) {
        i = new Intent(SelectWeatherActivity.this, WeatherActivity.class);
        i.putExtra("city", etSearch.getText());

        startActivity(i);
    }
}
