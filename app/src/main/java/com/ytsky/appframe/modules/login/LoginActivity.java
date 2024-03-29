package com.ytsky.appframe.modules.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.ytsky.appframe.R;
import com.ytsky.appframe.http.retrofit.FungoReqeust;
import com.ytsky.appframe.http.schedulers.SchedulerProvider;
import com.ytsky.appframe.util.ToastUtils;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginContract.View, View.OnClickListener {


    /*@Inject */LoginPresenter mPresenter;
    private EditText mEtUsername;
    private EditText mEtPassword;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //create a model
        LoginModel meModel = new LoginModel(FungoReqeust.getInstance());
        //create the presenter for this fragment
        mPresenter = new LoginPresenter(this, SchedulerProvider.getInstance(),meModel);
//        ((BaseApplication) getApplication()).getNetComponent().inject(this);

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
        switch (view.getId()){
            case R.id.btn_login:
                String userName = mEtUsername.getText().toString().trim();
                String password = mEtPassword.getText().toString().trim();
                mPresenter.login(userName,password);
                break;
            default:
                break;
        }
    }

    @Override
    public void showUserNameOrPasswordErrorToast() {
        ToastUtils.openToast("用户名或密码错误",ToastUtils.TYPE_ERROR);
    }

    @Override
    public void showSuccessView() {
        ToastUtils.openToast("登录成功",ToastUtils.TYPE_SUCCESE);
    }

    @Override
    public void showLoadingIndicator(boolean active) {

    }

    @Override
    public void showFailureToast() {
        ToastUtils.openToast("登录失败",ToastUtils.TYPE_ERROR);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }
}

