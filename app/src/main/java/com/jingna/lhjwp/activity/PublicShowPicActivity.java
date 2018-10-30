package com.jingna.lhjwp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.utils.SpUtils;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublicShowPicActivity extends BaseActivity {

    private Context context = PublicShowPicActivity.this;

    @BindView(R.id.iv_show)
    ImageView ivShow;
    @BindView(R.id.activity_public_show_pic_tv_top)
    TextView tvTop;

    private String path;

    private int position;
    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_show_pic);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PublicShowPicActivity.this);
        init();

    }

    private void init() {

        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        position = intent.getIntExtra("position", 0);
        title = intent.getStringExtra("title");
        tvTop.setText(title);
        Log.e("123123", path);
        Glide.with(context).load("file://"+path).into(ivShow);

    }

    @OnClick({R.id.activity_public_show_pic_rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_public_show_pic_rl_back:
                finish();
                break;
        }
    }

}
