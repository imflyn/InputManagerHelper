package com.flyn.inputmanagerhelper;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;

import com.flyn.inputmanagerhelper.helper.InputManagerHelper;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_activity);
        ScrollView layout_keyboard = (ScrollView) findViewById(R.id.layout_keyboard);
        InputManagerHelper inputManagerHelper = InputManagerHelper.attachToActivity(this);
        inputManagerHelper.bindScrollView(layout_keyboard);
    }


}
