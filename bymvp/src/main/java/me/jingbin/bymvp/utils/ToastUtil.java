package me.jingbin.bymvp.utils;

import android.text.TextUtils;

import es.dmoral.toasty.Toasty;
import me.jingbin.bymvp.base.RootApplication;

public class ToastUtil {

    public static void showToastLong(String text) {
        apply(text, Toasty.LENGTH_LONG);
    }

    public static void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            apply(text, Toasty.LENGTH_SHORT);
        }
    }

    private static void apply(String text, int duration) {
        Toasty.Config.getInstance().setTextSize(14).apply();
        Toasty.normal(RootApplication.getContext(), text, duration).show();
    }
}
