package me.jingbin.mvpbinding.demo.contract;

import me.jingbin.mvpbinding.base.mvp.BaseView;

/**
 * @author jingbin
 * @data 2019-07-09
 * @description
 */
public interface MainContract {

    interface OnLoadDataListener{
        void loadDetail(String text);
    }

    interface MainView extends BaseView {
        void showData();
    }
}
