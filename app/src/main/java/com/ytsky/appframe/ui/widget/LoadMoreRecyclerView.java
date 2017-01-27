package com.ytsky.appframe.ui.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ytsky.appframe.R;

/**
 * author:  yytian
 * time:    2016/12/2 11:35
 * des:
 */

public class LoadMoreRecyclerView extends RecyclerView {

    private boolean isLoadingMoreEnabled = true;
    private WrapAdapter mWrapAdapter;
    private static final int TYPE_FOOTER = 10001;
    private LoadingListener mLoadingListener;
    private boolean isLoading = false;

    private final RecyclerView.AdapterDataObserver mDataObserver = new DataObserver();

    public static final int LOADMORE_LOADING = 0;//正在加载更多
    public static final int LOADMORE_ERROR = 1;//加载更多失败
    public static final int LOADMORE_NONE = 2;//没有加载更多

    public int mCurrentLoadMoreState=LOADMORE_LOADING;// default is loading

    public LoadMoreRecyclerView(Context context) {
        this(context, null);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreRecyclerView(Context context,  AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        int lastVisibleItem = getLastVisibleItemPosition();
        int totalItemCount = getLayoutManager().getItemCount();
        if (!isLoadingMoreEnabled) {
            return;
        }
        if (!isLoading && lastVisibleItem >= totalItemCount - 2 && dy > 0 && mCurrentLoadMoreState!=LOADMORE_ERROR) {
            isLoading = true;
            if (mLoadingListener != null) {
                mLoadingListener.onLoadMore();
            }
        }

    }


    private int getLastVisibleItemPosition() {
        if (getLayoutManager() instanceof LinearLayoutManager) {
            int position = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
            return position;
        }
        throw new RuntimeException("only LinearLayoutManager is supported");

    }

    @Override
    public void setAdapter(Adapter adapter) {
        mWrapAdapter = new WrapAdapter(adapter);
        super.setAdapter(mWrapAdapter);
        adapter.registerAdapterDataObserver(mDataObserver);
        mDataObserver.onChanged();
    }


    @Override
    public Adapter getAdapter() {
        if (mWrapAdapter != null) {
            return mWrapAdapter.getOriginalAdapter();
        }
        return null;
    }


    public void setLoadingListener(LoadingListener listener) {
        mLoadingListener = listener;
    }

    /**
     * 加载完成之后必须调用该方法
     */
    public void onLoadMoreComplete(int state) {
        mCurrentLoadMoreState = state;
        isLoading = false;
        mWrapAdapter.notifyDataSetChanged();
    }


    public void refreshLoadMoreState(int state){
        mCurrentLoadMoreState = state;
        mWrapAdapter.notifyDataSetChanged();
    }

    class WrapAdapter extends RecyclerView.Adapter<ViewHolder> {

        private RecyclerView.Adapter adapter;

        public WrapAdapter(RecyclerView.Adapter adapter) {
            this.adapter = adapter;
        }

        public RecyclerView.Adapter getOriginalAdapter() {
            return this.adapter;
        }

        public boolean isFooter(int position) {
            if (isLoadingMoreEnabled) {
                return position == getItemCount() - 1;
            } else {
                return false;
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            if (viewType == TYPE_FOOTER) {
                View view = LayoutInflater.from(context).inflate(R.layout.item_load_more_new, parent, false);
                return new FootViewHolder(view);
            }
            return adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (adapter != null && !isFooter(position)) {
                adapter.onBindViewHolder(holder, position);
            } else if(isFooter(position)){
                FootViewHolder footViewHolder = (FootViewHolder) holder;
                footViewHolder.refreshHolderView(mCurrentLoadMoreState);
                footViewHolder.mItemLoadmoreContainerRetry.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mLoadingListener.onLoadMoreRetry();
                    }
                });
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (isFooter(position)) {
                return TYPE_FOOTER;
            }
            return adapter.getItemViewType(position);
        }

        @Override
        public int getItemCount() {
            if (adapter != null) {
                if (isLoadingMoreEnabled) {
                    return adapter.getItemCount() + 1;
                } else {
                    return adapter.getItemCount();
                }
            }
            return 0;
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mItemLoadmoreContainerLoading;
        public LinearLayout mItemLoadmoreContainerNone;
        public LinearLayout mItemLoadmoreContainerRetry;

        public FootViewHolder(View itemView) {
            super(itemView);
            mItemLoadmoreContainerLoading= (LinearLayout) itemView.findViewById(R.id.item_loadmore_container_loading);
            mItemLoadmoreContainerRetry= (LinearLayout) itemView.findViewById(R.id.item_loadmore_container_retry);
            mItemLoadmoreContainerNone= (LinearLayout) itemView.findViewById(R.id.item_loadmore_container_none);
        }

        public void refreshHolderView(int state) {
            mItemLoadmoreContainerLoading.setVisibility(View.GONE);
            mItemLoadmoreContainerRetry.setVisibility(View.GONE);
            mItemLoadmoreContainerNone.setVisibility(View.GONE);
            switch (state) {
                case LOADMORE_LOADING:
                    mItemLoadmoreContainerLoading.setVisibility(View.VISIBLE);
                    break;
                case LOADMORE_ERROR:
                    mItemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
                    break;
                case LOADMORE_NONE:
                    mItemLoadmoreContainerNone.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    }

    public interface LoadingListener {

        void onLoadMore();

        void onLoadMoreRetry();
    }

    private class DataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            if (mWrapAdapter != null) {
                mWrapAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    }
}




    /*@Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE && mLoadingListener != null && !isLoadingData && loadingMoreEnabled) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;
            if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            if (layoutManager.getChildCount() > 0
                    && lastVisibleItemPosition >= layoutManager.getItemCount() - 1 && layoutManager.getItemCount() > layoutManager.getChildCount() && !isNoMore && mRefreshHeader.getState() < ArrowRefreshHeader.STATE_REFRESHING) {
                isLoadingData = true;
                if (mFootView instanceof LoadingMoreFooter) {
                    ((LoadingMoreFooter) mFootView).setState(LoadingMoreFooter.STATE_LOADING);
                } else {
                    mFootView.setVisibility(View.VISIBLE);
                }
                mLoadingListener.onLoadMore();
            }
        }
    }*/
