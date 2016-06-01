package com.iotek.weathersystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iotek.weathersystem.R;
import com.iotek.weathersystem.model.Result;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by fhm on 2016/5/29.
 */
public class LifeActivity extends Activity {
    @ViewInject(R.id.tvCitynm)
    TextView tvCitynm;
    @ViewInject(R.id.tvWeek)
    TextView tvWeek;
    @ViewInject(R.id.tvDress)
    TextView tvDress;
    @ViewInject(R.id.tvSport)
    TextView tvSport;
    @ViewInject(R.id.tvWash)
    TextView tvWash;
    @ViewInject(R.id.tvMakeup)
    TextView tvMakeup;
    @ViewInject(R.id.tvLight)
    TextView tvLight;
    @ViewInject(R.id.back)
    Button back;

    @ViewInject(R.id.cbDressZambia)
    CheckBox cbDressZambia;

    @ViewInject(R.id.cbSportZambia)
    CheckBox cbSportZambia;

    @ViewInject(R.id.cbMakeupZambia)
    CheckBox cbMakeupZambia;

    @ViewInject(R.id.cbWashZambia)
    CheckBox cbWashZambia;

    @ViewInject(R.id.cbLightZambia)
    CheckBox cbLightZambia;

    @ViewInject(R.id.rlLife)
    RelativeLayout rlLife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        ViewUtils.inject(this);

        Intent intent = this.getIntent();
        Result weather = (Result) intent.getSerializableExtra("weather");
        String we = weather.getWeather();
        int temp_high = Integer.parseInt(weather.getTemp_high());
        tvCitynm.setText(weather.getCitynm());
        tvWeek.setText(weather.getWeek());
        if (we.contains("雨")) {
            rlLife.setBackgroundResource(R.drawable.bg_rain);
            tvSport.setText("建议在室内运动，外出记得带雨具");
            tvWash.setText("不建议洗车");
            tvMakeup.setText("不建议化浓妆");
            tvLight.setText("紫外线较弱");
            if (temp_high > 27) {
                tvDress.setText("今天有雨，天气炎热，请合理穿衣");
            } else if (temp_high <= 27 && temp_high > 18) {
                tvDress.setText("今天温度适宜，适合穿T恤，长衫");
            } else {
                tvDress.setText("温度较低，请注意添衣");
            }
        } else if (we.contains("晴")) {
            rlLife.setBackgroundResource(R.drawable.bg_fine_day);
            tvSport.setText("可在室外运动");
            tvWash.setText("建议洗车");
            tvMakeup.setText("建议化妆");
            tvLight.setText("紫外线较强，注意防晒");
            if (temp_high > 27) {
                tvDress.setText("天气炎热，适合穿短袖");
            } else if (temp_high <= 27 && temp_high > 18) {
                tvDress.setText("今天温度适宜，适合穿T恤，长衫");
            } else {
                tvDress.setText("温度较低，请注意添衣");
            }
        } else if (we.contains("阴")) {
            rlLife.setBackgroundResource(R.drawable.bg_cloudy);
            tvSport.setText("建议在室内运动，可能会有降雨，外出准备好雨具");
            tvWash.setText("按个人规划洗车");
            tvMakeup.setText("按个人喜好洗车");
            tvLight.setText("紫外线相对较弱");
            if (temp_high > 27) {
                tvDress.setText("天气较热，适合穿短袖");
            } else if (temp_high <= 27 && temp_high > 18) {
                tvDress.setText("今天温度适宜，适合穿T恤，长衫");
            } else {
                tvDress.setText("温度较低，请注意添衣");
            }
        } else if (we.contains("多云")) {
            rlLife.setBackgroundResource(R.drawable.bg_cloudy);
            tvWash.setText("按个人规划洗车");
            tvMakeup.setText("按个人喜好洗车");
            tvLight.setText("紫外线相对较弱");
            if (temp_high > 27) {
                tvDress.setText("天气较热，适合穿短袖");
            } else if (temp_high <= 27 && temp_high > 18) {
                tvDress.setText("今天温度适宜，适合穿T恤，长衫");
            } else {
                tvDress.setText("温度较低，请注意添衣");
            }
        } else if (we.contains("雪")) {
            rlLife.setBackgroundResource(R.drawable.bg_snow);
            tvDress.setText("温度较低，请注意保暖");
            tvSport.setText("建议在室内运动，会有降雪，外出准备好雨具");
            tvWash.setText("按个人规划洗车");
            tvMakeup.setText("不适宜洗车");
            tvLight.setText("紫外线相对较弱");
        }

    }

    @OnClick(R.id.back)
    public void back(View v) {
        finish();
    }

    @OnClick({R.id.cbDressZambia,R.id.cbSportZambia,R.id.cbDressZambia,R.id.cbWashZambia,R.id.cbMakeupZambia,R.id.cbLightZambia})
    public void ivDressZambia(View v) {

        switch (v.getId()){
/*            case R.id.cbDressZambia:
                if (cbDressZambia.isChecked()) {
                    cbDressZambia.setBackgroundResource(R.drawable.heart);
                } else {
                    cbDressZambia.setBackgroundResource(R.drawable.heart_selected);
                }
                break;
            case R.id.cbSportZambia:
                if (cbSportZambia.isChecked()) {
                    cbSportZambia.setBackgroundResource(R.drawable.heart);
                } else {
                    cbSportZambia.setBackgroundResource(R.drawable.heart_selected);
                }
                break;
            case R.id.cbWashZambia:
                if (cbWashZambia.isChecked()) {
                    cbWashZambia.setBackgroundResource(R.drawable.heart);
                } else {
                    cbWashZambia.setBackgroundResource(R.drawable.heart_selected);
                }
                break;
            case R.id.cbLightZambia:
                if (cbLightZambia.isChecked()) {
                    cbLightZambia.setBackgroundResource(R.drawable.heart);
                } else {
                    cbLightZambia.setBackgroundResource(R.drawable.heart_selected);
                }
                break;
            case R.id.cbMakeupZambia:
                if (cbMakeupZambia.isChecked()) {
                    cbMakeupZambia.setBackgroundResource(R.drawable.heart);
                } else {
                    cbMakeupZambia.setBackgroundResource(R.drawable.heart_selected);
                }
                break;*/
        }
    }

}
