package com.flyn.inputmanagerhelper;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;

import com.flyn.inputmanagerhelper.helper.InputManagerHelper;

public class ScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_form);
        ScrollView layout_keyboard = (ScrollView) findViewById(R.id.layout_keyboard);
        InputManagerHelper.attachToActivity(this).bindScrollView(layout_keyboard);
    }


}
