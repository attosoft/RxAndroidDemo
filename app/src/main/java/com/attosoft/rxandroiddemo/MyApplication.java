package com.attosoft.rxandroiddemo;

import android.app.Application;

/**
 * Created by andy on 2016/2/3.
 */
public class MyApplication extends Application{

    private static MyApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static MyApplication getApplication(){
        return mContext;
    }
}
