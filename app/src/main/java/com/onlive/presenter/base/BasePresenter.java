package com.onlive.presenter.base;

import android.app.Activity;

public class BasePresenter <A extends Activity> implements PresenterInterface<A>{
    private A activity;
    public void onAttach(A activity){
        this.activity = activity;
    }
    public A getActivity(){
        return this.activity;
    }
}
