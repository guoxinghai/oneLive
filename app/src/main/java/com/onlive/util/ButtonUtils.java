package com.onlive.util;

import android.content.res.Resources;
import android.widget.Button;

import com.onlive.R;

public class ButtonUtils {
    public static void setLoginButtonClick(Button button, Resources resources, boolean flag){
        if(flag){
            button.setBackgroundColor(resources.getColor(R.color.colorPrimary));
            button.setClickable(true);
        }else{
            button.setBackgroundColor(resources.getColor(R.color.gray));
            button.setClickable(false);
        }
    }
}
