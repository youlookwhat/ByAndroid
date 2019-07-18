package me.jingbin.mvpbinding.base.refresh;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * @author jingbin
 * @data 2019/1/19
 * @description
 */

public class JViewModel extends AndroidViewModel {

    private int loadOffset = initLoadOffset();

    public JViewModel(@NonNull Application application) {
        super(application);
    }

    public void onDestroy() {

    }

    public void onListRefresh() {
        loadOffset = initLoadOffset();
    }

    public void onListLoad(int offset) {
    }

    public int getLoadOffset() {
        return ++loadOffset;
    }

    /**
     * 超时时间(单位：s)
     */
    public int timeout() {
        return 20;
    }

    public int initLoadOffset() {
        return 0;
    }
}
