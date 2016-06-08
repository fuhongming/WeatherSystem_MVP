package com.iotek.weathersystem.activity;


import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.Toast;

import com.iotek.weathersystem.R;

public class MainTab extends ActivityGroup implements OnClickListener {
    public static final String TAB_WEATHER = "TAB_WEATHER"; //天气
    public static final String TAB_REALVIEW = "TAB_REALVIEW";//实景
    public static final String TAB_MY = "TAB_MY";            //我的
    private TabHost mHost;
    private Intent intentWeather, intentRealView, intentMy;
    private RadioButton btnWeather, btnRealView, btnMy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.tab_main);
        btnWeather = (RadioButton) findViewById(R.id.tabmain_weather);
        btnRealView = (RadioButton) findViewById(R.id.tabmain_realview);
        btnMy = (RadioButton) findViewById(R.id.tabmain_my);
        btnWeather.setOnClickListener(this);
        btnRealView.setOnClickListener(this);
        btnMy.setOnClickListener(this);
        mHost = (TabHost) findViewById(R.id.tabhost);
        mHost.setup(this.getLocalActivityManager());
        initTabs();

        btnWeather.setChecked(true);
        mHost.setCurrentTabByTag(TAB_WEATHER);
    }


    private void initTabs() {
        intentWeather = new Intent(this, WeatherActivity.class);
        intentRealView = new Intent(this, RealViewActivity.class);
        intentMy = new Intent(this, MyActivity.class);
        mHost.addTab(buildTabSpec(TAB_WEATHER, TAB_REALVIEW, R.drawable.defult, intentWeather));
        mHost.addTab(buildTabSpec(TAB_REALVIEW, TAB_REALVIEW, R.drawable.defult, intentRealView));
        mHost.addTab(buildTabSpec(TAB_MY, TAB_REALVIEW, R.drawable.defult, intentMy));
    }

    private TabHost.TabSpec buildTabSpec(String tag, String resLabel, int resIcon, final Intent content) {
        return this.mHost.newTabSpec(tag).setIndicator(resLabel, getResources().getDrawable(resIcon))
                .setContent(content);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.tabmain_weather:
                mHost.setCurrentTabByTag(TAB_WEATHER);
                break;
            case R.id.tabmain_realview:
                mHost.setCurrentTabByTag(TAB_REALVIEW);
                break;
            case R.id.tabmain_my:
                mHost.setCurrentTabByTag(TAB_MY);
                break;
        }
    }

}
