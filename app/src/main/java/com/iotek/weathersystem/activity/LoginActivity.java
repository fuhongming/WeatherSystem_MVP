package com.iotek.weathersystem.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iotek.weathersystem.R;
import com.iotek.weathersystem.model.User;
import com.iotek.weathersystem.presenter.LoginPresenter;
import com.iotek.weathersystem.view.IUserLoginView;
import com.lidroid.xutils.view.annotation.ViewInject;

import cn.bmob.v3.Bmob;

public class LoginActivity extends AppCompatActivity implements IUserLoginView, View.OnClickListener {

    @ViewInject(R.id.User_Name)
    EditText mUserName;
    @ViewInject(R.id.User_Psd)
    EditText mUserPsd;
    @ViewInject(R.id.Login)
    Button mLogin;
    @ViewInject(R.id.tvWeibo)
    LinearLayout mTvWeibo;
    @ViewInject(R.id.tvQq)
    LinearLayout mTvQq;
    @ViewInject(R.id.login_progress)
    ProgressBar mLoginProgress;
    @ViewInject(R.id.forget_psd)
    TextView mForgetPsd;
    @ViewInject(R.id.register)
    TextView mRegister;

    private LoginPresenter mLoginPresenter=new LoginPresenter(this,this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        com.lidroid.xutils.ViewUtils.inject(this);

        mLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mTvQq.setOnClickListener(this);
        mTvWeibo.setOnClickListener(this);
        mForgetPsd.setOnClickListener(this);

//        User bmobUser= BmobUser.getCurrentUser(this,User.class);
//        if(bmobUser!=null){
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            LoginActivity.this.finish();
//            return;
//        }
        // 初始化BmobSDK
        Bmob.initialize(this, "7cdff4a819f558aad9f62a8ecc9ae33f");
    }

    @Override
    public String getUserName() {
        return mUserName.getText().toString();
    }

    @Override
    public String getPassword() {
        return mUserPsd.getText().toString();
    }

    @Override
    public void showLoading() {
            mLoginProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
            mLoginProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void toHomeActivity(User user) {
            mLoginPresenter.toHomeActivity();
    }

    @Override
    public void showFailedError() {
        Toast.makeText(LoginActivity.this
                , "用户名或者密码错误", Toast.LENGTH_LONG).show();
    }

    @Override
    public Context getContext() {
        return LoginActivity.this;
    }

    @Override
    public void ErrorOfUsnAndPsd() {
        if (TextUtils.isEmpty(mUserName.getText().toString()) || TextUtils.isEmpty(mUserPsd.getText().toString())) {
            showFailedError();
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Login:
                mLoginPresenter.login();
                break;
            case R.id.register:
                mLoginPresenter.toRegisterActivity();
                break;
//            case R.id.forget_psd:
            case R.id.tvQq:
                Toast.makeText(LoginActivity.this,"Sorry，该功能还未开放！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvWeibo:
                Toast.makeText(getApplicationContext(),"Sorry，该功能还未开放！",Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter=null;
    }
}
