package com.iotek.weathersystem.presenter;

import android.content.Context;
import android.content.Intent;

import com.iotek.weathersystem.activity.RegisterActivity;
import com.iotek.weathersystem.activity.WeatherActivity;
import com.iotek.weathersystem.model.User;
import com.iotek.weathersystem.ui.IUserBiz;
import com.iotek.weathersystem.ui.OnLoginListener;
import com.iotek.weathersystem.ui.UserBiz;
import com.iotek.weathersystem.view.IUserLoginView;


/**
 * Created by boobooL on 2016/4/12 0012
 * Created 邮箱 ：boobooMX@163.com
 */
public class LoginPresenter {
    private IUserBiz mIUserBiz;
    private IUserLoginView mUserLoginView;
    private Context mContext;

    public LoginPresenter(Context context, IUserLoginView userLoginView) {
        mIUserBiz=new UserBiz();
        mContext = context;
        mUserLoginView = userLoginView;
    }

    public void login(){
        mUserLoginView.showLoading();
        mIUserBiz.login(mUserLoginView.getUserName(), mUserLoginView.getPassword(), new OnLoginListener() {
            @Override
            public void OnSuccess(User user) {
                mUserLoginView.hideLoading();
                mUserLoginView.toHomeActivity(user);
            }

            @Override
            public void OnFailed() {

                mUserLoginView.hideLoading();
                mUserLoginView.showFailedError();

            }
        },mUserLoginView.getContext());
    }

    public void toHomeActivity(){
        Intent intent=new Intent(mContext, WeatherActivity.class);
        mContext.startActivity(intent);
    }

    //跳转到注册的页面
    public  void  toRegisterActivity(){
        Intent intent = new Intent(mContext
                , RegisterActivity.class);
        mContext.startActivity(intent);
    }
}
