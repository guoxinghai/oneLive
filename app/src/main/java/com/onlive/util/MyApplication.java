package com.onlive.util;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;

public class MyApplication extends Application {
    private static Context context;
    private boolean status;//存储用户的登录状态
    private String ApplicationID = "";//bmob数据存储id
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, ApplicationID);
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
