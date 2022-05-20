package me.jingbin.byandroid.base.mvp;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @author jingbin
 * @data 2019-07-09
 * @description
 */
public class BaseModel implements IModel, LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        owner.getLifecycle().removeObserver(this);

        Log.e("tag","-----BaseModel--OnLifecycleEvent---onDestroy");
    }

    @Override
    public void onDestroy() {
        Log.e("tag","-----BaseModel-----onDestroy");
    }
}
