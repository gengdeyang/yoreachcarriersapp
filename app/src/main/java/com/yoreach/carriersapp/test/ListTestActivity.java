/*
 * Basic no frills app which integrates the ZBar barcode scanner with
 * the camera.
 * 
 * Created by lisah0 on 2012-02-24
 */
package com.yoreach.carriersapp.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.yoreach.carriersapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ListTestActivity extends Activity {

    // item view的类型总数。
    private final int VIEW_TYPE_COUNT = 2;

    private final String DATA = "data";
    private final String TYPE = "type";

    private final int GROUP = -2;
    private final int ITEM = -3;

    private ArrayList<HashMap<String, Object>> items = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mylistview);
        ListView listView = (ListView) findViewById(R.id.MyListView);
//        ListView listView = this.getListView();
        listView.setDividerHeight(0);
        BaseAdapter adapter = new MyBaseAdapter();

        items = new ArrayList<HashMap<String, Object>>();

        listView.setAdapter(adapter);

        // 假设我们演示以A,B,C,,,这样的字符串作为分组的标签。
        // 每一组装载5个子数据。
        String[] groups = {"A"};
        int count = 0;
        for (int i = 0; i < groups.length; i++) {
            HashMap<String, Object> group_map = new HashMap<String, Object>();
            group_map.put(TYPE, GROUP);
            group_map.put(DATA, groups[i]);
            items.add(group_map);

            for (int j = 0; j < 2; j++) {
                HashMap<String, Object> data_map = new HashMap<String, Object>();
                data_map.put(TYPE, ITEM);
                data_map.put(DATA, "数据:" + (count++));
                items.add(data_map);
            }
        }
    }

    private class MyBaseAdapter extends BaseAdapter {

        private LayoutInflater inflater = null;

        public MyBaseAdapter() {
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        // 返回的id可以自己定制。
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            int type = getItemViewType(position);

            // 根据不同的view type加载不同的布局文件。
            switch (type) {
                case GROUP:
                    convertView = inflater.inflate(R.layout.listchild01, null);
//                TextView tv1 = (TextView) convertView
//                        .findViewById(android.R.id.text1);
//                tv1.setText("分组");
//                tv1.setBackgroundColor(Color.RED);
//                TextView tv2 = (TextView) convertView
//                        .findViewById(android.R.id.text2);
//                tv2.setText(getItem(position) + "");
//                tv2.setBackgroundColor(Color.GRAY);
                    break;
                case ITEM:
                    convertView = inflater.inflate(R.layout.listchild02, null);
//                TextView tv = (TextView) convertView
//                        .findViewById(android.R.id.text1);
//                tv.setText(getItem(position) + "");
                    break;
            }

            return convertView;
        }

        // 解析view type。
        // view type值是我们事先埋入到items数据集中的字段值。
        // 注意!此处返回的值不要大于getViewTypeCount()的返回值。
        @Override
        public int getItemViewType(int position) {
            HashMap<String, Object> map = items.get(position);
            return (Integer) map.get(TYPE);
        }

        // 在本例中共计有2个不同类型的view
        // android.R.layout.simple_list_item_1 和
        // android.R.layout.simple_list_item_1.  
        @Override
        public int getViewTypeCount() {
            return VIEW_TYPE_COUNT;
        }
    }
}  