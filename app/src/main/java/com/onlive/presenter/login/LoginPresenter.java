package com.onlive.presenter.login;


import com.onlive.presenter.base.BasePresenter;
import com.onlive.util.WindowUtil;
import com.onlive.view.login.LoginActivity;

public class LoginPresenter extends BasePresenter<LoginActivity> {

    public void setStatusBarColor(int color) {
        WindowUtil.setStatusBarColor(getActivity(),color);
    }
    public void setRegisterListener(){
        getActivity().setRegisterListener();
    }
    public void setTextInputEditTextListener(){
        getActivity().setUserTextInputEditTextListener();
        getActivity().setPassTextInputEditTextListener();
    }
}
