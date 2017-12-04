package com.yoreach.carriersapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.yoreach.carriersapp.component.BarCodeActivity;
import com.yoreach.carriersapp.test.ListTestActivity;
import com.yoreach.carriersapp.component.GaodeMapActivity;
import com.yoreach.carriersapp.tabbar.FragmentMainActivity;
import com.yoreach.carriersapp.test.ViewpagerTestActivity;

public class ZBarDemoActivity extends Activity implements OnClickListener {
    private EditText et_edit;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( com.yoreach.carriersapp.R.layout.main);

        et_edit = (EditText) findViewById(com.yoreach.carriersapp.R.id.editText1);

        findViewById( com.yoreach.carriersapp.R.id.button1).setOnClickListener(this);

    }

    public void listview(View v) {
        Intent intent = new Intent();
        intent.setClass(this, ListTestActivity.class);
        startActivityForResult(intent, 0);

    }

    public void tabbar(View v) {
        Intent intent = new Intent();
        intent.setClass(this, FragmentMainActivity.class);
        startActivityForResult(intent, 0);

    }

    public void viewpager(View v) {
        Intent intent = new Intent();
        intent.setClass(this, ViewpagerTestActivity.class);
        startActivityForResult(intent, 0);

    }

    public void location(View v) {
        Intent intent = new Intent();
        intent.setClass(this, GaodeMapActivity.class);
        startActivityForResult(intent, 0);

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        intent.setClass(this, BarCodeActivity.class);
        startActivityForResult(intent, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String code = data.getStringExtra("Code");
            et_edit.setText(code);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}