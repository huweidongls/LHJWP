package com.jingna.lhjwp.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.adapter.ActivityProRukuListAdapter;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.model.RukuListModel;
import com.jingna.lhjwp.utils.SpUtils;
import com.jingna.lhjwp.utils.WeiboDialogUtils;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.mode.CacheMode;
import com.vise.xsnow.http.mode.CacheResult;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RukuListActivity extends BaseActivity {

    private Context context = RukuListActivity.this;

    @BindView(R.id.activity_ruku_list_rv)
    RecyclerView recyclerView;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rl_pro)
    RelativeLayout rlPro;
    @BindView(R.id.tv_pro)
    TextView tvPro;
    @BindView(R.id.iv_pro)
    ImageView ivPro;
    @BindView(R.id.rl_type)
    RelativeLayout rlType;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.iv_type)
    ImageView ivType;
    @BindView(R.id.rl_sort)
    RelativeLayout rlSort;
    @BindView(R.id.tv_sort)
    TextView tvSort;
    @BindView(R.id.iv_sort)
    ImageView ivSort;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private String type = "";
    private boolean isNet = true;

    private ActivityProRukuListAdapter adapter;
    private List<RukuListModel.XmListBean> mList = new ArrayList<>();
    private List<RukuListModel.XmListBean> mData = new ArrayList<>();

    private PopupWindow popupWindow;

    private int popPro = 0;
    private int popType = 0;
    private int popSort = 0;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruku_list);
        isNet = getIntent().getBooleanExtra("isnet", false);
        type = getIntent().getStringExtra("type");
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(RukuListActivity.this);

        initData();

    }

    private void initData() {

        if(type.equals("1")){
            tvTitle.setText("入库项目采集");
        }else if(type.equals("2")){
            tvTitle.setText("进度项目采集");
        }else if(type.equals("3")){
            tvTitle.setText("变更项目采集");
        }else if(type.equals("4")){
            tvTitle.setText("竣工项目采集");
        }

        if(isNet){
            String name = SpUtils.getUsername(context);
            String S_ORGAN = SpUtils.getS_ORGAN(context);
            String url = "/tzapp/phone/getXmList.action?type="+type+"&user_name="+name+"&S_ORGAN="+S_ORGAN;
            ViseHttp.GET(url)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Log.e("123123", data);
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if(jsonObject.getString("result").equals("1")){
                                    Gson gson = new Gson();
                                    RukuListModel model = gson.fromJson(data, RukuListModel.class);
                                    Map<String, RukuListModel> map = SpUtils.getRukuCache(context);
                                    if(map == null){
                                        map = new LinkedHashMap<>();
                                    }
                                    map.put(type, model);
                                    SpUtils.setRukuCache(context, map);
                                    LinearLayoutManager manager = new LinearLayoutManager(context);
                                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                                    recyclerView.setLayoutManager(manager);
                                    mList.addAll(model.getXmList());
                                    mData.addAll(model.getXmList());
                                    Log.e("123123", mData.size()+"");
                                    adapter = new ActivityProRukuListAdapter(context, mList);
                                    recyclerView.setAdapter(adapter);
                                    etSearch.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                                            adapter.getFilter().filter(sequence.toString());
                                        }

                                        @Override
                                        public void afterTextChanged(Editable editable) {

                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        }else {
            Map<String, RukuListModel> listModelMap = SpUtils.getRukuCache(context);
            RukuListModel listModel = listModelMap.get(type);
            if(listModel!=null){
                LinearLayoutManager manager = new LinearLayoutManager(context);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(manager);
                mList.addAll(listModel.getXmList());
                mData.addAll(listModel.getXmList());
                Log.e("123123", mData.size()+"");
                adapter = new ActivityProRukuListAdapter(context, mList);
                recyclerView.setAdapter(adapter);
                etSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                        adapter.getFilter().filter(sequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        }

    }

    @OnClick({R.id.activity_ruku_list_rl_back, R.id.rl_pro, R.id.rl_type, R.id.rl_sort, R.id.rl_refresh})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.activity_ruku_list_rl_back:
                finish();
                break;
//            case R.id.iv_record:
//                intent.setClass(context, VoiceActivity.class);
//                startActivityForResult(intent, 1);
//                break;
            case R.id.rl_pro:
                tvPro.setTextColor(Color.parseColor("#007AFF"));
                Glide.with(context).load(R.drawable.to_top).into(ivPro);
                showProPop();
                break;
            case R.id.rl_type:
                tvType.setTextColor(Color.parseColor("#007AFF"));
                Glide.with(context).load(R.drawable.to_top).into(ivType);
                showTypePop();
                break;
            case R.id.rl_sort:
                tvSort.setTextColor(Color.parseColor("#007AFF"));
                Glide.with(context).load(R.drawable.to_top).into(ivSort);
                showSortPop();
                break;
            case R.id.rl_refresh:
                dialog = WeiboDialogUtils.createLoadingDialog(context, "请等待...");
                String name = SpUtils.getUsername(context);
                String S_ORGAN = SpUtils.getS_ORGAN(context);
                String url = "/tzapp/phone/getXmList.action?type="+type+"&user_name="+name+"&S_ORGAN="+S_ORGAN;
                ViseHttp.GET(url)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("123123", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getString("result").equals("1")){
                                        Gson gson = new Gson();
                                        RukuListModel model = gson.fromJson(data, RukuListModel.class);
                                        mList.clear();
                                        mData.clear();
                                        mList.addAll(model.getXmList());
                                        mData.addAll(model.getXmList());
                                        adapter.notifyDataSetChanged();
                                        popPro = 0;
                                        popType = 0;
                                        popSort = 0;
                                        tvPro.setText("专业/全部");
                                        tvType.setText("状态/全部");
                                        tvSort.setText("排序");
                                    }
                                    WeiboDialogUtils.closeDialog(dialog);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                WeiboDialogUtils.closeDialog(dialog);
                            }
                        });
                break;
        }
    }

    private void showSortPop() {

        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow_sort, null);
        ScreenAdapterTools.getInstance().loadView(view);

        RelativeLayout rl1 = view.findViewById(R.id.pop_rl1);
        RelativeLayout rl2 = view.findViewById(R.id.pop_rl2);
        TextView tv1 = view.findViewById(R.id.pop_tv1);
        TextView tv2 = view.findViewById(R.id.pop_tv2);

        if(popSort == 0){
            tv1.setTextColor(Color.parseColor("#007AFF"));
            tv2.setTextColor(Color.parseColor("#404040"));
        }else if(popSort == 1){
            tv1.setTextColor(Color.parseColor("#404040"));
            tv2.setTextColor(Color.parseColor("#007AFF"));
        }

        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popSort != 0){
                    Collections.reverse(mList);
                }
                tvSort.setText("排序/正序");
                popSort = 0;
                popupWindow.dismiss();
                adapter.notifyDataSetChanged();
            }
        });

        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popSort != 1){
                    Collections.reverse(mList);
                }
                tvSort.setText("排序/倒序");
                popSort = 1;
                popupWindow.dismiss();
                adapter.notifyDataSetChanged();
            }
        });

        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
