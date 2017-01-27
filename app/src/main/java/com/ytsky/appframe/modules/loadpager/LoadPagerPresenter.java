package com.ytsky.appframe.modules.loadpager;

import com.ytsky.appframe.http.schedulers.BaseSchedulerProvider;
import com.ytsky.appframe.http.subscribe.HttpSubscribe;
import com.ytsky.appframe.ui.widget.pager.LoadedResult;
import com.ytsky.appframe.util.ToastUtils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * author:  yytian
 * time:    2016/12/1 0001 下午 7:32
 * des:
 */

public class LoadPagerPresenter implements LoadPagerContract.Presenter {

    private final LoadPagerContract.View mView;
    private final BaseSchedulerProvider mSchedulerProvider;
    private final CompositeDisposable mSubscriptions;
    private final LoadPagerContract.Model mModel;
//    LoadedResult result/* = LoadedResult.EMPTY */;
    @Inject
    public LoadPagerPresenter(LoadPagerContract.View view, BaseSchedulerProvider schedulerProvider,
                                LoadPagerContract.Model model) {
        mModel = model;
        mView = view;
        mSchedulerProvider = schedulerProvider;
        mSubscriptions = new CompositeDisposable();
    }
    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void loadInitData() {

        HttpSubscribe<Integer> httpSubscribe = mModel.getInitData()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribeWith(new HttpSubscribe<Integer>() {

                    @Override
                    public void onNext(Integer o) {
//                        result = LoadedResult.SUCCESS;
                        ToastUtils.openToast("加载成功",ToastUtils.TYPE_SUCCESE);
                        mView.refreshInitView(LoadedResult.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable t) {
//                        result = LoadedResult.ERROR;
                        ToastUtils.openToast("加载失败",ToastUtils.TYPE_ERROR);
                        mView.refreshInitView(LoadedResult.ERROR);
                    }
                });
        mSubscriptions.add(httpSubscribe);


//        return result;
    }
}
