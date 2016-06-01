package com.iotek.weathersystem.view;

import android.content.Context;

import com.iotek.weathersystem.model.User;


/**
 * Created by boobooL on 2016/4/12 0012
 * Created 邮箱 ：boobooMX@163.com
 */
public interface IUserLoginView {

    String getUserName();
    String getPassword();
    void showLoading();
    void  hideLoading();
    void toHomeActivity(User user);
    void showFailedError();
    Context getContext();
    void ErrorOfUsnAndPsd();


}
