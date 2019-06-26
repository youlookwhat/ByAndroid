package me.jingbin.mvpbinding.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * Created by jingbin on 2018/11/26.
 */

public class BaseDialogFragment extends AppCompatDialogFragment {

    /**
     * 防止内存泄漏:
     * 声明周期：onCreate - onCreateView - onActivityCreated
     * 源码在 onActivityCreated 设置的监听
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getDialog() != null) {
            // 防止内存泄漏 https://segmentfault.com/q/1010000017286787
            getDialog().setOnShowListener(null);
            getDialog().setOnCancelListener(null);
//            getDialog().setOnDismissListener(null);
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}
