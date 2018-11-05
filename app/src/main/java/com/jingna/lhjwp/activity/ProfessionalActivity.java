package com.jingna.lhjwp.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.model.TaskListModel;
import com.jingna.lhjwp.utils.DateUtils;
import com.jingna.lhjwp.utils.SpUtils;
import com.jingna.lhjwp.utils.WeiboDialogUtils;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.mode.CacheMode;
import com.vise.xsnow.http.mode.CacheResult;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfessionalActivity extends BaseActivity {

    private Context context = ProfessionalActivity.this;

    @BindView(R.id.tv_last_time)
    TextView tvLastTime;
    @BindView(R.id.tv_rk_tz)
    TextView tvRkTz;
    @BindView(R.id.tv_rk_fdc)
    TextView tvRkFdc;
    @BindView(R.id.tv_jg_tz)
    TextView tvJgTz;
    @BindView(R.id.tv_jg_fdc)
    TextView tvJgFdc;
    @BindView(R.id.tv_jd_tz)
    TextView tvJdTz;
    @BindView(R.id.tv_jd_fdc)
    TextView tvJdFdc;
    @BindView(R.id.tv_bg_tz)
    TextView tvBgTz;
    @BindView(R.id.tv_bg_fdc)
    TextView tvBgFdc;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(ProfessionalActivity.this);

        dialog = WeiboDialogUtils.createLoadingDialog(context, "请等待...");
        initData();

    }

    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        //在这个判断，根据需要做处理
        if (netMobile == 1) {
            Log.e("2222", "inspectNet:连接wifi");
            String time = DateUtils.stampToDateSecond(System.currentTimeMillis()+"");
            tvLastTime.setText("采集任务最后更新时间: "+ time);
            SpUtils.setLastTime(context, time);
            initData();
        } else if (netMobile == 0) {
            Log.e("2222", "inspectNet:连接移动数据");
            String time = DateUtils.stampToDateSecond(System.currentTimeMillis()+"");
            tvLastTime.setText("采集任务最后更新时间: "+ time);
            SpUtils.setLastTime(context, time);
            initData();
        } else if (netMobile == -1) {
            Log.e("2222", "inspectNet:当前没有网络");
            tvLastTime.setText("网络连接不可用,上次更新时间为: "+ SpUtils.getLastTime(context));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        tvLastTime.setText("采集任务最后更新时间: "+SpUtils.getLastTime(context));
    }

    private void initData() {

        String name = SpUtils.getUsername(context);
        String S_ORGAN = SpUtils.getS_ORGAN(context);
        String url = "/tzapp/phone/getTaskList.action?user_name="+name+"&S_ORGAN="+S_ORGAN;
        ViseHttp.GET(url)
                .setLocalCache(true)//设置是否使用缓存，如果使用缓存必须设置为true
                .cacheMode(CacheMode.FIRST_CACHE) //配置缓存策略
                .request(new ACallback<CacheResult<String>>() {
                    @Override
                    public void onSuccess(CacheResult<String> data) {
                        Log.e("123123", data.getCacheData());
                        try {
                            JSONObject jsonObject = new JSONObject(data.getCacheData());
                            if(jsonObject.getString("result").equals("1")){
                                Gson gson = new Gson();
                                TaskListModel model = gson.fromJson(data.getCacheData(), TaskListModel.class);
                                //入库项目采集
                                if(TextUtils.isEmpty(model.getXkgTask().getTzTime())){
                                    tvRkTz.setText("未发布");
                                }else {
                                    tvRkTz.setText("投    资: "+model.getXkgTask().getTzTime()+"  完成: "+model.getXkgTask().getTzFinish()+"/"+model.getXkgTask().getTzAllXm());
                                }
                                if(TextUtils.isEmpty(model.getXkgTask().getFdcTime())){
                                    tvRkFdc.setText("未发布");
                                }else {
                                    tvRkFdc.setText("房地产: "+model.getXkgTask().getFdcTime()+"  完成: "+model.getXkgTask().getFdcFinish()+"/"+model.getXkgTask().getFdcAllXm());
                                }
                                //竣工项目采集
                                if(TextUtils.isEmpty(model.getJgTask().getTzTime())){
                                    tvJgTz.setText("未发布");
                                }else {
                                    tvJgTz.setText("投    资: "+model.getJgTask().getTzTime()+"  完成: "+model.getJgTask().getTzFinish()+"/"+model.getJgTask().getTzAllXm());
                                }
                                if(TextUtils.isEmpty(model.getJgTask().getFdcTime())){
                                    tvJgFdc.setText("未发布");
                                }else {
                                    tvJgFdc.setText("房地产: "+model.getJgTask().getFdcTime()+"  完成: "+model.getJgTask().getFdcFinish()+"/"+model.getJgTask().getFdcAllXm());
                                }
                                //进度项目采集
                                if(TextUtils.isEmpty(model.getJdTask().getTzTime())){
                                    tvJdTz.setText("未发布");
                                }else {
                                    tvJdTz.setText("投    资: "+model.getJdTask().getTzTime()+"  完成: "+model.getJdTask().getTzFinish()+"/"+model.getJdTask().getTzAllXm());
                                }
                                if(TextUtils.isEmpty(model.getJdTask().getFdcTime())){
                                    tvJdFdc.setText("未发布");
                                }else {
                                    tvJdFdc.setText("房地产: "+model.getJdTask().getFdcTime()+"  完成: "+model.getJdTask().getFdcFinish()+"/"+model.getJdTask().getFdcAllXm());
                                }
                                //变更项目采集
                                if(TextUtils.isEmpty(model.getBgTask().getTzTime())){
                                    tvBgTz.setText("未发布");
                                }else {
                                    tvBgTz.setText("投    资: "+model.getBgTask().getTzTime()+"  完成: "+model.getBgTask().getTzFinish()+"/"+model.getBgTask().getTzAllXm());
                                }
                                if(TextUtils.isEmpty(model.getBgTask().getFdcTime())){
                                    tvBgFdc.setText("未发布");
                                }else {
                                    tvBgFdc.setText("房地产: "+model.getBgTask().getFdcTime()+"  完成: "+model.getBgTask().getFdcFinish()+"/"+model.getBgTask().getFdcAllXm());
                                }
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

    }

    @OnClick({R.id.ll1, R.id.rl_person, R.id.ll2, R.id.ll3, R.id.ll4, R.id.rl_refresh})
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
            case R.id.ll2:
                intent.setClass(context, RukuListActivity.class);
                intent.putExtra("type", "2");
                startActivity(intent);
                break;
            case R.id.ll3:
                intent.setClass(context, RukuListActivity.class);
                intent.putExtra("type", "3");
                startActivity(intent);
                break;
            case R.id.ll4:
                intent.setClass(context, RukuListActivity.class);
                intent.putExtra("type", "4");
                startActivity(intent);
                break;
            case R.id.rl_refresh:
                dialog = WeiboDialogUtils.createLoadingDialog(context, "请等待...");
                initData();
                break;
        }
    }

}
