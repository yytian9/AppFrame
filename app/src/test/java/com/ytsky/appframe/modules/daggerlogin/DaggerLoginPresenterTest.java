package com.ytsky.appframe.modules.daggerlogin;

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
 * time:    2016/12/1 0001 上午 11:48
 * des:
 */
public class DaggerLoginPresenterTest {
    private DaggerLoginPresenter mPresenter;
    @Mock
    private DaggerLoginContract.Model mModel;
    @Mock
    private DaggerLoginContract.View mView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        BaseSchedulerProvider schedulerProvider = new ImmediateSchedulerProvider();
        mPresenter = new  DaggerLoginPresenter(mView, schedulerProvider,mModel);
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