package com.onlive.view.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.onlive.R;
import com.onlive.presenter.register.RegisterPresenter2;
import com.onlive.util.ButtonUtils;
import com.onlive.util.ProcessBarUtil;
import com.onlive.view.verifycode.VerifyCodeView;

public class RegisterActivity2 extends AppCompatActivity {
    private VerifyCodeView verifyCodeView;
    private RegisterPresenter2 registerPresenter;
    private String verifyCode;//存取发送给用户的验证码
    private String phone;//存取用户注册的手机号
    private ImageButton rg_back;
    private Button button;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        init();

    }
    private void init(){
        verifyCodeView = findViewById(R.id.verify_code_view);
        button = findViewById(R.id.rg2_login);
        rg_back = findViewById(R.id.rg2_back);
        registerPresenter = new RegisterPresenter2();
        alertDialog = ProcessBarUtil.showSimpleDialog(this, "提示", null, "确定", null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        },null,true);
        registerPresenter.onAttach(this);
        registerPresenter.hideStatusBar();
        //为verifyCodeView设置监听
        setVerifyCodeViewListener();
        //获取验证码和用户注册手机号
        getParam();
        //初始化按钮
        setButton();
    }
    private void setVerifyCodeViewListener(){
        verifyCodeView.setInputCompleteListener(new VerifyCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                registerPresenter.onVerifyCodeInput(verifyCodeView.getEditContent());
            }

            @Override
            public void invalidContent() {
                setButtonStatus(false);
            }
        });
    }
    //获取从第一个注册界面传递的参数
    public void getParam(){
        Intent intent = getIntent();
        setVerifyCode(intent.getStringExtra("code"));
        setPhone(intent.getStringExtra("phone"));
    }
    //弹出提示框
    public void showAlertDialog(String msg){
        alertDialog.setMessage(msg);
        alertDialog.show();
    }
    //验证码输入正确
    public void inputVerifyCodeCorrect(){
        setButtonStatus(true);
    }
    //初始化按钮
    private void setButton(){
        rg_back.setOnClickListener(v->finish());
        button.setOnClickListener(v->{
            Intent intent = new Intent(this,RegisterActivity3.class);
            intent.putExtra("phone",phone);
            startActivity(intent);
        });
        setButtonStatus(false);
    }
    public void setButtonStatus(boolean flag){
        ButtonUtils.setLoginButtonClick(button,getResources(),flag);
    }
    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
