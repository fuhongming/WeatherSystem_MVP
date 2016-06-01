package com.iotek.weathersystem.ui;


import com.iotek.weathersystem.model.User;

/**
 * Created by boobooL on 2016/4/12 0012
 * Created 邮箱 ：boobooMX@163.com
 */
public interface OnLoginListener {

    void OnSuccess(User user);
    void OnFailed();
}
