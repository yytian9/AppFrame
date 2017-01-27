package com.ytsky.appframe.http;

import com.ytsky.appframe.http.retrofit.TempReqeust;
import com.ytsky.appframe.http.schedulers.BaseSchedulerProvider;
import com.ytsky.appframe.http.schedulers.ImmediateSchedulerProvider;

import java.util.List;
import java.util.Map;

import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;


/**
 * author： yytian
 * time：   2016/9/28 11:14
 * des：     provide encapsulation for network test,this class assert every api port that  could get
 *    the right response.For more assertion,you can get response data through method(testApi(...))
 */

public class TempNetTestHelper {
    private TempReqeust mRequest;
    private BaseSchedulerProvider mSchedulerProvider;
    public static TempNetTestHelper mInstance;

    private TempNetTestHelper() {
        mRequest = TempReqeust.getInstance();
        mSchedulerProvider = new ImmediateSchedulerProvider();
    }

    public static TempNetTestHelper getInstance() {
        if (mInstance == null) {
            mInstance = new TempNetTestHelper();
        }
        return mInstance;
    }



    public<T> T testApi(String sourceUrl, Map<String, Object> params, Class<T> clazz){
        TestScheduler scheduler = new TestScheduler();

        TestObserver<T> test = mRequest.getRequest(sourceUrl, params, clazz, false)
                .test();

        test.assertNoErrors();
        test.assertValueCount(1);

        List<T> values = test.values();

        return values.get(0);

    }

}
