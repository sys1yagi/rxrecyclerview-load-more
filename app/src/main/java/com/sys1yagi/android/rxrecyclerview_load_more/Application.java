package com.sys1yagi.android.rxrecyclerview_load_more;

import com.squareup.leakcanary.LeakCanary;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
