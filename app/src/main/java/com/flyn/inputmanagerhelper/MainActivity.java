package com.flyn.inputmanagerhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_custom_layout = (Button) findViewById(R.id.btn_custom_layout);
        Button btn_custom_layout_with_toolbar = (Button) findViewById(R.id.btn_custom_layout_with_toolbar);
        Button btn_translucent_layout = (Button) findViewById(R.id.btn_translucent_layout);
        Button btn_form_layout = (Button) findViewById(R.id.btn_form_layout);
        Button btn_toolbar = (Button) findViewById(R.id.btn_toolbar);
        Button btn_actionBar = (Button) findViewById(R.id.btn_actionBar);
        Button btn_recycleview = (Button) findViewById(R.id.btn_recycleview);


        btn_custom_layout.setOnClickListener(this);
        btn_custom_layout_with_toolbar.setOnClickListener(this);
        btn_translucent_layout.setOnClickListener(this);
        btn_form_layout.setOnClickListener(this);
        btn_toolbar.setOnClickListener(this);
        btn_actionBar.setOnClickListener(this);
        btn_recycleview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_custom_layout:
                intent.setClass(this, CustomLayoutActivity.class);
                break;
            case R.id.btn_custom_layout_with_toolbar:
                intent.setClass(this, CustomLayoutWithToolBarActivity.class);
                break;
            case R.id.btn_translucent_layout:
                intent.setClass(this, TranslucentLayoutActivity.class);
                break;
            case R.id.btn_toolbar:
                intent.setClass(this, ToolBarActivity.class);
                break;
            case R.id.btn_actionBar:
                intent.setClass(this, ActionBarActivity.class);
                break;
            case R.id.btn_form_layout:
                intent.setClass(this, FormActivity.class);
                break;
            case R.id.btn_recycleview:
                intent.setClass(this, RecyclerViewActivity.class);
                break;
        }
        startActivity(intent);
    }
}
