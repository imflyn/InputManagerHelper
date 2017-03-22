package com.flyn.inputmanagerhelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Button;

import com.flyn.inputmanagerhelper.helper.InputManagerHelper;

public class ToolBarActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button tv_login = (Button) findViewById(R.id.tv_login);

        InputManagerHelper inputManagerHelper = InputManagerHelper.attachToActivity(this);
        inputManagerHelper.bindLayout((ViewGroup) findViewById(R.id.layout_keyboard), tv_login, 0);
    }

}
