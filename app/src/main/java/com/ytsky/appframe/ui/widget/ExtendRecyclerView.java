package com.ytsky.appframe.ui.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * author:  yytian
 * time:    2016/11/28 23:29
 * des:
 */
public class ExtendRecyclerView extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener {
    public ExtendRecyclerView(Context context) {
        this(context,null);
    }

    public ExtendRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {

    }
}
