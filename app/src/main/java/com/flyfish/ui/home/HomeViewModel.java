package com.flyfish.ui.home;

import com.flyfish.bean.Password;

public class HomeViewModel {

    private static final HomeViewModel instance = new HomeViewModel();

    private Password password;
    private String appName;
    private Integer passWordLen;

    private HomeViewModel(){}

    public static HomeViewModel getInstance() {
        return instance;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getPassWordLen() {
        return passWordLen;
    }

    public void setPassWordLen(Integer passWordLen) {
        this.passWordLen = passWordLen;
    }
}
