package com.jingna.lhjwp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.imagepreview.StatusBarUtils;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TypeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        StatusBarUtils.setStatusBarTransparent(TypeActivity.this);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(TypeActivity.this);

    }

    @OnClick({R.id.btn1, R.id.btn2})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.btn1:
                intent.setClass(TypeActivity.this, PublicActivity.class);
                startActivity(intent);
                break;
            case R.id.btn2:
                intent.setClass(TypeActivity.this, ProfessionalLoginActivity.class);
                startActivity(intent);
                break;
        }
    }

}
