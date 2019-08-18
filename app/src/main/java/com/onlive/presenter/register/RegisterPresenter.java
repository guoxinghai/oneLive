package com.onlive.presenter.register;

import android.text.Editable;
import android.util.Log;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.onlive.presenter.base.BasePresenter;
import com.onlive.util.JsonUtil;
import com.onlive.util.RegexUtils;
import com.onlive.util.VerifyCodeUtil;
import com.onlive.util.WindowUtil;
import com.onlive.view.register.RegisterActivity;


public class RegisterPresenter extends BasePresenter<RegisterActivity> {
    //隐藏状态栏
    public void hideStatusBar(){
        WindowUtil.hideStatusBar(getActivity());
    }
    //为InputEditText设置监听
    public void setUserTextInputEditTextListener(){
        getActivity().setUserTextInputEditTextListener();
    }
    public void onUserTextChange(Editable s){
        if(s!=null&&!RegexUtils.isPhoneNumber(s.toString())){
            getActivity().userInputPhoneError();
        }else{
            //判断
            getActivity().userInputPhoneCorrect();
            //判断按钮是否正在倒计时
            if(!getActivity().isCountDown()){
                getActivity().setButtonStatus(true);
            }
        }
    }
    public void sendVerifyCode(String phone,int n){
        final String code = VerifyCodeUtil.getVerifyCode(n);
        //获取查询发送验证码次数的request
        final CommonRequest request_time = VerifyCodeUtil.queryVerifyCodeSentTimeRequest(phone);
        //获取发送验证码的request
        final CommonRequest request_code = VerifyCodeUtil.getVerityCodeRequest(phone,code);
        final IAcsClient client = VerifyCodeUtil.getClient();
        //开启按钮倒计时
        getActivity().startCountDown();
        //保存发送验证码时间
        getActivity().setSent_time(System.currentTimeMillis());
        new Thread(new Runnable(){
            @Override
            public void run() {
                CommonResponse response;
                String msg;
                String data;
                try {
                    response = client.getCommonResponse(request_time);
                    data = response.getData();
                    msg = JsonUtil.getStringValue(data,"Message");
                    Log.d("VerifyCode",data);
                    if("OK".equals(msg)){
                        int count = JsonUtil.getIntValue(data,"TotalCount");
                        if(count > 5){
                            //超过五次
                            getActivity().sendVerifyCodeTimesExceeding();
                            return;
                        }
                    }else {
                        getActivity().sendVerifyCodeFailed();
                        return;
                    }
                    response = client.getCommonResponse(request_code);
                    data = response.getData();
                    msg = JsonUtil.getStringValue(data,"Message");
                    if("OK".equals(msg)){
                        getActivity().sendVerifyCodeSuccess(code);
                    }else{
                        getActivity().sendVerifyCodeFailed();
                    }
                    Log.d("VerifyCode",response.getData());
                } catch (ClientException e) {
                    e.printStackTrace();
                    getActivity().sendVerifyCodeFailed();
                }
            }
        }).start();
    }


}
