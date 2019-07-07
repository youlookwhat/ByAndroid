package me.jingbin.mvpbinding.base.presenter;

import android.app.Activity;

/**
 * @author jingbin
 * @data 2019-06-30
 * @description
 */
public interface IPresenter {

    /**
     * 做一些初始化操作
     */
    void onStart();

    /**
     * 在框架中 {@link Activity#onDestroy()} 时会默认调用 {@link IPresenter#onDestroy()}
     */
    void onDestroy();
}
