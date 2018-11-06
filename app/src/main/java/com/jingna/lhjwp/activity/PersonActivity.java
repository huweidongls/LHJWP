package com.jingna.lhjwp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.imagepreview.StatusBarUtils;
import com.jingna.lhjwp.utils.SpUtils;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonActivity extends BaseActivity {

    private Context context = PersonActivity.this;

    @BindView(R.id.tv_name)
    TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        StatusBarUtils.setStatusBarTransparent(PersonActivity.this);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PersonActivity.this);

        tvName.setText(SpUtils.getUsername(context));

    }

    @OnClick({R.id.activity_person_rl_back, R.id.ll_version, R.id.ll_about, R.id.tv_exit})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.activity_person_rl_back:
                finish();
                break;
            case R.id.ll_version:
                intent.setClass(context, VersionActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_about:
                intent.setClass(context, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_exit:
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setClass(PersonActivity.this, ProfessionalLoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

}
