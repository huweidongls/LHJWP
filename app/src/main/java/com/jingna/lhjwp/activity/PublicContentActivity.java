package com.jingna.lhjwp.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.adapter.PicAddShowAdapter;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.info.PublicInfo;
import com.jingna.lhjwp.utils.ShareUtils;
import com.jingna.lhjwp.utils.SpUtils;
import com.jingna.lhjwp.utils.ToastUtil;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublicContentActivity extends BaseActivity {

    private Context context = PublicContentActivity.this;

    @BindView(R.id.activity_public_content_rv_pic)
    RecyclerView recyclerView;
    @BindView(R.id.activity_public_content_rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.activity_public_content_tv_bottom)
    TextView tvBottom;
    @BindView(R.id.activity_public_content_tv_top)
    TextView tvTop;

    private PicAddShowAdapter adapter;
    private ArrayList<PublicInfo.PicInfo> mList = new ArrayList<>();

    private PopupWindow popupWindow;

    private int position;
    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_content);
        position = getIntent().getIntExtra("position", 0);
        title = getIntent().getStringExtra("title");
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PublicContentActivity.this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {

        tvTop.setText(title);
        mList.clear();
        mList.addAll(SpUtils.getPublicInfo(context).get(position).getPicList());
        GridLayoutManager manager = new GridLayoutManager(PublicContentActivity.this, 3);
        recyclerView.setLayoutManager(manager);
        adapter = new PicAddShowAdapter(mList);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new PicAddShowAdapter.OnAddImgListener() {
            @Override
            public void onAddImg() {
                Intent intent = new Intent();
                intent.putExtra("position", position);
                intent.putExtra("title", title);
                intent.setClass(PublicContentActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

    }

    @OnClick({R.id.activity_public_content_rl_back, R.id.activity_public_content_rl_more, R.id.activity_public_content_tv_bottom})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_public_content_rl_back:
                if(adapter.getEdit()){
                    adapter.setEdit(false);
                    ivBack.setVisibility(View.VISIBLE);
                    tvCancel.setVisibility(View.GONE);
                    tvBottom.setText("发送");
                    tvBottom.setBackgroundColor(Color.parseColor("#2276F6"));
                }else {
                    finish();
                }
                break;
            case R.id.activity_public_content_rl_more:
                initMorePop();
                break;
            case R.id.activity_public_content_tv_bottom:
                if(adapter.getEdit()){
                    ArrayList<PublicInfo> list = SpUtils.getPublicInfo(context);
                    List<Integer> editList = adapter.getEditList();
                    Collections.sort(editList);
                    Collections.reverse(editList);
//                Log.e("121212", mList.size()+"--"+editList.size());
                    for (int i = 0; i<editList.size(); i++){
                        int num = editList.get(i);
                        File file = new File(mList.get(num).getPicPath());
                        file.delete();
                        mList.remove(num);
                        list.get(position).getPicList().remove(num);
                    }
                    adapter.setEdit(false);
                    adapter.notifyDataSetChanged();
                    SpUtils.setPublicInfo(context, list);
                    ivBack.setVisibility(View.VISIBLE);
                    tvCancel.setVisibility(View.GONE);
                    tvBottom.setText("发送");
                    tvBottom.setBackgroundColor(Color.parseColor("#2276F6"));
                }else {
                    List<File> fileList = new ArrayList<>();
                    for (int i = 0; i<mList.size(); i++){
                        fileList.add(new File(mList.get(i).getPicPath()));
                    }
                    ShareUtils.startShare(0, "测试", fileList, context);
                }
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
                if (mList.size()>0){
                    intent.putExtra("position", position);
                    intent.putExtra("title", title);
                    intent.setClass(PublicContentActivity.this, PublicPicLocationActivity.class);
                    startActivity(intent);
                }else {
                    ToastUtil.showShort(context, "暂无图片信息");
                }
                popupWindow.dismiss();
            }
        });
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setEdit(true);
                ivBack.setVisibility(View.GONE);
                tvCancel.setVisibility(View.VISIBLE);
                tvBottom.setText("删除");
                tvBottom.setBackgroundColor(Color.parseColor("#FF3A30"));
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

    @Override
    public void onBackPressed() {
        if(adapter.getEdit()){
            adapter.setEdit(false);
            ivBack.setVisibility(View.VISIBLE);
            tvCancel.setVisibility(View.GONE);
            tvBottom.setText("发送");
            tvBottom.setBackgroundColor(Color.parseColor("#2276F6"));
        }else {
            finish();
        }
    }
}