//        popupWindow.showAtLocation(cameraView, Gravity.RIGHT|Gravity.BOTTOM, 0, y+20);
        popupWindow.showAsDropDown(rlPro);
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
                tvSort.setTextColor(Color.parseColor("#656565"));
                Glide.with(context).load(R.drawable.to_bottom).into(ivSort);
            }
        });

    }

    private void showTypePop() {

        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow_type, null);
        ScreenAdapterTools.getInstance().loadView(view);

        RelativeLayout rl1 = view.findViewById(R.id.pop_rl1);
        RelativeLayout rl2 = view.findViewById(R.id.pop_rl2);
        RelativeLayout rl3 = view.findViewById(R.id.pop_rl3);
        TextView tv1 = view.findViewById(R.id.pop_tv1);
        TextView tv2 = view.findViewById(R.id.pop_tv2);
        TextView tv3 = view.findViewById(R.id.pop_tv3);

        if(popType == 0){
            tv1.setTextColor(Color.parseColor("#007AFF"));
            tv2.setTextColor(Color.parseColor("#404040"));
            tv3.setTextColor(Color.parseColor("#404040"));
        }else if(popType == 1){
            tv1.setTextColor(Color.parseColor("#404040"));
            tv2.setTextColor(Color.parseColor("#007AFF"));
            tv3.setTextColor(Color.parseColor("#404040"));
        }else if(popType == 2){
            tv1.setTextColor(Color.parseColor("#404040"));
            tv2.setTextColor(Color.parseColor("#404040"));
            tv3.setTextColor(Color.parseColor("#007AFF"));
        }

        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvType.setText("状态/全部");
                popType = 0;
                popupWindow.dismiss();
                mList.clear();
                if(popPro == 0){
                    mList.addAll(mData);
                }else if(popPro == 1){
                    for (int i = 0; i<mData.size(); i++){
                        if(mData.get(i).getS_ZY().equals("1")){
                            mList.add(mData.get(i));
                        }
                    }
                }else if(popPro == 2){
                    for (int i = 0; i<mData.size(); i++){
                        if(mData.get(i).getS_ZY().equals("2")){
                            mList.add(mData.get(i));
                        }
                    }
                }
                if(popSort == 1){
                    Collections.reverse(mList);
                }
                adapter.notifyDataSetChanged();
            }
        });

        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvType.setText("状态/已上报");
                popType = 1;
                popupWindow.dismiss();
                mList.clear();
                for (int i = 0; i<mData.size(); i++){
                    if(popPro == 0){
                        if(mData.get(i).getS_UP_FLAG().equals("1")){
                            mList.add(mData.get(i));
                        }
                    }else if(popPro == 1){
                        if(mData.get(i).getS_UP_FLAG().equals("1")&&mData.get(i).getS_ZY().equals("1")){
                            mList.add(mData.get(i));
                        }
                    }else if(popPro == 2){
                        if(mData.get(i).getS_UP_FLAG().equals("1")&&mData.get(i).getS_ZY().equals("2")){
                            mList.add(mData.get(i));
                        }
                    }
                }
                if(popSort == 1){
                    Collections.reverse(mList);
                }
                adapter.notifyDataSetChanged();
            }
        });

        rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvType.setText("状态/未上报");
                popType = 2;
                popupWindow.dismiss();
                mList.clear();
                for (int i = 0; i<mData.size(); i++){
                    if(popPro == 0){
                        if(mData.get(i).getS_UP_FLAG().equals("0")){
                            mList.add(mData.get(i));
                        }
                    }else if(popPro == 1){
                        if(mData.get(i).getS_UP_FLAG().equals("0")&&mData.get(i).getS_ZY().equals("1")){
                            mList.add(mData.get(i));
                        }
                    }else if(popPro == 2){
                        if(mData.get(i).getS_UP_FLAG().equals("0")&&mData.get(i).getS_ZY().equals("2")){
                            mList.add(mData.get(i));
                        }
                    }
                }
                if(popSort == 1){
                    Collections.reverse(mList);
                }
                adapter.notifyDataSetChanged();
            }
        });

        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
