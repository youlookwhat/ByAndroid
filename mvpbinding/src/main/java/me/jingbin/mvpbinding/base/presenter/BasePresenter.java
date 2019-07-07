package me.jingbin.mvpbinding.base.presenter;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.jingbin.mvpbinding.base.view.BaseView;
import me.jingbin.mvpbinding.base.view.IView;

/**
 * @author jingbin
 * @data 2019-06-30
 * @description Prestener基类
 */
public abstract class BasePresenter<V extends BaseView> implements IPresenter, LifecycleObserver {

    private CompositeDisposable mCompositeDisposable;

    protected V mView;

    public BasePresenter() {
        onStart();
    }

    /**
     * 如果当前页面不需要操作数据,只需要 View 层,则使用此构造函数
     *
     * @param rootView
     */
    public BasePresenter(V rootView) {
        if (rootView == null) {
            throw new NullPointerException(IView.class.getName() + " cannot be null");
        }
        this.mView = rootView;
        onStart();
    }

    @Override
    public void onStart() {
        //将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
        if (mView != null && mView instanceof LifecycleOwner) {
            ((LifecycleOwner) mView).getLifecycle().addObserver(this);
        }
    }


//    public void bind(V view) {
//        this.mView = view;
//    }

//    public void onClear() {
//        mView = null;
//        if (this.mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
//            this.mCompositeDisposable.clear();
//        }
//    }

    public void addDispose(Disposable s) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(s);
    }

    /**
     * 只有当 {@code mRootView} 不为 null, 并且 {@code mRootView} 实现了 {@link LifecycleOwner} 时, 此方法才会被调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(LifecycleOwner owner) {
        /**
         * 注意, 如果在这里调用了 {@link #onDestroy()} 方法, 会出现某些地方引用 {@code mModel} 或 {@code mRootView} 为 null 的情况
         * 比如在 {@link RxLifecycle} 终止 {@link Observable} 时, 在 {@link io.reactivex.Observable#doFinally(Action)} 中却引用了 {@code mRootView} 做一些释放资源的操作, 此时会空指针
         * 或者如果你声明了多个 @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY) 时在其他 @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
         * 中引用了 {@code mModel} 或 {@code mRootView} 也可能会出现此情况
         */
        owner.getLifecycle().removeObserver(this);
        mView = null;
        if (this.mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            this.mCompositeDisposable.clear();
        }
    }
}
