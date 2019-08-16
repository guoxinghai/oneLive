package com.onlive.presenter.register;

import android.text.Editable;
import android.util.Log;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.onlive.presenter.base.BasePresenter;
import com.onlive.util.RegexUtils;
import com.onlive.util.VerifyCodeUtil;
import com.onlive.util.WindowUtil;
import com.onlive.view.register.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

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
            getActivity().userInputPhoneCorrect();
        }
    }
    public void sendVerifyCode(String phone,int n){
        final String code = VerifyCodeUtil.getVerifyCode(n);
        final CommonRequest request = VerifyCodeUtil.getVerityCodeRequest(phone,code);
        final IAcsClient client = VerifyCodeUtil.getClient();
        new Thread(new Runnable(){
            @Override
            public void run() {
                CommonResponse response;
                try {
                    response = client.getCommonResponse(request);
                    JSONObject jsonObject = new JSONObject(response.getData());
                    String msg = jsonObject.getString("Message");
                    if("OK".equals(msg)){
                        getActivity().sendVerifyCodeSuccess(code);
                    }else{
                        getActivity().sendVerifyCodeFailed();
                    }
                    Log.d("VerifyCode",response.getData());
                } catch (ClientException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
