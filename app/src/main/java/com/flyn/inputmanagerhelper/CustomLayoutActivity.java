package com.flyn.inputmanagerhelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.flyn.inputmanagerhelper.helper.InputManagerHelper;
import com.flyn.inputmanagerhelper.view.KeyboardListenLayout;


public class CustomLayoutActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_custom_layout);
        KeyboardListenLayout keyboardListenLayout = (KeyboardListenLayout) findViewById(R.id.layout_keyboard);
        Button tv_login = (Button) findViewById(R.id.tv_login);

        InputManagerHelper.attachToActivity(this).bindCustomLayout(keyboardListenLayout, tv_login, 16);
    }

}
