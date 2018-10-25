package com.jingna.lhjwp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.adapter.PicAddShowAdapter;
import com.jingna.lhjwp.base.BaseActivity;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublicContentActivity extends BaseActivity {

    @BindView(R.id.activity_public_content_rv_pic)
    RecyclerView recyclerView;

    private PicAddShowAdapter adapter;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_content);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PublicContentActivity.this);
        initData();

    }

    private void initData() {

        GridLayoutManager manager = new GridLayoutManager(PublicContentActivity.this, 3);
        recyclerView.setLayoutManager(manager);
        adapter = new PicAddShowAdapter(mList);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new PicAddShowAdapter.OnAddImgListener() {
            @Override
            public void onAddImg() {
                Intent intent = new Intent();
                intent.setClass(PublicContentActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

    }

    @OnClick({R.id.activity_public_content_rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_public_content_rl_back:
                finish();
                break;
        }
    }

}
