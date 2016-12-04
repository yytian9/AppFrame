package com.ytsky.appframe.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.ytsky.appframe.base.BaseApplication;


/**
 * Author  Farsky
 * Date    2016/11/14 0014
 * Des
 */
public class ToastUtils {

    public static final int TYPE_NORMAL =1;
    public static final int TYPE_ERROR =2;
    public static final int TYPE_SUCCESE =3;

    private static Toast mToast;

    public static void openToast(int resId ,int type ) {
        String meg = BaseApplication.getApplication().getResources().getString(resId);
        openToast(BaseApplication.getApplication(),meg,type);
    }
    public static void openToast(String meg ,int type ) {
        openToast(BaseApplication.getApplication(),meg,type);
    }


    public static void openToast(Context context, int resId ,int type ) {
        String meg = context.getResources().getString(resId);
        openToast(context,meg,type);
    }
    public static void openToast(Context context, String  meg ,int type ) {
        if (context != null) {

            Context applicationContext = context.getApplicationContext();

            if(mToast == null){
                //1.创建吐司
                mToast = Toast.makeText(applicationContext, meg, Toast.LENGTH_SHORT);
            }else {
                mToast.setText(meg);
            }


            //2.吐司的位置
            mToast.setGravity(Gravity.CENTER,0,0);
            //3.显示吐司
            mToast.show();
        }
    }

}
