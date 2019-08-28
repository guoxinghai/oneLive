package com.onlive.view.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;

import com.onlive.R;
import com.onlive.model.User;
import com.onlive.presenter.login.LoginPresenter;
import com.onlive.util.ButtonUtils;
import com.onlive.util.ProcessBarUtil;
import com.onlive.view.home.HomeActivity;
import com.onlive.view.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {
    private LoginPresenter loginPresenter;
    private TextInputLayout lg_user_layout;
    private TextInputEditText lg_user;
    private TextInputLayout lg_pass_layout;
    private TextInputEditText lg_pass;
    private Button lg_login;
    private TextView lg_register;
    private AlertDialog processAlertDialog;
    private AlertDialog alertDialog;
    private Bundle register_msg;//用来保存注册活动的信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取组件设置监听者
        init();


    }
    public void setButtonListener(){
        //为注册设置监听
        lg_register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            intent.putExtra("register_msg",register_msg);
            startActivityForResult(intent,1);
        });
        lg_login.setOnClickListener(v->{
            User user = new User();
            user.setPhone(lg_user.getText().toString());
            user.setPassword(lg_pass.getText().toString());
            loginPresenter.userLogin(user);
        });
    }
    public void init(){
        lg_user = findViewById(R.id.lg_user);
        lg_pass = findViewById(R.id.lg_pass);
        lg_login = findViewById(R.id.lg_login);
        lg_register = findViewById(R.id.lg_register);
        lg_user_layout = findViewById(R.id.lg_user_layout);
        lg_pass_layout = findViewById(R.id.lg_pass_layout);
        processAlertDialog = ProcessBarUtil.showProgressDialog(this,"提示","正在登录...",null,null,null,null,false);
        alertDialog = ProcessBarUtil.showSimpleDialog(this, "提示", null, "确定", null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        },null,true);
        loginPresenter = new LoginPresenter();
        register_msg = new Bundle();
        //与绑定loginPresenter绑定
        loginPresenter.onAttach(this);
        //隐藏状态栏
        loginPresenter.hideStatusBar();
        //给TextInputEditText设置监听者
        setUserTextInputEditTextListener();
        setPassTextInputEditTextListener();
        //给按钮设置监听者
        setButtonListener();
        setButtonStatus(false);
    }
    //给UserTextInputEditText设置监听者
    private void setUserTextInputEditTextListener(){
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
    private void setPassTextInputEditTextListener(){
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
    //接收注册界面返回的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        loginPresenter.onActivityResult(requestCode,resultCode,data);
    }
    //向register_msg中放入数据
    public void putDataForBundle(String name,String value){
        register_msg.putString(name,value);
    }
    public void putDataForBundle(String name,long value){
        register_msg.putLong(name,value);
    }
    //弹出提示框
    public void showAlertDialog(final String msg){
        runOnUiThread(() -> {
            alertDialog.setMessage(msg);
            alertDialog.show();
        });
    }

    //弹出加载弹出框
    public void showProgressDialog(){
        runOnUiThread(()-> processAlertDialog.show());
    }
    //关闭加载弹出框
    public void closeProgressDialog(){
        runOnUiThread(()-> processAlertDialog.cancel());
    }

    //启动主界面
    public void launchHomeActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("phone",lg_user.getText().toString());
        intent.putExtra("password",lg_pass.getText().toString());
        startActivity(intent);
    }
}
