package me.jingbin.mvpbinding.base;

import android.app.Dialog;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;

import com.gyf.barlibrary.ImmersionBar;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.jingbin.mvpbinding.R;
import me.jingbin.mvpbinding.base.mvp.BasePresenter;
import me.jingbin.mvpbinding.databinding.BaseActivityBinding;
import me.jingbin.mvpbinding.utils.CheckNetwork;


/**
 * @author jingbin
 * @date 19/6/26
 */
public abstract class BaseActivity<P extends BasePresenter, B extends ViewDataBinding> extends AppCompatActivity implements BaseFragment.IDialogAction, LifecycleOwner {

    // 布局view
    protected B binding;
    protected P presenter;
    protected Dialog progressDialog;
    private CompositeDisposable mCompositeDisposable;
    protected ImmersionBar mImmersionBar;
    protected View loadingView;
    private View errorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        BaseActivityBinding mBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.base_activity, null, false);
        binding = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);

        // content
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        binding.getRoot().setLayoutParams(params);
        RelativeLayout mContainer = (RelativeLayout) mBaseBinding.getRoot().findViewById(R.id.container);
        mContainer.addView(binding.getRoot());
        getWindow().setContentView(mBaseBinding.getRoot());

        setTitleBar((RelativeLayout) getView(R.id.rl_title));
        showLoadView();
        binding.getRoot().setVisibility(View.GONE);

        presenter = createPresenter();
    }

    /**
     * 初始化Presenter
     */
    protected abstract P createPresenter();

    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }

    /**
     * 设置titlebar
     *
     * @param title 标题布局
     */
    protected void setTitleBar(RelativeLayout title) {
        ImageView backButton = title.findViewById(R.id.ib_base_back);
        backButton.setImageResource(R.drawable.icon_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void setTitle(CharSequence text) {
        TextView view = getView(R.id.tv_base_title);
        view.setText(text);
    }

    protected void setNoTitleBar() {
        getView(R.id.rl_title).setVisibility(View.GONE);
    }

    protected void showLoadView() {
        setLoadView();
        if (binding.getRoot().getVisibility() != View.GONE) {
            binding.getRoot().setVisibility(View.GONE);
        }
        if (errorView != null && errorView.getVisibility() != View.GONE) {
            errorView.setVisibility(View.GONE);
        }
    }

    /**
     * 这里改变加载视图
     */
    protected void setLoadView() {
        if (loadingView == null) {
            ViewStub viewStub = getView(R.id.vs_loading);
            viewStub.setLayoutResource(R.layout.layout_loading_view);
            loadingView = viewStub.inflate();
        }
        // 没有设置布局
        if (loadingView != null && loadingView.getVisibility() != View.VISIBLE) {
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    protected void showContentView() {
        if (loadingView != null && loadingView.getVisibility() != View.GONE) {
            loadingView.setVisibility(View.GONE);
        }
        if (errorView != null && errorView.getVisibility() != View.GONE) {
            errorView.setVisibility(View.GONE);
        }
        if (binding.getRoot().getVisibility() != View.VISIBLE) {
            binding.getRoot().setVisibility(View.VISIBLE);
        }
    }

    protected void showErrorView() {
        if (loadingView != null && loadingView.getVisibility() != View.GONE) {
            loadingView.setVisibility(View.GONE);
        }
        if (errorView == null) {
            ViewStub viewStub = (ViewStub) findViewById(R.id.vs_load_error);
            errorView = viewStub.inflate();
            // 点击加载失败布局
            View view = getView(R.id.title);
            if (view != null && view.getVisibility() != View.VISIBLE) {
                errorView.findViewById(R.id.iv_error_back).setVisibility(View.VISIBLE);
                errorView.findViewById(R.id.iv_error_back).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }
            errorView.findViewById(R.id.tv_refresh).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!CheckNetwork.isNetworkConnected(BaseActivity.this)) {
//                        ToastUtil.showToastLong("请检查您的网络是否正常！");
                    } else {
                        showLoadView();
                        onRefresh();
                    }
                }
            });
//            ToastUtil.showToastLong("当前网络环境较差，请稍后再试");
        } else {
            errorView.setVisibility(View.VISIBLE);
        }
        if (binding.getRoot().getVisibility() != View.GONE) {
            binding.getRoot().setVisibility(View.GONE);
        }
    }

    /**
     * 显示错误页面时，点击刷新数据
     */
    protected void onRefresh() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
            mImmersionBar = null;
        }
        if (this.mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            // clear 和 dispose的区别是：  disposed = true;
            this.mCompositeDisposable.clear();
        }
        if (presenter != null) {
            presenter.onDestroy();
            presenter = null;
        }
    }

    public void addDispose(Disposable s) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(s);
    }

    public void startProgressDialog() {
        try {
            if (progressDialog == null) {
                progressDialog = LoadDialog.buildDialog(this);
            }
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        startProgressDialog();
    }

    @Override
    public void dismiss() {
        stopProgressDialog();
    }

    /**
     * 禁止改变字体大小
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**
     * 显示白色状态栏
     * 必须在 setContentView() 之后调用
     */
    public void showWhiteImmersionBar() {
        // 当前设备支持状态栏字体变色
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            if (mImmersionBar == null) {
                mImmersionBar = ImmersionBar.with(this);
            }
            mImmersionBar
                    .statusBarView(getView(R.id.top_view))
                    .navigationBarDarkIcon(true)
                    .navigationBarEnable(false)
                    .statusBarDarkFont(true, 0.2f)
                    .init();
        }
    }

    /**
     * 单独处理键盘弹起的状况
     */
    public void showWhiteKeyboardBar() {
        // 当前设备支持状态栏字体变色
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            if (mImmersionBar == null) {
                mImmersionBar = ImmersionBar.with(this);
            }
            mImmersionBar
                    .statusBarView(getView(R.id.top_view))
                    .navigationBarDarkIcon(true)
                    .navigationBarEnable(false)
                    .keyboardEnable(true)// 键盘弹起
                    .statusBarDarkFont(true, 0.2f)
                    .init();
        }
    }

}
