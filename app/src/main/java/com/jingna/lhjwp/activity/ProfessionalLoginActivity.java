package com.jingna.lhjwp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.imagepreview.StatusBarUtils;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfessionalLoginActivity extends BaseActivity {

    private Context context = ProfessionalLoginActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_login);
        StatusBarUtils.setStatusBarTransparent(ProfessionalLoginActivity.this);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(ProfessionalLoginActivity.this);

    }

    @OnClick({R.id.btn_login})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.btn_login:
                intent.setClass(context, ProfessionalActivity.class);
                startActivity(intent);
                break;
        }
    }

}
