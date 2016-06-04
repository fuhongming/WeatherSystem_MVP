package com.iotek.weathersystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.iotek.weathersystem.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by fhm on 2016/5/28.
 */

public class MyActivity extends BaseActivity {
    @ViewInject(R.id.ivLogin)
    ImageView ivLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ViewUtils.inject(this);

    }
    @OnClick(R.id.ivLogin)
    public void ivLogin(View v){
//        Intent intent =new Intent(MyActivity.this,LoginActivity.class);
//        startActivity(intent);
    }
}
