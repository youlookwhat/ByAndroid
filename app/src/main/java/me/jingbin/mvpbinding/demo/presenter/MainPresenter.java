package me.jingbin.mvpbinding.demo.presenter;

import android.util.Log;

import me.jingbin.mvpbinding.base.mvp.BasePresenter;
import me.jingbin.mvpbinding.demo.contract.MainContract;
import me.jingbin.mvpbinding.demo.model.MainModel;

/**
 * @author jingbin
 * @data 2019-07-01
 * @description
 */
public class MainPresenter extends BasePresenter<MainContract.MainView> {

    private MainModel mModel;

    public MainPresenter(MainContract.MainView mView) {
        super(mView);
        mModel = new MainModel();
        setModel(mModel);
    }

    public void load() {
        mModel.loadData(new MainContract.OnLoadDataListener() {
            @Override
            public void loadDetail(String text) {
                Log.e("tag", "load------loadData");
                mView.showContent();
                mView.showData(text);
            }
        });
    }

    @Override
    public void onDestroy() {
        Log.e("tag", "load--MainPresenter----onDestroy");
    }
}
