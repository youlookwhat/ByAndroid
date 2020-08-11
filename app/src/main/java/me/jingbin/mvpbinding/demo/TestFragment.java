package me.jingbin.mvpbinding.demo;

import android.content.Context;
import android.os.Bundle;

import me.jingbin.bymvp.base.BaseFragment;
import me.jingbin.bymvp.base.mvp.NoPresenter;
import me.jingbin.mvpbinding.demo.databinding.FragmentTestBinding;

/**
 * @author jingbin
 */
public class TestFragment extends BaseFragment<NoPresenter, FragmentTestBinding> {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public static TestFragment newInstance(String param1, String param2) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public int setContent() {
        return R.layout.fragment_test;
    }

    @Override
    protected NoPresenter createPresenter() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
