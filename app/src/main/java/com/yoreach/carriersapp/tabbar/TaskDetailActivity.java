package com.yoreach.carriersapp.tabbar;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yoreach.carriersapp.R;

public class TaskDetailActivity extends Activity implements OnClickListener {

    private LinearLayout mTabBtnWeixin;
    private LinearLayout mTabBtnFrd;
    private LinearLayout mTabBtnAddress;
    private RelativeLayout mTabBtnSettings;
    private LinearLayout task_detail_child;
    private LinearLayout yundanlist;
    private TextView titlename;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskdetail_main);

        titlename = (TextView) findViewById(R.id.titlename);
        titlename.setText("任务详情");
        // 返回按钮
        Button back = (Button) findViewById(R.id.backbutton);
        back.setOnClickListener(new BackClickListener());

        initViews();
    }

    private void initViews() {

        mTabBtnWeixin = (LinearLayout) findViewById(R.id.detal_main_01);
        mTabBtnFrd = (LinearLayout) findViewById(R.id.detal_main_02);
        mTabBtnAddress = (LinearLayout) findViewById(R.id.detal_main_03);

        View taskdetailchildv = getLayoutInflater().inflate(     //运单信息
                R.layout.taskdetail_child, null);

        mTabBtnFrd.addView(taskdetailchildv);

        yundanlist = (LinearLayout) findViewById(R.id.task_detail_yundanlist_linelayout);


        for (int z = 0; z < 10; z++) {
            View taskdetail_child02 = getLayoutInflater().inflate(       //车辆信息
                    R.layout.taskdetail_child02, null);
            yundanlist.addView(taskdetail_child02);
        }


        mTabBtnSettings = (RelativeLayout) findViewById(R.id.detal_main_03_03); // 随车备品

        mTabBtnWeixin.setOnClickListener(this);
        mTabBtnFrd.setOnClickListener(this);
        mTabBtnAddress.setOnClickListener(this);
        mTabBtnSettings.setOnClickListener(this);
    }

    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    /**
     * 返回
     *
     * @author Austriee
     */
    class BackClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            finish();
        }
    }

}
