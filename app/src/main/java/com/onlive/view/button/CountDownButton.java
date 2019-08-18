package com.onlive.view.button;

import android.content.Context;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.onlive.callback.CountDownCallback;
import com.onlive.util.ButtonUtils;

public class CountDownButton extends AppCompatButton {
    private String text;
    private String baseText;
    private int countDownTime = 60;
    private CountDownTimer timer;
    private String hintText = "%d秒后重新获取";
    private Boolean isCountDown=false;//标志着按钮是否正在倒计时
    private Resources r;//用来设置button样式属性
    private CountDownCallback callback;
    public CountDownButton(Context context) {
        super(context);
    }

    public CountDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        baseText = getText().toString();
    }

    public CountDownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setResource(Resources r){
        this.r = r;
    }
    @Override
    public void setText(CharSequence text, BufferType type) {
        this.text = text.toString();
        super.setText(text, type);
    }
    public void setHintText (String hint){
        this.hintText = hint;
    }
    private void setHintTextCount(int count){
        String hint = String.format(this.hintText,count);
        setText(hint);
    }
    public void setCountDownTime(int time){
        this.countDownTime = time;
        timer = getTimer();
    }
    private CountDownTimer getTimer(){
        return new CountDownTimer(countDownTime*1000+500,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(isClickable()){
                    ButtonUtils.setLoginButtonClick(CountDownButton.this,r,false);
                }
                setHintTextCount((int)millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                setText(baseText);
                isCountDown = false;
                if(callback!=null){
                    callback.onFinish();
                }
                cancel();
                if(countDownTime != 60){
                    countDownTime = 60;
                    timer = getTimer();
                }
            }
        };
    }
    public void setCallback(CountDownCallback callback){
        this.callback = callback;
    }
    public void startCountDown(){
        isCountDown = true;
        baseText = getText().toString();
        if(timer == null){
            timer = getTimer();
        }
        timer.start();
    }
    public void onCancel(){
        if(timer != null){
            this.timer = null;
        }
    }
    public boolean isCountDown(){
        return this.isCountDown;
    }
}
