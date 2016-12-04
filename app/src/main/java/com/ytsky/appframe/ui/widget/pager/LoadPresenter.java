package com.ytsky.appframe.ui.widget.pager;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * author:  yytian
 * time:    2016/12/1 0001 下午 6:23
 * des:
 */

public class LoadPresenter {
    private LoadingPager mView;
    public int mCurState = LoadState.NONE;    // 默认显示loading视图

    public LoadPresenter(LoadingPager view) {
        mView = view;
    }

    public void triggerLoadData() {
        // think : the order run in JVM
        // it needn't refresh if the state is success now
        /*if (mCurState != LoadState.SUCCESS && mCurState != LoadState.LOADING) {
            // reset mCurState to loading
            mCurState = LoadState.LOADING;
            // 1. refresh view before load data
            mView.refreshUIByState(mCurState);

            // 2. load data from net work,it must be async
            LoadedResult tempState = mView.initData();
            mCurState = tempState.getState();

            // 3. refresh view after load data
            mView.refreshUIByState(mCurState);

        }*/
        if (mCurState != LoadState.SUCCESS && mCurState != LoadState.LOADING) {
            Flowable.empty()
                    .doOnNext(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            // reset mCurState to loading
                            mCurState = LoadState.LOADING;
                            // 1. refresh view before load data
                            mView.refreshUIByState(mCurState);
                        }
                    })
                    .doOnNext(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            // 2. load data from net work,it must be async
                            LoadedResult tempState = mView.initData();
                            mCurState = tempState.getState();
                        }
                    })
                    .doOnNext(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            // 3. refresh view after load data
                            mView.refreshUIByState(mCurState);
                        }
                    })
                    .subscribe();
        }

    }
}
