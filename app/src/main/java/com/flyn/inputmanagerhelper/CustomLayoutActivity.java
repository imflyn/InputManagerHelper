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
        getSupportActionBar().setTitle("This is actionbar");
        KeyboardListenLayout keyboardListenLayout = findViewById(R.id.layout_keyboard);
        Button tv_login = findViewById(R.id.tv_login);

        InputManagerHelper.attachToActivity(this).bind(keyboardListenLayout, tv_login).offset(16);
    }

}
