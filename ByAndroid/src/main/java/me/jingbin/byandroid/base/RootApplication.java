package me.jingbin.byandroid.base;


import android.content.Context;

import androidx.multidex.MultiDexApplication;

import me.jingbin.byandroid.http.HttpUtils;
import me.jingbin.byandroid.utils.PreferenceHelper;


/**
 * @author jingbin
 */
public class RootApplication extends MultiDexApplication {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        // 初始化本地存储
        if (!PreferenceHelper.isInit()) {
            PreferenceHelper.init(this);
        }
        HttpUtils.getInstance().init(this);
    }

    public static Context getContext() {
        return mContext;
    }
}
