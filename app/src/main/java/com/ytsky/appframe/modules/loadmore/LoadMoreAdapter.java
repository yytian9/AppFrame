package com.ytsky.appframe.modules.loadmore;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ytsky.appframe.R;
import com.ytsky.appframe.util.ToastUtils;

import java.util.List;

/**
 * author:  yytian
 * time:    2016/12/2 15:20
 * des:
 */

public class LoadMoreAdapter extends RecyclerView.Adapter {
    private List<String> mData;

    public LoadMoreAdapter(List<String> data) {
        mData = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_load_more, parent, false);
        return new LoadMoreHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        LoadMoreHolder loadMoreHolder = (LoadMoreHolder) holder;
        loadMoreHolder.mTvItem.setText(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.openToast("条目："+position+"被点击了",ToastUtils.TYPE_NORMAL);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mData!=null){
            return mData.size();
        }
        return 0;
    }

    public void addData(List<String> data) {
        if (data != null) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }
}
