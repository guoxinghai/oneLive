package com.onlive.view.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.onlive.R;
import com.onlive.view.verifycode.VerifyCodeView;

public class RegisterActivity2 extends AppCompatActivity {
    private VerifyCodeView verifyCodeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        init();
        //隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置验证码监听事件
        verifyCodeView.setInputCompleteListener(new VerifyCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                Toast.makeText(RegisterActivity2.this,"complete",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void invalidContent() {
            }
        });
    }
    private void init(){
        verifyCodeView = findViewById(R.id.verify_code_view);
    }
}
