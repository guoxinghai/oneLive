package com.onlive.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String getCurrentDate(){
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
        return date.format(new Date());
    }
    public static int getTimeDifference(long oldTime,long newTime){
        return (int)(newTime-oldTime)/1000;
    }
}
