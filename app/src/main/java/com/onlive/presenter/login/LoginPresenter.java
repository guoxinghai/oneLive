package com.onlive.presenter.login;


import android.text.Editable;

import com.onlive.presenter.base.BasePresenter;
import com.onlive.util.RegexUtils;
import com.onlive.util.WindowUtil;
import com.onlive.view.login.LoginActivity;

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

}
