/*
 * Basic no frills app which integrates the ZBar barcode scanner with
 * the camera.
 * 
 * Created by lisah0 on 2012-02-24
 */
package com.yoreach.carriersapp.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.*;
import com.yoreach.carriersapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huiyi on 2016/7/18.
 */
public class MyListView extends ListView implements AbsListView.OnScrollListener {
    private static final String TAG = "MyListView";
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Date mDate;
    private SimpleDateFormat mSimpleDateFormat;

    /**
     * 定义头布局对象和高度
     */
    private int firstVisibleItemPosition;//屏幕显示子啊第一个的item的索引  
    private int downY;//按下屏幕时y轴的偏移量  
    private int headerViewHeight;//头布局的高度(通过measureHeight测量获得)  
    public View headerView;//头布局的对象  

    /**
     * 下拉刷新相应的状态
     */
    private final int DOWN_PULL_REFRESH = 0;//下拉刷新状态
    private final int RELEASE_REFRESH = 1;//松开刷新
    private final int REFRESHING = 2;//正在刷新状态
    private int currentState_header = REFRESHING;//头布局的状态，默认为下拉刷新状态

    /**
     * 下拉刷新时的动画变量
     */
    private Animation upAnimation;//向上旋转的动画  
    private Animation downAnimation;//向下旋转的动画  

    //下拉刷新时显示的子控件  
    private ImageView ivArrow;//头布局的显示箭头  
    private ProgressBar mProgressBar;//头布局的进度条  
    private TextView tvState;//头布局的状态  
    private TextView tvLastUpdateTime;//头布局的最后更新时间  

    /**
     * 下拉刷新监听接口
     */
    private OnRefreshListener mOnRefreshListener;

    /**
     * 脚布局对象和动作判定
     */
    private boolean isScrollToBottom;//是否滑动到底部  
    private View footerView;//脚布局的对象  
    private int footerViewHeight;//脚布局的高度  
    private boolean isLoadingMore = false;//是否正在加载更多中


