package com.onlive.view.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.onlive.R;
import com.onlive.model.User;
import com.onlive.presenter.register.RegisterPresenter3;
import com.onlive.util.ButtonUtils;
import com.onlive.util.ProcessBarUtil;
import com.onlive.view.login.LoginActivity;

public class RegisterActivity3 extends AppCompatActivity {
    private RegisterPresenter3 registerPresenter;
    private TextInputLayout userNameInputLayout;
    private TextInputEditText userNameEditText;
    private TextInputLayout passInputLayout1;
    private TextInputEditText passEditText1;
    private TextInputLayout passInputLayout2;
    private TextInputEditText passEditText2;
    private String phone;
    private ImageButton rg_back;
    private Button button;
    private AlertDialog processAlertDialog;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        init();
    }
    private void init(){
        rg_back = findViewById(R.id.rg3_back);
        button = findViewById(R.id.rg3_register);
        userNameInputLayout = findViewById(R.id.rg3_user_layout);
        userNameEditText = findViewById(R.id.rg3_userName);
        passInputLayout1 = findViewById(R.id.rg3_pass1_layout);
        passEditText1 = findViewById(R.id.rg3_userPass1);
        passInputLayout2 = findViewById(R.id.rg3_pass2_layout);
        passEditText2 = findViewById(R.id.rg3_userPass2);

        registerPresenter = new RegisterPresenter3();
        registerPresenter.onAttach(this);
        registerPresenter.hideStatusBar();

        processAlertDialog = ProcessBarUtil.showProgressDialog(this,"提示","正在注册...",null,null,null,null,false);
        alertDialog = ProcessBarUtil.showSimpleDialog(this, "提示", null, "确定", null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        },null,true);
        getParam();
        setButton();
        setPassTextInputEditTextListener(passEditText1,1);
        setPassTextInputEditTextListener(passEditText2,2);
        setPassTextInputEditTextListener(userNameEditText,3);
    }

    private void setButton() {
        rg_back.setOnClickListener(v->finish());
        button.setOnClickListener(v->{
            User user = new User();
            user.setUsername(getUserNameInput());
            user.setPassword(passEditText1.getText().toString());
            user.setPhone(phone);
            registerPresenter.userRegister(user);
        });
    }
    public void setButtonStatus(boolean flag){
        ButtonUtils.setLoginButtonClick(button,getResources(),flag);
    }
    //获取从第二个注册界面传递的参数
    public void getParam(){
        Intent intent = getIntent();
        setPhone(intent.getStringExtra("phone"));
    }
    //给PassTextInputEditText设置监听者 flag 1:第一次密码输入 2:第二次密码输入 3:用户名输入
    private void setPassTextInputEditTextListener(TextInputEditText textInputEditText,int flag){
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                switch (flag){
                    case 1:registerPresenter.inputPassFirst(s);break;
                    case 2:registerPresenter.inputPassSecond(s);break;
                    case 3:registerPresenter.inputUserName();break;
                }
            }
        });
    }

    //为TextInputLayout设置错误信息 1：用户名 2：第一次密码 3：第二次密码
    public void setError(String msg,int flag,boolean status){
        TextInputLayout textInputLayout;
        switch (flag){
            case 1:textInputLayout = userNameInputLayout;break;
            case 2:textInputLayout = passInputLayout1;break;
            case 3:textInputLayout = passInputLayout2;break;
            default:textInputLayout = null;
        }
        if(textInputLayout != null){
            if(msg != null) {
                textInputLayout.setError(msg);
            }
            textInputLayout.setErrorEnabled(status);
        }
    }
    public boolean userNameIsEmpty(){
        return TextUtils.isEmpty(getUserNameInput());
    }
    public boolean passEquals(){
        boolean flag = false;
        String pass1 = passEditText1.getText().toString();
        if(pass1!=null){
            flag = pass1.equals(passEditText2.getText().toString());
        }
        return flag;
    }
    public String getUserNameInput(){
        return userNameEditText.getText().toString();
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
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setAlertDialogButtonListener(){
        Button btn = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        btn.setOnClickListener(v->{
            alertDialog.cancel();
            Intent intent = new Intent(RegisterActivity3.this, LoginActivity.class);
            startActivity(intent);
        });
    }
    public void setAlertDialogClickAble(boolean flag){
        alertDialog.setCancelable(flag);
    }
}
