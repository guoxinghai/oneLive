package com.onlive.presenter.login;


import android.content.Intent;
import android.text.Editable;

import com.onlive.presenter.base.BasePresenter;
import com.onlive.util.RegexUtils;
import com.onlive.util.WindowUtil;
import com.onlive.view.login.LoginActivity;

import static android.app.Activity.RESULT_OK;

public class LoginPresenter extends BasePresenter<LoginActivity> {

    public void onUserTextChange(Editable s){
        if(s!=null&&!RegexUtils.isPhoneNumber(s.toString())){
            getActivity().userInputPhoneError();
        }else{
            getActivity().userInputPhoneCorrect();
            if(!(getActivity().getTextInputLayoutErrorAble())){
                getActivity().setButtonStatus(true);
            }
        }
    }
    public void onPassTextChange(Editable s){

        if(s==null||!RegexUtils.isPassNumber(s.toString())){
            getActivity().userInputPassError();
        }else{
            getActivity().userInputPassCorrect();
            if(!(getActivity().getTextInputLayoutErrorAble())){
                getActivity().setButtonStatus(true);
            }
        }
    }
    public void hideStatusBar(){
        WindowUtil.hideStatusBar(getActivity());
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    String phone = data.getStringExtra("phone");
                    long sentTime = data.getLongExtra("sentTime",-1);
                    if(phone != null){
                        getActivity().putDataForBundle("phone",phone);
                    }
                    if(sentTime != -1){
                        getActivity().putDataForBundle("sentTime",sentTime);
                    }
                }
        }
    }
}
