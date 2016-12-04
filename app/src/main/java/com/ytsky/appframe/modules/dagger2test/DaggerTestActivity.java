package com.ytsky.appframe.modules.dagger2test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ytsky.appframe.R;
import com.ytsky.appframe.util.ToastUtils;

import javax.inject.Inject;

public class DaggerTestActivity extends AppCompatActivity implements Dagger2TestContract.View {

    @Inject Dagger2TestPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger_test);

        /*Dagger2TestModel model = new Dagger2TestModel(this);
        mPresenter = new Dagger2TestPresenter(this, model);*/

        DaggerDagger2TestComponent
                .builder()
                .dagger2TestModule(new Dagger2TestModule(this))
                .build()
                .inject(this);
    }

    public void btnClick(View view) {
        mPresenter.btnClickTest();
    }

    @Override
    public void btnClickSuccessToast() {
        ToastUtils.openToast("dagger2点击成功了",ToastUtils.TYPE_SUCCESE);
    }
}
