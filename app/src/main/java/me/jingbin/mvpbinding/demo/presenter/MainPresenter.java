package me.jingbin.mvpbinding.demo.presenter;

import android.util.Log;

import me.jingbin.mvpbinding.base.presenter.BasePresenter;
import me.jingbin.mvpbinding.demo.contract.MainView;

/**
 * @author jingbin
 * @data 2019-07-01
 * @description
 */
public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MainView rootView) {
        super(rootView);
    }

    public void load() {
        Log.e(getClass().getName(), "load");
    }

    @Override
    public void onDestroy() {

    }
}