//        popupWindow.showAtLocation(cameraView, Gravity.RIGHT|Gravity.BOTTOM, 0, y+20);
        popupWindow.showAsDropDown(rlPro);
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
                tvType.setTextColor(Color.parseColor("#656565"));
                Glide.with(context).load(R.drawable.to_bottom).into(ivType);
            }
        });

    }

    private void showProPop() {

        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow_pro, null);
        ScreenAdapterTools.getInstance().loadView(view);

        RelativeLayout rl1 = view.findViewById(R.id.pop_rl1);
        RelativeLayout rl2 = view.findViewById(R.id.pop_rl2);
        RelativeLayout rl3 = view.findViewById(R.id.pop_rl3);
        TextView tv1 = view.findViewById(R.id.pop_tv1);
        TextView tv2 = view.findViewById(R.id.pop_tv2);
        TextView tv3 = view.findViewById(R.id.pop_tv3);

        if(popPro == 0){
            tv1.setTextColor(Color.parseColor("#007AFF"));
            tv2.setTextColor(Color.parseColor("#404040"));
            tv3.setTextColor(Color.parseColor("#404040"));
        }else if(popPro == 1){
            tv1.setTextColor(Color.parseColor("#404040"));
            tv2.setTextColor(Color.parseColor("#007AFF"));
            tv3.setTextColor(Color.parseColor("#404040"));
        }else if(popPro == 2){
            tv1.setTextColor(Color.parseColor("#404040"));
            tv2.setTextColor(Color.parseColor("#404040"));
            tv3.setTextColor(Color.parseColor("#007AFF"));
        }

        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPro.setText("专业/全部");
                popPro = 0;
                popupWindow.dismiss();
                mList.clear();
                if(popType == 0&&popSort == 0){
                    mList.addAll(mData);
                }else if(popType == 1&&popSort == 0){
                    for (int i = 0; i<mData.size(); i++){
                        if(mData.get(i).getS_UP_FLAG().equals("1")){
                            mList.add(mData.get(i));
                        }
                    }
                }else if(popType == 2&&popSort == 0){
                    for (int i = 0; i<mData.size(); i++){
                        if(mData.get(i).getS_UP_FLAG().equals("0")){
                            mList.add(mData.get(i));
                        }
                    }
                }else if(popType == 0&&popSort == 1){
                    mList.addAll(mData);
                    Collections.reverse(mList);
                }else if(popType == 1&&popSort == 1){
                    for (int i = 0; i<mData.size(); i++){
                        if(mData.get(i).getS_UP_FLAG().equals("1")){
                            mList.add(mData.get(i));
                        }
                    }
                    Collections.reverse(mList);
                }else if(popType == 2&&popSort == 1){
                    for (int i = 0; i<mData.size(); i++){
                        if(mData.get(i).getS_UP_FLAG().equals("0")){
                            mList.add(mData.get(i));
                        }
                    }
                    Collections.reverse(mList);
                }
                adapter.notifyDataSetChanged();
            }
        });

        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPro.setText("专业/投资");
                popPro = 1;
                popupWindow.dismiss();
                mList.clear();
                for (int i = 0; i<mData.size(); i++){
                    if(popType == 0){
                        if(mData.get(i).getS_ZY().equals("1")){
                            mList.add(mData.get(i));
                        }
                    }else if(popType == 1){
                        if(mData.get(i).getS_ZY().equals("1")&&mData.get(i).getS_UP_FLAG().equals("1")){
                            mList.add(mData.get(i));
                        }
                    }else if(popType == 2){
                        if(mData.get(i).getS_ZY().equals("1")&&mData.get(i).getS_UP_FLAG().equals("0")){
                            mList.add(mData.get(i));
                        }
                    }
                }
                if(popSort == 1){
                    Collections.reverse(mList);
                }
                adapter.notifyDataSetChanged();
            }
        });

        rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPro.setText("专业/房地产");
                popPro = 2;
                popupWindow.dismiss();
                mList.clear();
                for (int i = 0; i<mData.size(); i++){
                    if(popType == 0){
                        if(mData.get(i).getS_ZY().equals("2")){
                            mList.add(mData.get(i));
                        }
                    }else if(popType == 1){
                        if(mData.get(i).getS_ZY().equals("2")&&mData.get(i).getS_UP_FLAG().equals("1")){
                            mList.add(mData.get(i));
                        }
                    }else if(popType == 2){
                        if(mData.get(i).getS_ZY().equals("2")&&mData.get(i).getS_UP_FLAG().equals("0")){
                            mList.add(mData.get(i));
                        }
                    }
                }
                if(popSort == 1){
                    Collections.reverse(mList);
                }
                adapter.notifyDataSetChanged();
            }
        });

        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
//        popupWindow.showAtLocation(cameraView, Gravity.RIGHT|Gravity.BOTTOM, 0, y+20);
        popupWindow.showAsDropDown(rlPro);
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
                tvPro.setTextColor(Color.parseColor("#656565"));
                Glide.with(context).load(R.drawable.to_bottom).into(ivPro);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = data.getStringExtra("result");
        adapter.getFilter().filter(result);
    }
}
