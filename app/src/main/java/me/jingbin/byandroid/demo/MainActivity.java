package me.jingbin.byandroid.demo;

import android.os.Bundle;

import me.jingbin.byandroid.base.BaseActivity;
import me.jingbin.byandroid.demo.contract.MainContract;
import me.jingbin.byandroid.demo.databinding.ActivityMainBinding;
import me.jingbin.byandroid.demo.presenter.MainPresenter;


/**
 * @author jingbin
 * <a href="https://github.com/youlookwhat">Follow me</a>
 */
public class MainActivity extends BaseActivity<MainPresenter, ActivityMainBinding> implements MainContract.MainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("主页");
        showWhiteImmersionBar();

        binding.toolBar.postDelayed(() -> presenter.load(), 1000);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void showContent() {
        showContentView();
    }

    @Override
    public void showData(String text) {
        binding.textView.setText(text);
    }

    @Override
    protected void onRefresh() {
        showContentView();
    }
}
