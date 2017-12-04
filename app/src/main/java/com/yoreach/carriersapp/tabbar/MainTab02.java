package com.yoreach.carriersapp.tabbar;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.yoreach.carriersapp.R;
import com.yoreach.carriersapp.component.MyListView;
import com.yoreach.carriersapp.component.MyListView.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;


@SuppressLint("NewApi")
public class MainTab02 extends Fragment implements OnRefreshListener {
    private final int VIEW_TYPE_COUNT = 2;

    private final String DATA = "data";
    private final String TYPE = "type";

    private final int GROUP = -2;
    private final int ITEM = -3;
    private final int BOTTOM = -4;
    private TextView titlename;
    private MyListView listView;
    private BaseAdapter adapter;
    private ArrayList<HashMap<String, Object>> items = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_tab_02, container, false);

        titlename = (TextView) view.findViewById(R.id.titlename);
        titlename.setText("历史任务");

        // 返回按钮
        Button back = (Button) view.findViewById(R.id.backbutton);
        back.setVisibility(view.GONE); //隐藏

//	 		back.setOnClickListener(new BackClickListener());
//		class BackClickListener implements OnClickListener {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				finish();
//			}
//		}

        listView = (MyListView) view.findViewById(R.id.MyListView);
        // ListView listView = this.getListView();
        listView.setDividerHeight(0);
        adapter = new MyBaseAdapter();

        items = new ArrayList<HashMap<String, Object>>();

        listView.setEmptyView(view.findViewById(R.id.layout_empty));
        listView.setAdapter(adapter);

        String[] groups = {"A", "A", "A"};
        int count = 0;
        for (int i = 0; i < groups.length; i++) {
            HashMap<String, Object> group_map = new HashMap<String, Object>();
            group_map.put(TYPE, GROUP);
            group_map.put(DATA, groups[i]);
            items.add(group_map);

        }
        //设置回调
        listView.setOnRefreshListener(this);
        listView.refreshHeaderView();

//		listView.setDoMoreWhenBottom(false);
//		listView.setOnRefreshListener(this);
//		listView.setOnMoreListener(this);

        return view;

    }

    private class MyBaseAdapter extends BaseAdapter {

        private LayoutInflater inflater = null;

        public MyBaseAdapter() {
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            HashMap<String, Object> map = items.get(position);
            return map.get(DATA);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            int type = getItemViewType(position);

            // 根据不同的view type加载不同的布局文件
            switch (type) {
                case GROUP:
                    convertView = inflater.inflate(R.layout.tab02_listchild, null);
                    // TextView tv1 = (TextView) convertView
                    // .findViewById(android.R.id.text1);
                    // tv1.setText("分组");
                    // tv1.setBackgroundColor(Color.RED);
                    // TextView tv2 = (TextView) convertView
                    // .findViewById(android.R.id.text2);
                    // tv2.setText(getItem(position) + "");
                    // tv2.setBackgroundColor(Color.GRAY);
                    break;
            }

            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            HashMap<String, Object> map = items.get(position);
            return (Integer) map.get(TYPE);
        }

        @Override
        public int getViewTypeCount() {
            return VIEW_TYPE_COUNT;
        }
    }

    @Override
    public void onDownPullRefresh() {
        // TODO Auto-generated method stub
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(2000);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
                listView.hideHeaderView();
            }
        }.execute(new Void[]{});
    }

    @Override
    public void onLoadingMore() {
        // TODO Auto-generated method stub
        // 加载更多
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(2000);
                String[] groups = {"B"};
                String[] groups2 = {"B"};
                int count = 0;
                for (int i = 0; i < groups.length; i++) {
                    HashMap<String, Object> group_map = new HashMap<String, Object>();
                    group_map.put(TYPE, GROUP);
                    group_map.put(DATA, groups[i]);
                    items.add(group_map);

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
                listView.hideFooterView();
            }
        }.execute(new Void[]{});


    }

}
