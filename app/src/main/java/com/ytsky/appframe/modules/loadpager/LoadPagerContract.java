package com.ytsky.appframe.modules.loadpager;

import com.ytsky.appframe.http.base.BasePresenter;
import com.ytsky.appframe.ui.widget.pager.LoadedResult;

import io.reactivex.Flowable;

/**
 * author:  yytian
 * time:    2016/12/1 0001 下午 7:32
 * des:
 */

public class LoadPagerContract {
    interface View  {
        void refreshInitView(LoadedResult result);
    }

    interface Presenter extends BasePresenter {

        void loadInitData();
    }
    interface Model{
        Flowable<Integer> getInitData();
    }
}
