package com.ytsky.appframe.modules.loadpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ytsky.appframe.R;
import com.ytsky.appframe.http.retrofit.FungoReqeust;
import com.ytsky.appframe.http.schedulers.SchedulerProvider;
import com.ytsky.appframe.ui.widget.pager.LoadedResult;
import com.ytsky.appframe.ui.widget.pager.LoadingPager;

public class LoadPagerActivity extends AppCompatActivity implements LoadPagerContract.View {

    private LoadPagerPresenter mPresenter;
    private LoadingPager mLoadingPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //create a model
        LoadPagerModel meModel = new LoadPagerModel(FungoReqeust.getInstance());
        //create the presenter for this fragment
        mPresenter = new LoadPagerPresenter(this, SchedulerProvider.getInstance(), meModel);

        mLoadingPager = new LoadingPager(this) {
            @Override
            public void initData() {
                mPresenter.loadInitData();
            }

            @Override
            public View initSuccessView() {
                return View.inflate(LoadPagerActivity.this, R.layout.activity_load_pager, null);
            }
        };

        setContentView(mLoadingPager);
    }

    @Override
    public void refreshInitView(LoadedResult result) {
        mLoadingPager.refreshUIByState(result.getState());
    }
}
