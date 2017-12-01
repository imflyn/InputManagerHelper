package com.flyn.inputmanagerhelper;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Button;

import com.flyn.inputmanagerhelper.helper.InputManagerHelper;

public class TranslucentLayoutActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translucent_layout);
        ViewGroup layout_keyboard = findViewById(R.id.layout_keyboard);
        Button tv_login = findViewById(R.id.tv_login);

        InputManagerHelper.attachToActivity(this).bind(layout_keyboard, tv_login).offset(-dip2px(25) + 16);
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
