package com.ytsky.appframe.modules.login;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.ytsky.appframe.entity.LoginInfo;
import com.ytsky.appframe.http.HttpResponse;
import com.ytsky.appframe.http.schedulers.BaseSchedulerProvider;
import com.ytsky.appframe.http.schedulers.ImmediateSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.StringReader;

import io.reactivex.Flowable;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * author:  yytian
 * time:    2016/11/30 0030 下午 7:59
 * des:
 */
public class LoginPresenterTest {
    private LoginPresenter mPresenter;
    @Mock
    private LoginContract.Model mModel;
    @Mock
    private LoginContract.View mView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        BaseSchedulerProvider schedulerProvider = new ImmediateSchedulerProvider();
        mPresenter = new  LoginPresenter(mView, schedulerProvider,mModel);
    }

    @Test
    public void login() throws Exception {
        JsonReader reader = new JsonReader(new StringReader(HttpResponse.R202_Login.APPVERSION_UPDATE_INFO));
        reader.setLenient(true);
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        LoginInfo login = gson.fromJson(reader, LoginInfo.class);

        when(mModel.getLoginResult(anyString(),anyString())).thenReturn(Flowable.just(login));

        mPresenter.login("mountain","123456");

        verify(mView).showLoadingIndicator(eq(true));
        verify(mView).showSuccessView();
    }

}