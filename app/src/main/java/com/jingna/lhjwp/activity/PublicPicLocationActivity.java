package com.jingna.lhjwp.activity;

import android.content.Context;
import android.os.Bundle;

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import butterknife.ButterKnife;

public class PublicPicLocationActivity extends BaseActivity {

    private Context context = PublicPicLocationActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_pic_location);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PublicPicLocationActivity.this);

    }
}
