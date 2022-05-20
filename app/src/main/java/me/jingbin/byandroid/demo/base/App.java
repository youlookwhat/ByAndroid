package me.jingbin.byandroid.demo.base;


import android.content.ComponentCallbacks2;

import androidx.multidex.MultiDexApplication;

import com.bumptech.glide.Glide;


/**
 * @author jingbin
 */
public class App extends MultiDexApplication {

    private static App app;

    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

//        HttpUtils.getInstance().init(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        //当应用所有UI隐藏时应该释放UI上所有占用的资源
        if (ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN == level) {
            Glide.get(this).clearMemory();
        }
        //根据level级别来清除一些图片缓存
        Glide.get(this).onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }
}
