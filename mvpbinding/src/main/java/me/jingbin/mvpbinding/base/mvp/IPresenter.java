package me.jingbin.mvpbinding.base.mvp;

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
     * 在框架中 {@link me.jingbin.mvpbinding.base.BaseActivity#onDestroy()} 时会默认调用 {@link IPresenter#onDestroy()}
     */
    void onDestroy();
}
