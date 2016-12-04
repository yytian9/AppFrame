package com.ytsky.appframe.ui.widget.pager;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.ytsky.appframe.R;
import com.ytsky.appframe.constant.MessageCode;
import com.ytsky.appframe.http.base.RequestError;
import com.ytsky.appframe.http.subscribe.HttpSubscribe;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


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
//    public int mCurState = LoadState.NONE;    // 默认显示loading视图
    private LoadedResult mTempState;

    public static final int NONE = -1;
    public static final int LOADING = 0;
    public static final int EMPTY = 1;
    public static final int ERROR = 2;
    public static final int SUCCESS = 3;

    public LoadingPager(Context context) {
        super(context);
        this.mContext = context;
        initCommonView();
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
                triggerLoadData();
            }


        });
        // 根据当前的状态,显示其中某一个视图
        refreshUIByState(LoadState.LOADING);
    }

    public void refreshUIByState(int state) {
        mLoadingView.setVisibility((state == LoadState.LOADING) || (state == LoadState.NONE) ? View.VISIBLE : View.GONE);
        mEmptyView.setVisibility((state == LoadState.EMPTY) ? View.VISIBLE : View.GONE);
        mErrorView.setVisibility((state == LoadState.ERROR) ? View.VISIBLE : View.GONE);
        // 如果是数据加载完成之后来到这里,那么就可能有成功视图了吧.
        if (state == LoadState.SUCCESS && mSuccessView == null) {
            mSuccessView = initSuccessView();
            addView(mSuccessView);
        }
        // 控制成功视图的显示/隐藏
        if (mSuccessView != null) {
            mSuccessView.setVisibility((state == LoadState.SUCCESS) ? View.VISIBLE : View.GONE);
        }
    }

    public void triggerLoadData() {
        //        if (mCurState != LoadState.SUCCESS && mCurState != LoadState.LOADING) {
        Flowable.just(1)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Integer, Publisher<Integer>>() {
                    @Override
                    public Publisher<Integer> apply(Integer integer) throws Exception {
                        // 2. load data from net work,it must be async
                        return Flowable.create(new FlowableOnSubscribe<Integer>() {
                            @Override
                            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                                LoadedResult tempState = initData();
                                if(tempState!=null){
//                                    mCurState = tempState.getState();
                                    e.onNext(tempState.getState());
                                }else {
                                    e.onError(new RequestError(MessageCode.LOADPAGE_EMPTY_DATA,"loadpage 常规错误"));
                                }
                            }
                        }, BackpressureStrategy.BUFFER);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpSubscribe<Integer>() {
                    @Override
                    public void _onStart() {
                        super._onStart();
                        refreshUIByState(LoadState.LOADING);

                    }

                    @Override
                    public void onNext(Integer integer) {
                        // 3. refresh view after load data
                        refreshUIByState(LoadState.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        refreshUIByState(LoadState.ERROR);
                    }
                });
        //        }
    }

    public abstract LoadedResult initData();

    public abstract View initSuccessView();

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
