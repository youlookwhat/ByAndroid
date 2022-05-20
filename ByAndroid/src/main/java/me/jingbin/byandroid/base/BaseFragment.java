package me.jingbin.byandroid.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import me.jingbin.byandroid.base.mvp.BasePresenter;
import me.jingbin.byandroid.R;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jingbin on 2019/6/26.
 */
public abstract class BaseFragment<P extends BasePresenter, B extends ViewDataBinding> extends Fragment {

    // ViewModel
    protected P presenter;
    // 布局view
    protected B binding;
    // 加载中
    private View loadingView;
    // 加载失败
    private View errorView;
    // 空布局
    private View emptyView;

    private Activity activity;

    private IDialogAction dialogAction;
    private CompositeSubscription mCompositeSubscription;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
        if (context instanceof IDialogAction) {
            dialogAction = (IDialogAction) context;
        } else {
            throw new IllegalArgumentException(context + " must implement interface " + BaseFragment.IDialogAction.class.getSimpleName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_base, null);
        binding = DataBindingUtil.inflate(activity.getLayoutInflater(), setContent(), null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        binding.getRoot().setLayoutParams(params);
        RelativeLayout mContainer = inflate.findViewById(R.id.container);
        mContainer.addView(binding.getRoot());
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadingView = ((ViewStub) activity.findViewById(R.id.vs_loading)).inflate();

        binding.getRoot().setVisibility(View.GONE);
        presenter = createPresenter();
    }

    /**
     * 初始化Presenter
     */
    protected abstract P createPresenter();

    interface IDialogAction {
        void show();

        void dismiss();
    }

    protected void startProgressDialog() {
        if (dialogAction != null) {
            dialogAction.show();
        }
    }

    protected void stopProgressDialog() {
        if (dialogAction != null) {
            dialogAction.dismiss();
        }
    }

    /**
     * 布局
     */
    public abstract int setContent();

    /**
     * 加载失败后点击后的操作
     */
    protected void onRefresh() {

    }

    /**
     * 显示加载中状态
     */
    protected void showLoading() {
        if (loadingView != null && loadingView.getVisibility() != View.VISIBLE) {
            loadingView.setVisibility(View.VISIBLE);
        }
        if (binding.getRoot().getVisibility() != View.GONE) {
            binding.getRoot().setVisibility(View.GONE);
        }
        if (errorView != null) {
            errorView.setVisibility(View.GONE);
        }
        if (emptyView != null && emptyView.getVisibility() != View.GONE) {
            emptyView.setVisibility(View.GONE);
        }
    }

    /**
     * 加载完成的状态
     */
    protected void showContentView() {
        if (binding.getRoot().getVisibility() != View.VISIBLE) {
            binding.getRoot().setVisibility(View.VISIBLE);
        }
        if (loadingView != null && loadingView.getVisibility() != View.GONE) {
            loadingView.setVisibility(View.GONE);
        }
        if (errorView != null) {
            errorView.setVisibility(View.GONE);
        }
        if (emptyView != null && emptyView.getVisibility() != View.GONE) {
            emptyView.setVisibility(View.GONE);
        }
    }

    /**
     * 加载失败点击重新加载的状态
     */
    protected void showError() {
        ViewStub viewStub = getView(R.id.vs_error_refresh);
        if (viewStub != null) {
            errorView = viewStub.inflate();
            // 点击加载失败布局
            errorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoading();
                    onRefresh();
                }
            });
        }
        if (errorView != null) {
            errorView.setVisibility(View.VISIBLE);
        }
        if (loadingView != null && loadingView.getVisibility() != View.GONE) {
            loadingView.setVisibility(View.GONE);
        }
        if (binding.getRoot().getVisibility() != View.GONE) {
            binding.getRoot().setVisibility(View.GONE);
        }
        if (emptyView != null && emptyView.getVisibility() != View.GONE) {
            emptyView.setVisibility(View.GONE);
        }
    }

    protected void showEmptyView(String text) {
        // 需要这样处理，否则二次显示会失败
        ViewStub viewStub = getView(R.id.vs_empty);
        if (viewStub != null) {
            emptyView = viewStub.inflate();
            ((TextView) emptyView.findViewById(R.id.tv_tip_empty)).setText(text);
        }
        if (emptyView != null) {
            emptyView.setVisibility(View.VISIBLE);
        }

        if (loadingView != null && loadingView.getVisibility() != View.GONE) {
            loadingView.setVisibility(View.GONE);
        }
        if (errorView != null) {
            errorView.setVisibility(View.GONE);
        }
        if (binding != null && binding.getRoot().getVisibility() != View.GONE) {
            binding.getRoot().setVisibility(View.GONE);
        }
    }

    protected <T extends View> T getView(int id) {
        return (T) getView().findViewById(id);
    }

    public void addDispose(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
        if (presenter != null) {
            presenter.onDestroy();
            presenter = null;
        }
    }
}
