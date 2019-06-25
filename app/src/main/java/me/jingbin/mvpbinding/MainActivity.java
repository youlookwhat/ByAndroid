package me.jingbin.mvpbinding;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gyf.barlibrary.ImmersionBar;

/**
 * @author jingbin
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimary)
                .statusBarAlpha(0).init();
        ImmersionBar.setTitleBar(this, findViewById(R.id.toolBar));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
