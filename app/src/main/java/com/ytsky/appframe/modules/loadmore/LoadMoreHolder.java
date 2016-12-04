package com.ytsky.appframe.modules.loadmore;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ytsky.appframe.R;

/**
 * author:  yytian
 * time:    2016/12/2 15:22
 * des:
 */

public class LoadMoreHolder extends RecyclerView.ViewHolder {

    public final TextView mTvItem;

    public LoadMoreHolder(View itemView) {
        super(itemView);
        mTvItem = (TextView) itemView.findViewById(R.id.tv_item);
    }
}
