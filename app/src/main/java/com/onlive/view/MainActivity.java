package com.onlive.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.onlive.R;
import com.onlive.view.home.HomeActivity;
import com.onlive.view.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean status = sharedPreferences.getBoolean("status",false);
        Intent intent;
        if(status){
            intent = new Intent(this,HomeActivity.class);
            intent.putExtra("phone",sharedPreferences.getString("phone",null));
            intent.putExtra("password",sharedPreferences.getString("password",null));
        }else{
            intent = new Intent(this,LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
