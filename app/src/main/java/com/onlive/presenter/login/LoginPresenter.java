package com.onlive.presenter.login;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Editable;

import com.onlive.model.User;
import com.onlive.presenter.base.BasePresenter;
import com.onlive.util.RegexUtils;
import com.onlive.util.WindowUtil;
import com.onlive.view.login.LoginActivity;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SQLQueryListener;

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
    public void userLogin(User user){
        //打开加载框
        getActivity().showProgressDialog();
        //查询用户输入的手机号和密码是否匹配
        String sql = "select * from User where phone = ? and password = ?";
        BmobQuery<User> query = new BmobQuery<>();
        query.doSQLQuery(sql, new SQLQueryListener<User>() {
            @Override
            public void done(BmobQueryResult<User> bmobQueryResult, BmobException e) {
                getActivity().closeProgressDialog();
                if(e==null&&bmobQueryResult.getResults().size()>0){
                    //输入正确
                    saveMessage(user);//存储基本信息
                    getActivity().launchHomeActivity();//加载主界面
                }else{
                    //输入错误
                    getActivity().showAlertDialog("用户名或密码输入错误请重新输入！");
                }
            }
        },user.getPhone(),user.getPassword());
    }
    //存储用户登录信息
    private void saveMessage(User user){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
        editor.putBoolean("status",true);//登录状态
        editor.putString("phone",user.getPhone());
        editor.putString("password",user.getPassword());
        editor.apply();
    }
}
