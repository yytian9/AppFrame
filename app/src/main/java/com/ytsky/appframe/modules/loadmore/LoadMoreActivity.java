package com.ytsky.appframe.modules.loadmore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.ytsky.appframe.R;
import com.ytsky.appframe.http.subscribe.HttpSubscribe;
import com.ytsky.appframe.ui.widget.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoadMoreActivity extends AppCompatActivity {

    private LoadMoreRecyclerView mRecyclerView;
    private LoadMoreAdapter mLoadMoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);
        mRecyclerView = (LoadMoreRecyclerView) findViewById(R.id.lmr_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLoadingListener(new LoadMoreRecyclerView.LoadingListener() {
            @Override
            public void onLoadMore() {

                Flowable
                        .create(new FlowableOnSubscribe<List<String>>() {
                            @Override
                            public void subscribe(FlowableEmitter<List<String>> e) throws Exception {
                                e.onNext(getData());
                            }
                        }, BackpressureStrategy.BUFFER)
                        .delay(3000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new HttpSubscribe<List<String>>() {
                            @Override
                            public void _onStart() {
                            }

                            @Override
                            public void onNext(List<String> o) {
                                mLoadMoreAdapter.addData(o);
                                mRecyclerView.onLoadMoreComplete();
                            }

                            @Override
                            public void onError(Throwable t) {

                            }
                        });

            }
        });

        init();
    }

    private void init() {
        mLoadMoreAdapter = new LoadMoreAdapter(getData());
        mRecyclerView.setAdapter(mLoadMoreAdapter);
    }

    List<String> getData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }
        return list;
    }
}
