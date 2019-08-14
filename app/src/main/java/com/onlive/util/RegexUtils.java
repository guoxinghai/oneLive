package com.onlive.util;

import java.util.regex.Pattern;

public class RegexUtils {
    public static boolean isPhoneNumber(String number){
        String regex = "^1[3-9]\\d{9}$";
        return Pattern.matches(regex,number);
    }
    public static boolean isPassNumber(String pass){
        String regex = "^(?![a-zA-Z]+$)(?!\\d+$)[0-9a-zA-Z]{6,20}";
        return Pattern.matches(regex,pass);
    }
}
