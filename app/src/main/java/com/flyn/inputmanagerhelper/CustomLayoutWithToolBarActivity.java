package com.flyn.inputmanagerhelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.flyn.inputmanagerhelper.helper.InputManagerHelper;
import com.flyn.inputmanagerhelper.view.KeyboardListenLayout;

public class CustomLayoutWithToolBarActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_layout_with_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        KeyboardListenLayout keyboardListenLayout = (KeyboardListenLayout) findViewById(R.id.layout_keyboard);
        Button tv_login = (Button) findViewById(R.id.tv_login);

        InputManagerHelper.attachToActivity(this).bind(keyboardListenLayout, tv_login).offset(16);
    }

}
