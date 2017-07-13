package com.support.android.designlibdemo;

import android.app.Application;

import com.pgyersdk.crash.PgyCrashManager;

/**
 * Created by gary on 17-7-13.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PgyCrashManager.register(this);
    }
}
