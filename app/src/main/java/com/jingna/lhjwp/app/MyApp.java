package com.jingna.lhjwp.app;

import android.app.Activity;
import android.app.Application;

import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/23.
 */

public class MyApp extends Application {

    private static MyApp instance;
    private List<Activity> mList = new LinkedList<Activity>();

    public MyApp() {
    }

    public synchronized static MyApp getInstance() {
        if (null == instance) {
            instance = new MyApp();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ScreenAdapterTools.init(this);
    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

}
