package com.iotek.weathersystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.iotek.weathersystem.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by fhm on 2016/5/28.
 */
public class CityActivity extends Activity {
    @ViewInject(R.id.btnAdd)
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.btnAdd)
    public void btnAdd(View v){
        Intent intent = new Intent(CityActivity.this,SelectWeatherActivity.class);
        startActivity(intent);
    }
}
