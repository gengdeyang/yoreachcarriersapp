package com.yoreach.carriersapp.tabbar;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.yoreach.carriersapp.R;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class MainTab03 extends Fragment {

    private ViewPager mPager;
    private List<View> listViews;
    private ImageView cursor;
    private TextView t1, t2, t3, titlename;
    private int offset = 0;
    private int currIndex = 0;
    private int bmpW;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_tab_03, container, false);

        titlename = (TextView) view.findViewById(R.id.titlename);
        titlename.setText("消息");

        // 返回按钮
        Button back = (Button) view.findViewById(R.id.backbutton);
        back.setVisibility(view.GONE); // 隐藏

        t1 = (TextView) view.findViewById(R.id.tab03_text01);
        t2 = (TextView) view.findViewById(R.id.tab03_text02);

        t1.setOnClickListener(new MyOnClickListener(0));
        t2.setOnClickListener(new MyOnClickListener(1));

        cursor = (ImageView) view.findViewById(R.id.tab03_cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.redline2).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (screenW / 2 - bmpW) / 2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);

        mPager = (ViewPager) view.findViewById(R.id.tab03_vPager);
        listViews = new ArrayList<View>();
        listViews.add(inflater.inflate(R.layout.tab03_viewpager_01, null));
        listViews.add(inflater.inflate(R.layout.tab03_viewpager_02, null));
        mPager.setAdapter(new MyPagerAdapter(listViews));
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());

        return view;

    }

    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    }

    ;

    public class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mListViews.get(arg1));
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
            return mListViews.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {

        int one = offset * 2 + bmpW;
        int two = one * 2;

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, one, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, two, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);// True
            animation.setDuration(300);
            cursor.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
}
