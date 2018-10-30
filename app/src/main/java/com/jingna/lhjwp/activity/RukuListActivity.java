package com.jingna.lhjwp.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RukuListActivity extends BaseActivity {

    private Context context = RukuListActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruku_list);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(RukuListActivity.this);

    }

    @OnClick({R.id.activity_ruku_list_rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_ruku_list_rl_back:
                finish();
                break;
        }
    }

}
