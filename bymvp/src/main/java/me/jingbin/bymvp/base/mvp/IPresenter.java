package me.jingbin.bymvp.base.mvp;

import me.jingbin.bymvp.base.BaseActivity;

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
     * 在框架中 {@link BaseActivity#onDestroy()} 时会默认调用 {@link IPresenter#onDestroy()}
     */
    void onDestroy();
}
