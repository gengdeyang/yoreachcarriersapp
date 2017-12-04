package com.yoreach.carriersapp.tabbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.yoreach.carriersapp.R;

public class FragmentMainActivity extends Activity implements OnClickListener {
    private MainTab02 mTab02;
    private MainTab01 mTab01;
    private MainTab03 mTab03;
    private MainTab04 mTab04;

    private LinearLayout mTabBtnWeixin;
    private LinearLayout mTabBtnFrd;
    private LinearLayout mTabBtnAddress;
    private LinearLayout mTabBtnSettings;
    private FragmentManager fragmentManager;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        initViews();
        fragmentManager = getFragmentManager();
        setTabSelection(0);
    }


    private void initViews() {

        mTabBtnWeixin = (LinearLayout) findViewById(R.id.id_tab_bottom_weixin);
        mTabBtnFrd = (LinearLayout) findViewById(R.id.id_tab_bottom_friend);
        mTabBtnAddress = (LinearLayout) findViewById(R.id.id_tab_bottom_contact);
        mTabBtnSettings = (LinearLayout) findViewById(R.id.id_tab_bottom_setting);

        mTabBtnWeixin.setOnClickListener(this);
        mTabBtnFrd.setOnClickListener(this);
        mTabBtnAddress.setOnClickListener(this);
        mTabBtnSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tab_bottom_weixin:
                setTabSelection(0);
                break;
            case R.id.id_tab_bottom_friend:
                setTabSelection(1);
                break;
            case R.id.id_tab_bottom_contact:
                setTabSelection(2);
                break;
            case R.id.id_tab_bottom_setting:
                setTabSelection(3);
                break;

            default:
                break;
        }
    }

    @SuppressLint("NewApi")
    private void setTabSelection(int index) {
        resetBtn();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                ((ImageButton) mTabBtnWeixin.findViewById(R.id.btn_tab_bottom_weixin)).setImageResource(R.drawable.tab_weixin_pressed);
                if (mTab01 == null) {
                    mTab01 = new MainTab01();
                    transaction.add(R.id.id_content, mTab01);
                } else {
                    transaction.show(mTab01);
                }
                break;
            case 1:
                ((ImageButton) mTabBtnFrd.findViewById(R.id.btn_tab_bottom_friend)).setImageResource(R.drawable.tab_find_frd_pressed);
                if (mTab02 == null) {
                    mTab02 = new MainTab02();
                    transaction.add(R.id.id_content, mTab02);
                } else {
                    transaction.show(mTab02);
                }
                break;
            case 2:
                ((ImageButton) mTabBtnAddress.findViewById(R.id.btn_tab_bottom_contact)).setImageResource(R.drawable.tab_address_pressed);
                if (mTab03 == null) {
                    mTab03 = new MainTab03();
                    transaction.add(R.id.id_content, mTab03);
                } else {
                    transaction.show(mTab03);
                }
                break;
            case 3:
                ((ImageButton) mTabBtnSettings.findViewById(R.id.btn_tab_bottom_setting)).setImageResource(R.drawable.tab_settings_pressed);
                if (mTab04 == null) {
                    mTab04 = new MainTab04();
                    transaction.add(R.id.id_content, mTab04);
                } else {
                    transaction.show(mTab04);
                }
                break;
        }
        transaction.commit();
    }

    private void resetBtn() {
        ((ImageButton) mTabBtnWeixin.findViewById(R.id.btn_tab_bottom_weixin)).setImageResource(R.drawable.tab_weixin_normal);
        ((ImageButton) mTabBtnFrd.findViewById(R.id.btn_tab_bottom_friend)).setImageResource(R.drawable.tab_find_frd_normal);
        ((ImageButton) mTabBtnAddress.findViewById(R.id.btn_tab_bottom_contact)).setImageResource(R.drawable.tab_address_normal);
        ((ImageButton) mTabBtnSettings.findViewById(R.id.btn_tab_bottom_setting)).setImageResource(R.drawable.tab_settings_normal);
    }

    @SuppressLint("NewApi")
    private void hideFragments(FragmentTransaction transaction) {
        if (mTab01 != null) {
            transaction.hide(mTab01);
        }
        if (mTab02 != null) {
            transaction.hide(mTab02);
        }
        if (mTab03 != null) {
            transaction.hide(mTab03);
        }
        if (mTab04 != null) {
            transaction.hide(mTab04);
        }

    }
//    private long firstTime = 0;
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
//            if (System.currentTimeMillis()-firstTime>2000){
//                Toast.makeText(this,"再按一次退出应用",Toast.LENGTH_SHORT).show();
//                firstTime=System.currentTimeMillis();
//            }else{
//                finish();
//                System.exit(0);
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
