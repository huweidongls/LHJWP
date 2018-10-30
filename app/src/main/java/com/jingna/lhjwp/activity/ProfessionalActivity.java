package com.jingna.lhjwp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfessionalActivity extends BaseActivity {

    private Context context = ProfessionalActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(ProfessionalActivity.this);

    }

    @OnClick({R.id.ll1})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.ll1:
                intent.setClass(context, RukuListActivity.class);
                startActivity(intent);
                break;
        }
    }

}
