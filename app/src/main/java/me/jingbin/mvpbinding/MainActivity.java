package me.jingbin.mvpbinding;

import android.os.Bundle;

import me.jingbin.mvpbinding.base.BaseMvpActivity;
import me.jingbin.mvpbinding.databinding.ActivityMainBinding;

/**
 * @author jingbin
 */
public class MainActivity extends BaseMvpActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("主页");
        showWhiteImmersionBar();
        binding.toolBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                showErrorView();
            }
        },3000);


//        ImmersionBar.with(this)
//                .statusBarColor(R.color.colorPrimary)
//                .statusBarAlpha(0).init();
//        ImmersionBar.setTitleBar(this, findViewById(R.id.toolBar));
    }

    @Override
    protected void onRefresh() {
        showContentView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
