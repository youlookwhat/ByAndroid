package me.jingbin.mvpbinding.base;

import android.content.Context;

import androidx.fragment.app.Fragment;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jingbin on 2019/6/26.
 */
public class BaseFragment extends Fragment {

    private IDialogAction dialogAction;
    private CompositeSubscription mCompositeSubscription;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IDialogAction) {
            dialogAction = (IDialogAction) context;
        } else {
            throw new IllegalArgumentException(context + " must implement interface " + BaseFragment.IDialogAction.class.getSimpleName());
        }
    }

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
    }
}
