package com.onlive.util;


public class ValueUtil {

    public static String getValue(int id){
        return MyApplication.getContext().getResources().getString(id);
    }
}
