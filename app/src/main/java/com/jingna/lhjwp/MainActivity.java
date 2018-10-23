package com.jingna.lhjwp;

import android.os.Bundle;

import com.jingna.lhjwp.base.BaseActivity;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

    }
}
