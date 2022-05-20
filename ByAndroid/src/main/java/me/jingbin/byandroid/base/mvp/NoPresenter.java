package me.jingbin.byandroid.base.mvp;


/**
 * @author jingbin
 * @data 2019-06-30
 * @description 简单页面不需要设置Presenter，用 NoPresenter 替代
 */
public class NoPresenter extends BasePresenter {

    @Override
    public void onDestroy() {

    }
}
