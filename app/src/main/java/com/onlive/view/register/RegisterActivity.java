package com.onlive.view.register;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.onlive.R;
import com.onlive.presenter.register.RegisterPresenter;
import com.onlive.util.ButtonUtils;
import com.onlive.util.ProcessBarUtil;

public class RegisterActivity extends AppCompatActivity {
    private int verifyCoden = 6;
    private Button button;
    private RegisterPresenter registerPresenter;
    private TextInputEditText rg_user;
    private TextInputLayout rg_user_layout;
    private AlertDialog ProcessAlertDialog;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化操作
        init();
    }
    private void init(){
        button = findViewById(R.id.rg_register);
        rg_user = findViewById(R.id.rg_user);
        rg_user_layout = findViewById(R.id.rg_user_layout);
        registerPresenter = new RegisterPresenter();
        //与registerPresenter绑定
        registerPresenter.onAttach(this);
        //隐藏状态栏
        registerPresenter.hideStatusBar();
        //为lg_user设置监听者
        registerPresenter.setUserTextInputEditTextListener();


    }
    //位rg_user设置监听者
    public void setUserTextInputEditTextListener(){
        rg_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerPresenter.onUserTextChange(s);
            }
        });
    }
    //用户手机号输入错误
    public void userInputPhoneError(){
        rg_user_layout.setError("手机号格式错误");
        rg_user_layout.setErrorEnabled(true);
        setButtonStatus(false);
    }
    //用户手机号输入正确
    public void userInputPhoneCorrect(){
        rg_user_layout.setErrorEnabled(false);
        setButtonStatus(true);
        setButtonListener();
    }
    //设置按钮状态
    public void setButtonStatus(boolean flag){
        ButtonUtils.setLoginButtonClick(button,getResources(),flag);
    }
    //为button设置监听者
    public void setButtonListener(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                registerPresenter.sendVerifyCode(rg_user.getText().toString(),verifyCoden);
            }
        });
    }
    //开启加载弹出框
    public void showProgressDialog(){
        if(ProcessAlertDialog == null){
            ProcessAlertDialog = ProcessBarUtil.showProgressDialog(this,"Title","MSG",null,null,null,null,true);
        }else{
            ProcessAlertDialog.show();
        }

    }
    //发送验证码成功
    public void sendVerifyCodeSuccess(String code){
                Intent intent = new Intent(RegisterActivity.this,RegisterActivity2.class);
                intent.putExtra("code",code);
                closeProgressDialog();
                startActivity(intent);
    }
    //发送验证码失败
    public void sendVerifyCodeFailed(){
        closeProgressDialog();
        showAlertDialog();
    }
    //关闭加载弹出框
    public void closeProgressDialog(){
        if(ProcessAlertDialog!=null){
            ProcessAlertDialog.cancel();
        }
    }
    //弹出提示框
    public void showAlertDialog(){
        if(alertDialog == null){
            alertDialog = ProcessBarUtil.showSimpleDialog(this, "提示", "验证码发送失败", "确定", null, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            },null,true);
        }else{
            alertDialog.show();
        }
    }
}
