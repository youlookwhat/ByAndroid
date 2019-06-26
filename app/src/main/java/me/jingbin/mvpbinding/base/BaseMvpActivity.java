package me.jingbin.mvpbinding.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.gyf.barlibrary.ImmersionBar;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.jingbin.mvpbinding.R;
import me.jingbin.mvpbinding.databinding.BaseActivityBinding;
import me.jingbin.mvpbinding.utils.CheckNetwork;


/**
 * Created by jingbin on 19/6/26.
 */
public abstract class BaseMvpActivity<SV extends ViewDataBinding> extends BaseActivity {

    // 布局view
    protected SV binding;
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

        setTitleBar(getView(R.id.title));
        showLoadView();
        binding.getRoot().setVisibility(View.GONE);
    }

    /**
     * 设置titlebar
     *
     * @param title
     */
    protected void setTitleBar(RelativeLayout title) {
        ImageView backButton = title.findViewById(R.id.ib_base_back);
        backButton.setOnClickListener(v -> {
            try {
                onBackPressed();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void setTitle(CharSequence text) {
        TextView view = getView(R.id.tv_base_title);
        view.setText(text);
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
                errorView.findViewById(R.id.iv_error_back).setOnClickListener(v -> finish());
            }
            errorView.findViewById(R.id.tv_refresh).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!CheckNetwork.isNetworkConnected(BaseMvpActivity.this)) {
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
     * 显示错误页面刷新数据
     */
    protected  void onRefresh(){

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
    }

    @Override
    public void addSubscription(Disposable s) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(s);
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
//                    .navigationBarDarkIcon(true)
//                    .navigationBarColorTransform(R.color.colorWhite)
//                    .navigationBarAlpha(0.9f)
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
//                    .navigationBarDarkIcon(true)
//                    .navigationBarColorTransform(R.color.colorWhite)
//                    .navigationBarAlpha(0.9f)// 虚拟键盘
                    .keyboardEnable(true)// 键盘弹起
                    .statusBarDarkFont(true, 0.2f)
                    .init();
        }
    }

}
