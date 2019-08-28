package com.onlive.view.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageButton;


import com.onlive.R;
import com.onlive.presenter.register.RegisterPresenter;
import com.onlive.util.ButtonUtils;
import com.onlive.util.ProcessBarUtil;
import com.onlive.util.TimeUtil;
import com.onlive.view.button.CountDownButton;

public class RegisterActivity extends AppCompatActivity {
    private int verifyCode = 6;
    private int sentInterval = 60;
    private CountDownButton button;
    private ImageButton back_button;
    private RegisterPresenter registerPresenter;
    private TextInputEditText rg_user;
    private TextInputLayout rg_user_layout;
    private AlertDialog processAlertDialog;
    private AlertDialog alertDialog;
    private long sent_time=-1;//保存最近一次发送验证码的时间
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化操作
        init();
        //查看上次活动保存的必要信息
        if(savedInstanceState != null){
            setMessage(savedInstanceState);
        }else{
            setMessage(getIntent().getBundleExtra("register_msg"));
        }
    }
    private void init(){
        button = findViewById(R.id.rg_register);
        button.setResource(getResources());
        back_button = findViewById(R.id.rg_back);
        rg_user = findViewById(R.id.rg_user);
        rg_user_layout = findViewById(R.id.rg_user_layout);
        registerPresenter = new RegisterPresenter();
        processAlertDialog = ProcessBarUtil.showProgressDialog(this,"提示","正在发送验证码...",null,null,null,null,false);
        alertDialog = ProcessBarUtil.showSimpleDialog(this, "提示", null, "确定", null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        },null,true);
        //与registerPresenter绑定
        registerPresenter.onAttach(this);
        //隐藏状态栏
        registerPresenter.hideStatusBar();
        //为lg_user设置监听者
        registerPresenter.setUserTextInputEditTextListener();
        //为button设置监听者
        setButtonListener();
        //设置button样式
        setButtonStatus(false);
        //为button添加callback
        button.setCallback(()->{
            if(!rg_user_layout.isErrorEnabled()){
                ButtonUtils.setLoginButtonClick(button,getResources(),true);
            }
        });

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
    }
    //设置按钮状态
    public void setButtonStatus(boolean flag){
        ButtonUtils.setLoginButtonClick(button,getResources(),flag);
    }
    //为button设置监听者
    private void setButtonListener(){
        //发送验证码Button设置监听者
        button.setOnClickListener(v -> {
            showProgressDialog();
            registerPresenter.queryPhoneNumber(rg_user.getText().toString(),verifyCode);
        });
        //为backButton设置监听者
        back_button.setOnClickListener(v-> onBackPressed());
    }
    //开启button倒计时
    public void startCountDown(){
        //button.setCountDownTime(sentInterval);
        button.startCountDown();
    }
    //查询按钮是否正在倒计时
    public boolean isCountDown(){
        return button.isCountDown();
    }
    //开启加载弹出框
    public void showProgressDialog(){
        runOnUiThread(()-> processAlertDialog.show());

    }
    //发送验证码成功
    public void sendVerifyCodeSuccess(String code){
                Intent intent = new Intent(RegisterActivity.this,RegisterActivity2.class);
//                intent.putExtra("code",code);
                intent.putExtra("code","666666");
                intent.putExtra("phone",rg_user.getText().toString());
                closeProgressDialog();
                startActivity(intent);
    }
    //发送验证码失败
    public void sendVerifyCodeFailed(){
        closeProgressDialog();
        showAlertDialog("验证码发送失败");
    }
    //验证码发送超过次数
    public void sendVerifyCodeTimesExceeding(){
        closeProgressDialog();
        showAlertDialog("该手机号今日发送超过5次！发送失败！");
    }
    //关闭加载弹出框
    public void closeProgressDialog(){
        runOnUiThread(()-> processAlertDialog.cancel());
    }
    //弹出提示框
    public void showAlertDialog(final String msg){
        runOnUiThread(() -> {
            alertDialog.setMessage(msg);
            alertDialog.show();
        });
    }
    //设置最近一次发送验证码的时间
    public void setSent_time(long time){
        this.sent_time = time;
    }

    @Override//活动被回收时保存必要信息
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("phone",rg_user.getText().toString());
        outState.putLong("sentTime",sent_time);
    }

    private void setMessage(Bundle bundle){
        String phone = bundle.getString("phone",null);
        long last_sentTime = bundle.getLong("sentTime",System.currentTimeMillis());
        int countdown =sentInterval-TimeUtil.getTimeDifference(last_sentTime,System.currentTimeMillis());

        if(phone != null){
            rg_user.setText(phone);
        }
        if(countdown>0&&countdown<sentInterval){
            button.setCountDownTime(countdown);
            button.startCountDown();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("phone",rg_user.getText().toString());
        intent.putExtra("sentTime",sent_time);
        setResult(RESULT_OK,intent);
        finish();
    }

}
