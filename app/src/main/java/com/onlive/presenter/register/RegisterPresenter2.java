package com.onlive.presenter.register;

import com.onlive.presenter.base.BasePresenter;
import com.onlive.util.WindowUtil;
import com.onlive.view.register.RegisterActivity2;

public class RegisterPresenter2 extends BasePresenter<RegisterActivity2> {
    public void hideStatusBar(){
        WindowUtil.hideStatusBar(getActivity());
    }
    public void onVerifyCodeInput(String inputCode){
        if(inputCode.equals(getActivity().getVerifyCode())){
            getActivity().inputVerifyCodeCorrect();
        }else{
            getActivity().showAlertDialog("验证码输入错误!");
        }
    }
}