    /**
     * 构造函数初始化
     *
     * @param context
     * @param attrs
     */
    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        initHeaderView();
        initFooterView();
        this.setOnScrollListener(this);
    }

    /**
     * 初始化头布局
     */
    private void initHeaderView() {
        headerView = mLayoutInflater.inflate(R.layout.header_layout, null);
        //箭头  
        ivArrow = (ImageView) headerView.findViewById(R.id.iv_listView_header_arrow);
        //进度条  
        mProgressBar = (ProgressBar) headerView.findViewById(R.id.pb_listView_header);
        //文本状态  
        tvState = (TextView) headerView.findViewById(R.id.tv_listView_header_state);
        //最后更新时间  
        tvLastUpdateTime = (TextView) headerView.findViewById(R.id.tv_listView_header_last_update_time);

        //设置最后刷新时间  
        tvLastUpdateTime.setText("刷新时间：" + getLastUpdateTime());
        //系统测量出headerView的高度  
        //指定measure的参数都为0时，系统便不会按这个规格去设置，而是根据实际来测量  
        headerView.measure(0, 0);
        //头布局的高度为负值（在屏幕top的上方）  
        headerViewHeight = headerView.getMeasuredHeight();
        //设置headerView的内边距(移到屏幕上方，隐藏掉)  
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        //设置成此效果是一样的headerView.setPadding(0, 0, 0, -headerViewHeight);（距离在内部为正，外面看不见的为负）  
        //将headerView添加到listView中，布局以xml为准  
        this.addHeaderView(headerView);
        //初始化动画  
        initAnimation();
    }


    /**
     * 最后刷新时间
     */
    private String getLastUpdateTime() {
        mDate = new Date();
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return mSimpleDateFormat.format(mDate);
    }


    /**
     * 初始化动画
     */
    private void initAnimation() {
        //松手动画  
        upAnimation = new RotateAnimation(0f, -180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //持续500ms  
        upAnimation.setDuration(500);
        //动画结束后，保留状态  
        upAnimation.setFillAfter(true);

        //下拉动画  
        downAnimation = new RotateAnimation(-180f, -360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //downAnimation.setInterpolator(new LinearInterpolator());  
        downAnimation.setDuration(500);
        downAnimation.setFillAfter(true);
    }

    /**
     * 触摸监听事件(处理下拉刷新事件)
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN://按下  
                //记录按下时的坐标，与移动的坐标进行比较  
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE://移动  
                int moveY = (int) ev.getY();
                //间距=移动的点-按下的点  
                int diff = (moveY - downY) / 2;
                //paddingTop=-头布局的高度+间距  
                int paddingTop = -headerViewHeight + diff;
                //只有当firstVisibleItem===0时，才进行相应的刷新准备  
                //同时要想刷新，必须首先下拉一下，即diff>0  
                if (firstVisibleItemPosition == 0 && paddingTop > -headerViewHeight) {
                    //下拉之后还要判断是下拉刷新还是松开刷新（默认执行次判断）  
                    if (paddingTop > 0 && currentState_header == DOWN_PULL_REFRESH) {//当前状态为正在往下拉
                        //改变状态，以便进行松开刷新  
                        currentState_header = RELEASE_REFRESH;
                        refreshHeaderView();//进行相应的刷新动画操作，currentState_header为最新改变的值  
                    } else if (paddingTop < 0 && currentState_header == RELEASE_REFRESH) {//当前状态为松手了，当没有成功
                        currentState_header = DOWN_PULL_REFRESH;
                        refreshHeaderView();
                    }
                    //将头布局显示出来(随着paddingTop变化)  
                    headerView.setPadding(0, paddingTop, 0, 0);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP://松手  
                //松手时判断当前的状态  
                if (currentState_header == RELEASE_REFRESH) {
                    //把头布局设置为完全显示，要进行刷新数据操作和切换视图  
                    //进入到正在刷新中状态  
                    currentState_header = REFRESHING;
                    //切换视图  
                    refreshHeaderView();
                    //调用刷新回调方法(刷新完成后，可以添加数据)  
                } else if (currentState_header == DOWN_PULL_REFRESH) {//回到初始的状态
                    //隐藏头布局  
                    headerView.setPadding(0, -headerViewHeight, 0, 0);
                }
                break;
            default:
                break;
        }
        //如果diff<0（paddingTop<-headerViewHeight）,则不做任何操作  
        return super.onTouchEvent(ev);
    }

    /**
     * 根据currentState_header刷新头布局的状态
     */
    public void refreshHeaderView() {
        switch (currentState_header) {
            case DOWN_PULL_REFRESH://默认要下拉的状态，开启箭头动画  

                ivArrow.startAnimation(downAnimation);
                tvState.setText("下拉刷新");
                break;
            case RELEASE_REFRESH://松开刷新箭头动画  

                ivArrow.startAnimation(upAnimation);
                tvState.setText("松开刷新");
                break;
            case REFRESHING://正在刷新中  
                //切换视图  
                headerView.setPadding(0, 0, 0, 0);
                ivArrow.clearAnimation();
                ivArrow.setVisibility(GONE);
                mProgressBar.setVisibility(VISIBLE);
                tvState.setText("正在刷新中...");
                if (mOnRefreshListener != null) {
                    mOnRefreshListener.onDownPullRefresh();
                }
                break;
        }
    }

    /**
     * 隐藏头布局
     */
    public void hideHeaderView() {
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        ivArrow.setVisibility(VISIBLE);
        mProgressBar.setVisibility(GONE);
        tvState.setText("下拉刷新");
        tvLastUpdateTime.setText("最后刷新时间：" + getLastUpdateTime());
        currentState_header = DOWN_PULL_REFRESH;
    }

    /**
     * 初始化脚布局
     */
    private void initFooterView() {
        footerView = mLayoutInflater.inflate(R.layout.footer_layout, null);
        //测量footerView的高度  
        footerView.measure(0, 0);
        footerViewHeight = footerView.getMeasuredHeight();
        //初始化隐藏脚布局  
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        //设置成此效果是一样的footerView.setPadding(0,0,0,-footerViewHeight);  
        //添加到myListView上  
        this.addFooterView(footerView);
    }

    /**
     * 隐藏脚布局
     */
    public void hideFooterView() {
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        isLoadingMore = false;
    }


    /**
     * 设置刷新监听事件
     *
     * @param onRefreshListener
     */
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.mOnRefreshListener = onRefreshListener;
    }

    /**
     * 处理上拉加载更多事件(在ListView状态改变时调用)
     *
     * @param view
     * @param scrollState（空闲SCROLL_STATE_IDLE 、滑动SCROLL_STATE_TOUCH_SCROLL和惯性滑动SCROLL_STATE_FLING）
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING) {//当滑动停止或惯性滑动时
            //判断是否已经到达了底部  
            if (isScrollToBottom && !isLoadingMore) {//若在底部，并且之前没有加载，则加载更多
                //即将加载更多(在每次加载完成之后才重置标识位为false)  
                isLoadingMore = true;
                //将脚布局显示出来  
                footerView.setPadding(0, 0, 0, 0);
                //获得listView的数量，显示新加载的那一项（原有的最后一个为getCount-1）  
                this.setSelection(this.getCount());
                if (mOnRefreshListener != null) {
                    mOnRefreshListener.onLoadingMore();
                }
            }

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //时刻监视可见视图中的第一个item  
        firstVisibleItemPosition = firstVisibleItem;
        if (firstVisibleItem + visibleItemCount == totalItemCount) {//到达了底部
            isScrollToBottom = true;
        } else {
            isScrollToBottom = false;
        }
    }

    public interface OnRefreshListener {
        void onDownPullRefresh();

        void onLoadingMore();
    }
}   