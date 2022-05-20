package me.jingbin.byandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;


/**
 * Created by jingbin on 2020-03-16.
 */
public class StatusImmersionUtil {

    public static void showTransparentBar(Activity activity) {
        showTransparentBar(activity, true);
    }

    /**
     * 状态栏透明，导航栏白色/透明度0.9f/导航栏按钮黑色
     */
    public static void showTransparentBar(Activity activity, boolean isDarkFont) {
        if (isDarkFont) {
            ImmersionBar.with(activity)
                    .transparentStatusBar()
                    .navigationBarEnable(false)
                    .statusBarDarkFont(true, 0.2f)
                    // 键盘弹起处理
                    .keyboardEnable(true)
                    .init();
        } else {
            ImmersionBar.with(activity)
                    .transparentStatusBar()
                    .navigationBarEnable(false)
                    // 键盘弹起处理
                    .keyboardEnable(true)
                    .init();
        }
    }

    /**
     * 设置标题栏MarginTop值为状态栏的高度
     */
    public static void setMarginTop(Activity activity, View view) {
        ImmersionBar.setTitleBarMarginTop(activity, view);
    }

    /**
     * 状态栏透明 & 设置标题栏PaddingTop值为状态栏的高度
     */
    public static void setPaddingTop(Activity activity, View view) {
        ImmersionBar.setTitleBar(activity, view);
    }

    /**
     * 状态栏透明 & 设置标题栏MarginTop值为状态栏的高度
     */
    public static void showTransparentMargin(Activity activity, View view) {
        showTransparentBar(activity);
        setMarginTop(activity, view);
    }

    public static void showTransparentMargin(Activity activity, View view, boolean isDarkFont) {
        showTransparentBar(activity, isDarkFont);
        setMarginTop(activity, view);
    }

    /**
     * 状态栏透明 & 设置标题栏PaddingTop值为状态栏的高度
     */
    public static void showTransparentPadding(Activity activity, View view, boolean isDarkFont) {
        showTransparentBar(activity, isDarkFont);
        setPaddingTop(activity, view);
    }

    /**
     * 状态栏和导航栏均透明
     */
    public static void showTransparentAllBar(Activity activity) {
        ImmersionBar.with(activity).transparentBar().init();
    }

    /**
     * 获取导航栏高度
     */
    public static int getNavigationBarHeight(Activity activity) {
        return ImmersionBar.getNavigationBarHeight(activity);
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

}
