package com.jingna.lhjwp.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.imagepreview.StatusBarUtils;
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

public class ProfessionalLoginActivity extends BaseActivity {

    private Context context = ProfessionalLoginActivity.this;

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.iv_is_kejian)
    ImageView ivIsKejian;

    private boolean isKejian = false;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_login);
        StatusBarUtils.setStatusBarTransparent(ProfessionalLoginActivity.this);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(ProfessionalLoginActivity.this);

        etName.setText("230102");
        etPwd.setText("13001300130");
    }

    @OnClick({R.id.btn_login, R.id.iv_is_kejian})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                dialog = WeiboDialogUtils.createLoadingDialog(context, "请等待...");
                login();
                break;
            case R.id.iv_is_kejian:
                if(isKejian){
                    Glide.with(context).load(R.drawable.bukejian).into(ivIsKejian);
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                    isKejian = false;
                }else {
                    Glide.with(context).load(R.drawable.kejian).into(ivIsKejian);
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    isKejian = true;
                }
                break;
        }
    }

    private void login() {

        final String name = etName.getText().toString();
        String pwd = etPwd.getText().toString();
        String deviceId = SpUtils.getDeviceId(context);
        String url = "/tzapp/phone/userlogin.action?user_name="+name+"&password="+pwd+"&device_id="+deviceId+"&device_type=1";
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
                                SpUtils.setUsername(context, name);
                                SpUtils.setS_ORGAN(context, jsonObject.getString("S_ORGAN"));
                                SpUtils.setMinPic(context, jsonObject.getString("minPic"));
                                SpUtils.setMaxPic(context, jsonObject.getString("maxPic"));
                                Intent intent = new Intent(context, ProfessionalActivity.class);
                                startActivity(intent);
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

}
