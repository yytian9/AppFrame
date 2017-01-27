package com.ytsky.appframe.ui.widget.pager;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.ytsky.appframe.R;


/**
 * author:  yytian
 * time:    2016/12/1 0001 下午 5:36
 * des:
 */
public abstract class LoadingPager extends FrameLayout {
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSuccessView;
    private Context mContext;

    public static final int STATE_NONE = -1;
    public static final int STATE_LOADING = 0;
    public static final int STATE_EMPTY = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_SUCCESS = 3;

    public LoadingPager(Context context) {
        super(context);
        this.mContext = context;
        initCommonView();
        initData();
    }


    /**
     * @des 初始化常规视图(加载视图, 空视图, 错误视图), 因为他们相对比较静态
     * @call LoadingPager初始化的时候被调用
     */
    private void initCommonView() {
        // 加载视图
        mLoadingView = View.inflate(mContext, R.layout.pager_loading, null);
        addView(mLoadingView);

        // 空页面
        mEmptyView = View.inflate(mContext, R.layout.pager_empty, null);
        addView(mEmptyView);

        // 错误页面
        mErrorView = View.inflate(mContext, R.layout.pager_error, null);
        addView(mErrorView);
        mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }


        });
        //成功界面
        mSuccessView = initSuccessView();
        addView(mSuccessView);

        // 根据当前的状态,显示其中某一个视图
        refreshUIByState(STATE_LOADING);
    }

    public void refreshUIByState(int state) {
        mLoadingView.setVisibility((state == STATE_LOADING) || (state == STATE_NONE) ? View.VISIBLE : View.GONE);
        mEmptyView.setVisibility((state == STATE_EMPTY) ? View.VISIBLE : View.GONE);
        mErrorView.setVisibility((state == STATE_ERROR) ? View.VISIBLE : View.GONE);
        // 控制成功视图的显示/隐藏
        if (mSuccessView != null) {
            mSuccessView.setVisibility((state == STATE_SUCCESS) ? View.VISIBLE : View.GONE);
        }
    }


    /**
     * use for init data,for most case: use for first come into page
     */
    public abstract void initData();

    /**
     * @return the view must be show
     */
    public abstract View initSuccessView();

}
