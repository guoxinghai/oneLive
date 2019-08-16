package com.onlive.view.login;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.onlive.R;
import com.onlive.presenter.login.LoginPresenter;
import com.onlive.util.ButtonUtils;
import com.onlive.view.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {
    private LoginPresenter loginPresenter;
    private TextInputLayout lg_user_layout;
    private TextInputEditText lg_user;
    private TextInputLayout lg_pass_layout;
    private TextInputEditText lg_pass;
    private Button lg_login;
    private TextView lg_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取组件设置监听者
        init();


    }
    public void setRegisterListener(){
        lg_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    public void init(){
        lg_user = findViewById(R.id.lg_user);
        lg_pass = findViewById(R.id.lg_pass);
        lg_login = findViewById(R.id.lg_login);
        lg_register = findViewById(R.id.lg_register);
        lg_user_layout = findViewById(R.id.lg_user_layout);
        lg_pass_layout = findViewById(R.id.lg_pass_layout);
        loginPresenter = new LoginPresenter();
        //与绑定loginPresenter绑定
        loginPresenter.onAttach(this);
        //隐藏状态栏
        loginPresenter.hideStatusBar();
        //给TextInputEditText设置监听者
        setUserTextInputEditTextListener();
        setPassTextInputEditTextListener();
        //给注册设置监听者
        setRegisterListener();
    }
    //给UserTextInputEditText设置监听者
    public void setUserTextInputEditTextListener(){
        lg_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                loginPresenter.onUserTextChange(s);
            }
        });
    }
    //给PassTextInputEditText设置监听者
    public void setPassTextInputEditTextListener(){
        lg_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                loginPresenter.onPassTextChange(s);
            }
        });
    }
    //用户手机号输入错误
    public void userInputPhoneError(){
        lg_user_layout.setError("手机号格式错误");
        lg_user_layout.setErrorEnabled(true);
        setButtonStatus(false);
    }
    //用户密码输入错误
    public void userInputPassError(){
        lg_pass_layout.setError("密码格式错误(6-20位同时包含数字和字母)");
        lg_pass_layout.setErrorEnabled(true);
        setButtonStatus(false);
    }
    //用户手机号输入正确
    public void userInputPhoneCorrect(){
        lg_user_layout.setErrorEnabled(false);
    }
    //用户密码输入正确
    public void userInputPassCorrect(){
        lg_pass_layout.setErrorEnabled(false);
    }
    //获取用户输入TextInputLayout是否可以设置error信息
    public boolean getTextInputLayoutErrorAble(){
        return lg_user_layout.isErrorEnabled()||lg_pass_layout.isErrorEnabled();
    }
    //设置按钮状态
    public void setButtonStatus(boolean flag){
        ButtonUtils.setLoginButtonClick(lg_login,getResources(),flag);
    }

}
