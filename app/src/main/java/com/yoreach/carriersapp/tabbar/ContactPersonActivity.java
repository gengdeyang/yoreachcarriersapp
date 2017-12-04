/*
 * Basic no frills app which integrates the ZBar barcode scanner with
 * the camera.
 * 
 * Created by lisah0 on 2012-02-24
 */
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
import android.widget.TextView;

import com.yoreach.carriersapp.component.MyListView;
import com.yoreach.carriersapp.R;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressLint("NewApi")
public class ContactPersonActivity extends Fragment implements MyListView.OnRefreshListener {

    // item view的类型
    private final int VIEW_TYPE_COUNT = 2;

    private final String DATA = "data";
    private final String TYPE = "type";

    private final int GROUP = -2;
    private final int ITEM = -3;
    private final int BOTTOM = -4;
    private TextView titlename;
    private BaseAdapter adapter;
    private MyListView listView;

    private ArrayList<HashMap<String, Object>> items = null;
//	Handler handler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			if (msg.what == 0) {
//				adapter.notifyDataSetChanged();
//				listView.doneRefresh();
//				Toast.makeText(getActivity(), "新加" + msg.arg1 + "条数?!",
//						Toast.LENGTH_LONG).show();
//			} else if (msg.what == 1) {
//				adapter.notifyDataSetChanged();
//				listView.doneMore();
//			} else {
//				super.handleMessage(msg);
//			}
//		}
//	};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mylistview, container, false);

        listView = (MyListView) view.findViewById(R.id.MyListView);
        // ListView listView = this.getListView();
        listView.setDividerHeight(0);
        adapter = new MyBaseAdapter();

        items = new ArrayList<HashMap<String, Object>>();

        listView.setEmptyView(view.findViewById(R.id.layout_empty));
        listView.setAdapter(adapter);

        String[] groups = {"A", "A"};
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
                    convertView = inflater.inflate(R.layout.msg_listchild02, null);
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