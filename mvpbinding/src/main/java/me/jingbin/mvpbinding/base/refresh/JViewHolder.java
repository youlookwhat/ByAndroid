package me.jingbin.mvpbinding.base.refresh;

import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.BaseViewHolder;

import me.jingbin.mvpbinding.R;

/**
 * @author jingbin
 * @data 2019/1/19
 * @description
 */

public class JViewHolder <B extends ViewDataBinding> extends BaseViewHolder {
    public final B binding;

    public JViewHolder(View view) {
        super(view);
        this.binding = (B) view.getTag(R.id.BaseQuickAdapter_databinding_support);
    }

}
