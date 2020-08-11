package me.jingbin.bymvp.base.mvp;

/**
 * @author jingbin
 * @data 2019-07-09
 * @description
 */
public interface IModel {

    /**
     * 在框架中 {@link BasePresenter#onDestroy()} 时会默认调用 {@link IModel#onDestroy()}
     */
    void onDestroy();
}
