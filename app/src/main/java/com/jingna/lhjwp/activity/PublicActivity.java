package com.jingna.lhjwp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.adapter.ActivityPublicRvAdapter;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.dialog.AddDialog;
import com.jingna.lhjwp.dialog.CustomDialog;
import com.jingna.lhjwp.info.PublicInfo;
import com.jingna.lhjwp.utils.DateUtils;
import com.jingna.lhjwp.utils.FormatCurrentData;
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

public class PublicActivity extends BaseActivity {

    private Context context = PublicActivity.this;

    @BindView(R.id.activity_public_rv)
    RecyclerView recyclerView;
    @BindView(R.id.activity_public_tv_delete)
    TextView tvDelete;
    @BindView(R.id.activity_public_tv_edit)
    TextView tvEdit;

    private ActivityPublicRvAdapter adapter;
    private ArrayList<PublicInfo> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PublicActivity.this);

        initData();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mList.clear();
        mList.addAll(SpUtils.getPublicInfo(context));
        LinearLayoutManager manager = new LinearLayoutManager(PublicActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new ActivityPublicRvAdapter(PublicActivity.this, mList);
        recyclerView.setAdapter(adapter);
        adapter.setEditTitleListener(new ActivityPublicRvAdapter.EditTitleListener() {
            @Override
            public void onEdit(final int pos) {
                AddDialog addDialog = new AddDialog(PublicActivity.this, "编辑相册名称", new AddDialog.OnOkReturnListener() {
                    @Override
                    public void onReturn(String title) {
                        mList.get(pos).setTitle(title);
                        adapter.notifyDataSetChanged();
                        Log.e("123123", mList.toString());
                        SpUtils.setPublicInfo(context, mList);
                    }
                });
                addDialog.show();
            }
        });
    }

    private void initData() {

        mList.clear();
        mList.addAll(SpUtils.getPublicInfo(context));
        LinearLayoutManager manager = new LinearLayoutManager(PublicActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new ActivityPublicRvAdapter(PublicActivity.this, mList);
        recyclerView.setAdapter(adapter);
        adapter.setEditTitleListener(new ActivityPublicRvAdapter.EditTitleListener() {
            @Override
            public void onEdit(final int pos) {
                AddDialog addDialog = new AddDialog(PublicActivity.this, "编辑相册名称", new AddDialog.OnOkReturnListener() {
                    @Override
                    public void onReturn(String title) {
                        mList.get(pos).setTitle(title);
                        adapter.notifyDataSetChanged();
                        Log.e("123123", mList.toString());
                        SpUtils.setPublicInfo(context, mList);
                    }
                });
                addDialog.show();
            }
        });

        checkList();

    }

    private void checkList() {

        final List<Integer> list = new ArrayList<>();
        for(int i = 0; i<mList.size(); i++){
            if(FormatCurrentData.getTimeRange(mList.get(i).getCreateDate())){
                list.add(i);
            }
        }
        if(list.size()>0){
            CustomDialog customDialog = new CustomDialog(context, "列表存在超过1个月的相册，是否删除", new CustomDialog.OnSureListener() {
                @Override
                public void onSure() {
                    Collections.reverse(list);
                    for (int i = 0; i<list.size(); i++){
                        int num = list.get(i);
                        if(mList.get(num).getPicList()!=null&&mList.size()>0){
                            List<PublicInfo.PicInfo> list = mList.get(num).getPicList();
                            for (int a = 0; a<list.size(); a++){
                                File file = new File(list.get(a).getPicPath());
                                file.delete();
                            }
                        }
                        Log.e("123123", num+"");
                        mList.remove(num);
                        adapter.notifyDataSetChanged();
                        SpUtils.setPublicInfo(context, mList);
                    }
                }
            });
            customDialog.show();
        }

    }

    @OnClick({R.id.activity_public_rl_edit, R.id.activity_public_rl_add, R.id.activity_public_tv_delete})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_public_rl_edit:
                if(tvEdit.getText().toString().equals("编辑")){
                    adapter.setEdit(true);
                    tvEdit.setText("取消");
                    tvDelete.setVisibility(View.VISIBLE);
                }else if(tvEdit.getText().toString().equals("取消")){
                    adapter.setEdit(false);
                    tvEdit.setText("编辑");
                    tvDelete.setVisibility(View.GONE);
                }
                break;
            case R.id.activity_public_rl_add:
                AddDialog addDialog = new AddDialog(PublicActivity.this, "新建相册", new AddDialog.OnOkReturnListener() {
                    @Override
                    public void onReturn(String title) {
                        mList.add(0, new PublicInfo(title, "", DateUtils.stampToDateSecond1(System.currentTimeMillis()+""), 0, new ArrayList<PublicInfo.PicInfo>()));
                        adapter.notifyDataSetChanged();
                        Log.e("123123", mList.toString());
                        SpUtils.setPublicInfo(context, mList);
                    }
                });
                addDialog.show();
                break;
            case R.id.activity_public_tv_delete:
                if(adapter.getEditList().size()>0){
                    CustomDialog customDialog = new CustomDialog(context, "是否删除选中相册", new CustomDialog.OnSureListener() {
                        @Override
                        public void onSure() {
                            List<Integer> editList = adapter.getEditList();
                            Collections.sort(editList);
                            Collections.reverse(editList);
                            Log.e("123123", editList.toString());
                            for (int i = 0; i<editList.size(); i++){
                                int num = editList.get(i);
                                if(mList.get(num).getPicList()!=null&&mList.size()>0){
                                    List<PublicInfo.PicInfo> list = mList.get(num).getPicList();
                                    for (int a = 0; a<list.size(); a++){
                                        File file = new File(list.get(a).getPicPath());
                                        file.delete();
                                    }
                                }
                                Log.e("123123", num+"");
                                mList.remove(num);
                            }
                            adapter.notifyDataSetChanged();
                            adapter.setEdit(false);
                            tvEdit.setText("编辑");
                            tvDelete.setVisibility(View.GONE);
                            SpUtils.setPublicInfo(context, mList);
                        }
                    });
                    customDialog.show();
                }else {
                    ToastUtil.showShort(context, "请至少选择一个项册");
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if(adapter.getIsEdit()){
            adapter.setEdit(false);
            tvEdit.setText("编辑");
            tvDelete.setVisibility(View.GONE);
        }else {
            finish();
        }
    }
}
