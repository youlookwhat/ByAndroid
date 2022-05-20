package me.jingbin.byandroid.demo.model;

import me.jingbin.byandroid.base.mvp.BaseModel;
import me.jingbin.byandroid.demo.contract.MainContract;

/**
 * @author jingbin
 * @data 2019-07-09
 * @description
 */
public class MainModel extends BaseModel {

    public void loadData(MainContract.OnLoadDataListener listener) {
        String text = "我是内容";
        listener.loadDetail(text);
    }
}
