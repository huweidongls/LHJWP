package com.jingna.lhjwp.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.adapter.PicAddShowAdapter;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.utils.ToastUtil;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublicContentActivity extends BaseActivity {

    @BindView(R.id.activity_public_content_rv_pic)
    RecyclerView recyclerView;
    @BindView(R.id.activity_public_content_rl_top)
    RelativeLayout rlTop;

    private PicAddShowAdapter adapter;
    private List<String> mList = new ArrayList<>();

    private PopupWindow popupWindow;

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

    @OnClick({R.id.activity_public_content_rl_back, R.id.activity_public_content_rl_more})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_public_content_rl_back:
                finish();
                break;
            case R.id.activity_public_content_rl_more:
                initMorePop();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void initMorePop() {

        View view = LayoutInflater.from(PublicContentActivity.this).inflate(R.layout.popupwindow_more, null);
        ScreenAdapterTools.getInstance().loadView(view);

        final Intent intent = new Intent();
        LinearLayout ll1 = view.findViewById(R.id.ll1);
        LinearLayout ll2 = view.findViewById(R.id.ll2);
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(PublicContentActivity.this, PublicPicLocationActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort(PublicContentActivity.this, "编辑");
                popupWindow.dismiss();
            }
        });

        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
//        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        popupWindow.showAsDropDown(rlTop, 0, 20, Gravity.RIGHT);
        // 设置popWindow的显示和消失动画
//        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.alpha = 0.5f;
//        getWindow().setAttributes(params);
        popupWindow.update();

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
//                WindowManager.LayoutParams params = getWindow().getAttributes();
//                params.alpha = 1f;
//                getWindow().setAttributes(params);
            }
        });

    }

}
