package com.jingna.lhjwp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.model.TaskListModel;
import com.jingna.lhjwp.utils.SpUtils;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfessionalActivity extends BaseActivity {

    private Context context = ProfessionalActivity.this;

    @BindView(R.id.tv_rk_tz)
    TextView tvRkTz;
    @BindView(R.id.tv_rk_fdc)
    TextView tvRkFdc;
    @BindView(R.id.tv_jg_tz)
    TextView tvJgTz;
    @BindView(R.id.tv_jg_fdc)
    TextView tvJgFdc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(ProfessionalActivity.this);

        initData();

    }

    private void initData() {

        String name = SpUtils.getUsername(context);
        String S_ORGAN = SpUtils.getS_ORGAN(context);
        String url = "/tzapp/phone/getTaskList.action?user_name="+name+"&S_ORGAN="+S_ORGAN;
        ViseHttp.GET(url)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("123123", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getString("result").equals("1")){
                                Gson gson = new Gson();
                                TaskListModel model = gson.fromJson(data, TaskListModel.class);
                                //入库项目采集
                                tvRkTz.setText("投资: "+model.getXkgTask().getTzTime()+"  完成: "+model.getXkgTask().getTzFinish()+"/"+model.getXkgTask().getTzAllXm());
                                tvRkFdc.setText("房地产: "+model.getXkgTask().getFdcTime()+"  完成: "+model.getXkgTask().getFdcFinish()+"/"+model.getXkgTask().getFdcAllXm());
                                //竣工项目采集
                                tvJgTz.setText("投资: "+model.getJgTask().getTzTime()+"  完成: "+model.getJgTask().getTzFinish()+"/"+model.getJgTask().getTzAllXm());
                                tvJgFdc.setText("房地产: "+model.getJgTask().getFdcTime()+"  完成: "+model.getJgTask().getFdcFinish()+"/"+model.getJgTask().getFdcAllXm());
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

    @OnClick({R.id.ll1, R.id.rl_person})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.ll1:
                intent.setClass(context, RukuListActivity.class);
                intent.putExtra("type", "1");
                startActivity(intent);
                break;
            case R.id.rl_person:
                intent.setClass(context, PersonActivity.class);
                startActivity(intent);
                break;
        }
    }

}
