package com.jingna.lhjwp.activity;

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
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublicActivity extends BaseActivity {

    @BindView(R.id.activity_public_rv)
    RecyclerView recyclerView;
    @BindView(R.id.activity_public_tv_delete)
    TextView tvDelete;
    @BindView(R.id.activity_public_tv_edit)
    TextView tvEdit;

    private ActivityPublicRvAdapter adapter;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PublicActivity.this);

        initData();

    }

    private void initData() {

        mList.add("");
        mList.add("");
        mList.add("");
        LinearLayoutManager manager = new LinearLayoutManager(PublicActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new ActivityPublicRvAdapter(PublicActivity.this, mList);
        recyclerView.setAdapter(adapter);

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
                AddDialog addDialog = new AddDialog(PublicActivity.this, new AddDialog.OnOkReturnListener() {
                    @Override
                    public void onReturn(String title) {

                    }
                });
                addDialog.show();
                break;
            case R.id.activity_public_tv_delete:
                Log.e("123123", adapter.getEditList().toString());
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