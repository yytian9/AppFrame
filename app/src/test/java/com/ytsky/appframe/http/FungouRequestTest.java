package com.ytsky.appframe.http;

import android.support.v4.util.ArrayMap;

import com.ytsky.appframe.BuildConfig;
import com.ytsky.appframe.constant.ParamName;
import com.ytsky.appframe.constant.RequestPath;
import com.ytsky.appframe.entity.LoginInfo;
import com.ytsky.appframe.util.MapUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

/**
 * author： yytian
 * time：   2016/11/16 09:05
 * des：    network test using test encapsulation(NetTestHelper)
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class FungouRequestTest {

    private NetTestHelper mNetTestHelper;

    @Before
    public void setupCategoryPresenter() {
        mNetTestHelper = NetTestHelper.getInstance();
    }


    @Test
    public void r202_login() throws Exception {
        ArrayMap<String, Object> dataParams = MapUtils.getDataParams(
                ParamName.USER, PersonanlTestInfo.USER_NAME,
                ParamName.PASSWORD, PersonanlTestInfo.PASSWORD);
        LoginInfo loginInfo = mNetTestHelper.testApi(RequestPath.LOGIN, dataParams, LoginInfo.class);
        //token shouldn't be a null,since that will cause a serious error
        assertNotNull(loginInfo.token);

    }


}
