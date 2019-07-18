package me.jingbin.mvpbinding.base.refresh;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import me.jingbin.mvpbinding.R;

/**
 * @author jingbin
 * @data 2019/1/19
 * @description
 */

public abstract class JQuickAdapter<T, B extends ViewDataBinding> extends BaseQuickAdapter<T, JViewHolder<B>> implements BaseQuickAdapter.RequestLoadMoreListener {

    private boolean loaded = false;

    public JQuickAdapter(int layoutResId) {
        super(layoutResId);
    }

    public JQuickAdapter(@Nullable List<T> data) {
        super(data);
    }

    public JQuickAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }


    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        if (layoutResId != mLayoutResId) {
            return super.getItemView(layoutResId, parent);
        }

        B binding = DataBindingUtil.inflate(
                mLayoutInflater,
                layoutResId,
                parent,
                false);
        onBinding(binding);
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }

    @Override
    protected void convert(JViewHolder<B> helper, T item) {

    }

    /**
     * 已初始化Binding
     */
    protected abstract void onBinding(B binding);

    /**
     * 绑定RecyclerView
     *
     * @param load 是否需要加载
     */
    public void bindToRecyclerView(RecyclerView recyclerView, boolean load) {
        super.bindToRecyclerView(recyclerView);

        if (load) {
            this.setOnLoadMoreListener(this, recyclerView);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if (loaded) {
            this.loadMoreEnd();
            return;
        }
        JViewModel viewModel = getViewModel();
        if (viewModel == null) {
            return;
        }
        viewModel.onListLoad(viewModel.getLoadOffset());
    }

    @Nullable
    protected JViewModel getViewModel() {
        return null;
    }

    public void setLoaded(boolean end) {
        loaded = end;
        if (!end) {
            this.loadMoreComplete();
        }
    }
}
