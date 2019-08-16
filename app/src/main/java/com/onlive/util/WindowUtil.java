package com.onlive.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

public class WindowUtil {
    //设置状态栏颜色
    public static void setStatusBarColor(Activity activity,int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            //取消设置Window半透明的Flag
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //添加Flag把状态栏设为可绘制模式
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏为透明
            window.setStatusBarColor(color);
        }
    }
    //隐藏状态栏
    public static void hideStatusBar(Activity activity){
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
