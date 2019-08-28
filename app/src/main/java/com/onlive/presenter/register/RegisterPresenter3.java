package com.onlive.presenter.register;

import android.text.Editable;

import com.onlive.model.User;
import com.onlive.presenter.base.BasePresenter;
import com.onlive.util.RegexUtils;
import com.onlive.util.WindowUtil;
import com.onlive.view.register.RegisterActivity3;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterPresenter3 extends BasePresenter<RegisterActivity3> {
    public void hideStatusBar(){
        WindowUtil.hideStatusBar(getActivity());
    }
    public void inputPassFirst(Editable pass){
        if(pass!=null&& RegexUtils.isPassNumber(pass.toString())){
            getActivity().setError(null,2,false);
            setButtonStatus(true);
        }else{
            getActivity().setError("密码格式错误(6-20位同时包含数字和字母)",2,true);
            setButtonStatus(false);
        }
    }
    public void inputPassSecond(Editable pass){
        if(pass==null||!RegexUtils.isPassNumber(pass.toString())){
            getActivity().setError("密码格式错误(6-20位同时包含数字和字母)",3,true);
            setButtonStatus(false);
        }else if(!getActivity().passEquals()){
            getActivity().setError("两次密码输入不相等",3,true);
            setButtonStatus(false);
        }else{
            getActivity().setError(null,3,false);
            setButtonStatus(true);
        }
    }
    public void inputUserName(){
        if(getActivity().userNameIsEmpty()){
            getActivity().setError("用户名不能为空!",1,true);
        }else if(getActivity().passEquals()){
            getActivity().setButtonStatus(true);
            getActivity().setError(null,1,false);
        }
    }
    public void setButtonStatus(boolean flag){
        if(flag&&getActivity().passEquals()&&!getActivity().userNameIsEmpty()){
            getActivity().setButtonStatus(true);
        }else {
            getActivity().setButtonStatus(false);
        }
    }
    public void userRegister(User user){
        //显示加载框
        getActivity().showProgressDialog();
        //检查用户名是否冲突
        String sql = "select * from User where username = ?";
        BmobQuery<User> query = new BmobQuery<>();
        query.doSQLQuery(sql, new SQLQueryListener<User>() {
            @Override
            public void done(BmobQueryResult<User> bmobQueryResult, BmobException e) {
                if(e==null){
                    if(bmobQueryResult.getResults().size()>0){
                        getActivity().closeProgressDialog();
                        getActivity().showAlertDialog("用户名被占用!");
                        return;
                    }
                }else{
                    getActivity().closeProgressDialog();
                    getActivity().showAlertDialog("注册失败!");
                    return;
                }
                //向用户表中插入数据
                saveUserMessage(user);
            }
        },user.getUsername());
    }
    //保存用户信息
    public void saveUserMessage(User user){
        user.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                getActivity().closeProgressDialog();
                if(e==null){
                    getActivity().showAlertDialog("注册成功!");
                    getActivity().setAlertDialogClickAble(false);
                    getActivity().setAlertDialogButtonListener();
                }else{
                    e.printStackTrace();
                    getActivity().showAlertDialog("注册失败!");
                }
            }
        });
    }
}
