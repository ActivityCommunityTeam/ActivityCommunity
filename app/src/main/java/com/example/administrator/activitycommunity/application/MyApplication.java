package com.example.administrator.activitycommunity.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Daniel on 2016/11/16.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        //测试内存溢出工具初始化
//        LeakCanary.install(this);

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(1)// Must be bumped when the schema changes
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }
}
