package com.linsr.loudspeaker.application;

import android.app.Application;
import android.support.compat.BuildConfig;

import com.linsr.loudspeaker.utils.log.Log;
import com.linsr.loudspeaker.utils.log.LogImpl;


/**
 * Description
 *
 * @author linsenrong on 2016/10/10 15:13
 */

public class ApplicationEx extends Application {

    private static ApplicationEx instance;

    public static ApplicationEx getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Log log = LogImpl.getInstance();
        log.setLogToLogCat(BuildConfig.DEBUG);


    }

}