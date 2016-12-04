package com.ytsky.appframe.modules.daggerlogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.ytsky.appframe.R;
import com.ytsky.appframe.util.ToastUtils;

import javax.inject.Inject;

public class DaggerLoginActivity extends AppCompatActivity implements DaggerLoginContract.View, View.OnClickListener {
    @Inject
    DaggerLoginPresenter mPresenter;
    private EditText mEtUsername;
    private EditText mEtPassword;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger_login);
        /*//create a model
        DaggerLoginModel meModel = new DaggerLoginModel(FungoReqeust.getInstance());
        //create the presenter for this fragment
        mPresenter = new DaggerLoginPresenter(this, SchedulerProvider.getInstance(), meModel);
        //        ((BaseApplication) getApplication()).getNetComponent().inject(this);*/


        DaggerDaggerLoginComponent
                .builder()
                .daggerLoginModule(new DaggerLoginModule(this))
                .build()
                .inject(this);


        initView();
    }


    private void initView() {
        mProgressView = findViewById(R.id.login_progress);
        mEtUsername = (EditText) findViewById(R.id.et_username);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        findViewById(R.id.btn_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String userName = mEtUsername.getText().toString().trim();
                String password = mEtPassword.getText().toString().trim();
                mPresenter.login(userName, password);
                break;
            default:
                break;
        }
    }

    @Override
    public void showUserNameOrPasswordErrorToast() {
        ToastUtils.openToast("用户名或密码错误", ToastUtils.TYPE_ERROR);
    }

    @Override
    public void showSuccessView() {
        ToastUtils.openToast("登录成功", ToastUtils.TYPE_SUCCESE);
    }

    @Override
    public void showLoadingIndicator(boolean active) {
        mProgressView.setVisibility(active ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showFailureToast() {
        ToastUtils.openToast("登录失败", ToastUtils.TYPE_ERROR);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }
}
