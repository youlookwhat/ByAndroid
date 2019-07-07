package me.jingbin.mvpbinding.demo;

import android.os.Bundle;

import me.jingbin.mvpbinding.base.BaseActivity;
import me.jingbin.mvpbinding.demo.contract.MainView;
import me.jingbin.mvpbinding.demo.databinding.ActivityMainBinding;
import me.jingbin.mvpbinding.demo.presenter.MainPresenter;


/**
 * @author jingbin
 * <a href="https://github.com/youlookwhat">Follow me</a>
 */
public class MainActivity extends BaseActivity<MainPresenter, ActivityMainBinding> implements MainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("主页");
        showWhiteImmersionBar();
        presenter.load();
//        startProgressDialog();
//        setNoTitleBar();

        binding.toolBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                showErrorView();
                stopProgressDialog();
            }
        }, 3000);


//        ImmersionBar.with(this)
//                .statusBarColor(R.color.colorPrimary)
//                .statusBarAlpha(0).init();
//        ImmersionBar.setTitleBar(this, findViewById(R.id.toolBar));
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void onRefresh() {
//        showContentView();
        binding.toolBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                showErrorView();
            }
        }, 3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showContent() {
        showContentView();
    }
}
