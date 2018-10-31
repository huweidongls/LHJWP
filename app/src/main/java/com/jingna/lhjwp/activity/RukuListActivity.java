package com.jingna.lhjwp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.adapter.ActivityProRukuListAdapter;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.model.RukuListModel;
import com.jingna.lhjwp.utils.SpUtils;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RukuListActivity extends BaseActivity {

    private Context context = RukuListActivity.this;

    @BindView(R.id.activity_ruku_list_rv)
    RecyclerView recyclerView;

    private String type = "";

    private ActivityProRukuListAdapter adapter;
    private List<RukuListModel.XmListBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruku_list);
        type = getIntent().getStringExtra("type");
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(RukuListActivity.this);

        initData();

    }

    private void initData() {

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
                                LinearLayoutManager manager = new LinearLayoutManager(context);
                                manager.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView.setLayoutManager(manager);
                                mList = model.getXmList();
                                adapter = new ActivityProRukuListAdapter(context, mList);
                                recyclerView.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

    }

    @OnClick({R.id.activity_ruku_list_rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_ruku_list_rl_back:
                finish();
                break;
        }
    }

}
