package com.onlive.util;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
    public static String getStringValue(String data,String param){
        JSONObject jsonObject;
        String msg = null;
        try {
            jsonObject = new JSONObject(data);
            msg = jsonObject.getString(param);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;
    }
    public static int getIntValue(String data,String param){
        JSONObject jsonObject;
        int msg = -1;
        try {
            jsonObject = new JSONObject(data);
            msg = jsonObject.getInt(param);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
