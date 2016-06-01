package com.iotek.weathersystem.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.iotek.weathersystem.R;
import com.iotek.weathersystem.presenter.RegisterPresenter;
import com.iotek.weathersystem.view.IUserRegisterView;
import com.lidroid.xutils.view.annotation.ViewInject;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,IUserRegisterView {

    @ViewInject(R.id.username)
    EditText mUsername;
    @ViewInject(R.id.psd)
    EditText mPsd;
    @ViewInject(R.id.config_psd)
    EditText mConfigPsd;
    @ViewInject(R.id.email)
    EditText mEmail;
    @ViewInject(R.id.submit)
    Button mSubmit;
    @ViewInject(R.id.register_progress)
    ProgressBar mRegisterProgress;

    private RegisterPresenter mRegisterPresenter=new RegisterPresenter(RegisterActivity.this,this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        com.lidroid.xutils.ViewUtils.inject(this);
        mSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                mRegisterPresenter.register();

                break;
        }
    }

    @Override
    public void Success() {
        Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
        mRegisterPresenter.toLoginActivity();
    }

    @Override
    public void Failed() {
        Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showLoading() {
            mRegisterProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
            mRegisterProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void FinishAty() {
            RegisterActivity.this.finish();
    }

    @Override
    public String getUsername() {
        return mUsername.getText().toString();
    }

    @Override
    public String getPsd() {
        return mPsd.getText().toString();
    }

    @Override
    public String getEmail() {
        return mEmail.getText().toString();
    }

    @Override
    public String getConPsd() {
        return mConfigPsd.getText().toString();
    }

    @Override
    public Context getContext() {
        return RegisterActivity.this;
    }

    @Override
    public void ErrorOfUsnorPsdorEmail() {
        Toast.makeText(getApplicationContext(),"用户名,密码或者邮箱错误",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ErrorOfConfingerPsd() {
        Toast.makeText(getApplicationContext(),"两次密码不一致",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRegisterPresenter=null;
    }
}
