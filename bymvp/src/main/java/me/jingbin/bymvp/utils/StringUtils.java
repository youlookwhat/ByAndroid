package me.jingbin.bymvp.utils;

import android.content.Context;

/**
 * @author yeqing
 * @description
 * @time 2019/9/16
 */
public class StringUtils {
    public static String getString(Context context, int sourceId) {
        return context.getResources().getString(sourceId);
    }
}
