package com.onlive.view.login;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.onlive.R;
import com.onlive.presenter.login.LoginPresenter;
import com.onlive.util.RegexUtils;
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
        loginPresenter = new LoginPresenter();
        loginPresenter.onAttach(this);
        //隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置window颜色
        loginPresenter.setStatusBarColor(Color.rgb(25,118,210));
        //获取组件
        init();
        //注册设置监听
        loginPresenter.setRegisterListener();
        //给TextInputEditText设置监听者
        loginPresenter.setTextInputEditTextListener();

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
    }

    public TextInputEditText getLg_user() {
        return lg_user;
    }

    public TextInputEditText getLg_pass() {
        return lg_pass;
    }

    public Button getLg_login() {
        return lg_login;
    }

    public TextView getLg_register() {
        return lg_register;
    }

    public TextInputLayout getLg_user_layout() {
        return lg_user_layout;
    }

    public TextInputLayout getLg_pass_layout() {
        return lg_pass_layout;
    }

    //给TextInputLayout设置错误信息
    public void setError(TextInputLayout textInputLayout,String msg){
        textInputLayout.setError(msg);
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
                if(s!=null&&!RegexUtils.isPhoneNumber(s.toString())){
                    lg_user_layout.setError("手机号格式错误");
                    lg_user_layout.setErrorEnabled(true);
                    setLoginButtonClick(false);
                }else{
                    lg_user_layout.setErrorEnabled(false);
                    if(!(lg_user_layout.isErrorEnabled()||lg_pass_layout.isErrorEnabled())){
                        setLoginButtonClick(true);
                    }
                }
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
                if(s == null){
                    lg_pass_layout.setError("密码不能为空!");
                    lg_pass_layout.setErrorEnabled(true);
                    setLoginButtonClick(false);
                }else if(!RegexUtils.isPassNumber(s.toString())){
                    lg_pass_layout.setError("密码格式错误(6-20位同时包含数字和字母)");
                    lg_pass_layout.setErrorEnabled(true);
                    setLoginButtonClick(false);
                }else{
                    lg_pass_layout.setErrorEnabled(false);
                    if(!(lg_user_layout.isErrorEnabled()||lg_pass_layout.isErrorEnabled())){
                        setLoginButtonClick(true);
                    }
                }
            }
        });
    }
    //设置login按钮的可点击性
    private void setLoginButtonClick(boolean flag){
        if(flag){
            lg_login.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            lg_login.setClickable(true);
        }else{
            lg_login.setBackgroundColor(getResources().getColor(R.color.gray));
            lg_login.setClickable(false);
        }
    }
}
