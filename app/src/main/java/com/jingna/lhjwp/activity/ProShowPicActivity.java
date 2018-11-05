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
import com.jingna.lhjwp.dialog.CustomDialog;
import com.jingna.lhjwp.info.ProPicInfo;
import com.jingna.lhjwp.utils.DateUtils;
import com.jingna.lhjwp.utils.SpUtils;
import com.jingna.lhjwp.utils.ToastUtil;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProShowPicActivity extends BaseActivity {

    private Context context = ProShowPicActivity.this;

    @BindView(R.id.iv_show)
    ImageView ivShow;
    @BindView(R.id.activity_public_show_pic_tv_top)
    TextView tvTop;

    private String path;
    private String uuid = "";
    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_show_pic2);
        title = getIntent().getStringExtra("title");
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(ProShowPicActivity.this);
        init();

    }

    private void init() {

        tvTop.setText(title);
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        uuid = intent.getStringExtra("uuid");
        Log.e("123123", path);
        Glide.with(context).load("file://"+path).into(ivShow);

    }

    @OnClick({R.id.activity_public_show_pic_rl_back, R.id.rl_delete})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_public_show_pic_rl_back:
                finish();
                break;
            case R.id.rl_delete:
                CustomDialog customDialog = new CustomDialog(context, "是否删除照片", new CustomDialog.OnSureListener() {
                    @Override
                    public void onSure() {
                        File file = new File(path);
                        if(file.delete()){
                            Map<String, ArrayList<ProPicInfo>> map = SpUtils.getProPicInfo(context);
                            ArrayList<ProPicInfo> list = map.get(uuid);
                            list.remove(list.size()-1);
                            map.put(uuid, list);
                            SpUtils.setProPicInfo(context, map);
                            finish();
                        }else {
                            ToastUtil.showShort(context, "图片删除失败");
                        }
                    }
                });
                customDialog.show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        SpUtils.setLastTime(context, DateUtils.stampToDateSecond(System.currentTimeMillis()+""));
        finish();
    }
}
