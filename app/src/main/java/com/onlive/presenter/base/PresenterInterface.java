package com.onlive.presenter.base;


import android.app.Activity;

public interface PresenterInterface <V extends Activity> {
    public void onAttach(V activity);
}
