package com.jingna.lhjwp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

    @OnClick({R.id.activity_public_rl_edit, R.id.activity_public_rl_add})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_public_rl_edit:

                break;
            case R.id.activity_public_rl_add:
                AddDialog addDialog = new AddDialog(PublicActivity.this, new AddDialog.OnOkReturnListener() {
                    @Override
                    public void onReturn(String title) {

                    }
                });
                addDialog.show();
                break;
        }
    }

}
