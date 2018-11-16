package com.jingna.lhjwp.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.dialog.SetIpDialog;
import com.jingna.lhjwp.imagepreview.StatusBarUtils;
import com.jingna.lhjwp.utils.Const;
import com.jingna.lhjwp.utils.SpUtils;
import com.jingna.lhjwp.utils.ToastUtil;
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
    @BindView(R.id.iv_jizhu)
    ImageView ivJizhu;

    private boolean isKejian = false;

    private Dialog dialog;

    private boolean isNet = true;

    private boolean isJizhu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_login);
        StatusBarUtils.setStatusBarTransparent(ProfessionalLoginActivity.this);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(ProfessionalLoginActivity.this);

        if(SpUtils.getJizhu(context)){
            isJizhu = true;
            Glide.with(context).load(R.drawable.jizhu).into(ivJizhu);
        }
        if (SpUtils.getJizhu(context)) {
            etName.setText(SpUtils.getUsername(context));
            etPwd.setText(SpUtils.getProPwd(context));
        }

//        etName.setText("230102");
//        etPwd.setText("13001300130");
    }

    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        //在这个判断，根据需要做处理
        if (netMobile == 1) {
            Log.e("2222", "inspectNet:连接wifi");
            isNet = true;
        } else if (netMobile == 0) {
            Log.e("2222", "inspectNet:连接移动数据");
            isNet = true;
        } else if (netMobile == -1) {
            Log.e("2222", "inspectNet:当前没有网络");
            isNet = false;
        }
    }

    @OnClick({R.id.btn_login, R.id.iv_is_kejian, R.id.ll_jizhu, R.id.iv_set_ip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                dialog = WeiboDialogUtils.createLoadingDialog(context, "请等待...");
                if (isNet) {
                    login();
                } else {

                    String data = SpUtils.getLoginInfo(context);
                }
                break;
            case R.id.iv_is_kejian:
                if (isKejian) {
                    Glide.with(context).load(R.drawable.bukejian).into(ivIsKejian);
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    isKejian = false;
                } else {
                    Glide.with(context).load(R.drawable.kejian).into(ivIsKejian);
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    isKejian = true;
                }
                break;
            case R.id.ll_jizhu:
                if (isJizhu) {
                    Glide.with(context).load(R.drawable.jizhu_kong).into(ivJizhu);
                    isJizhu = false;
                } else {
                    Glide.with(context).load(R.drawable.jizhu).into(ivJizhu);
                    isJizhu = true;
                }
                break;
            case R.id.iv_set_ip:
                SetIpDialog setIpDialog = new SetIpDialog(context, new SetIpDialog.OnOkReturnListener() {
                    @Override
                    public void onReturn(String title) {
                        ViseHttp.CONFIG()
                                .baseUrl("http://"+title+"/");
                        SpUtils.setIp(context, "http://"+title+"/");
                    }

                    @Override
                    public void onReset() {
                        String ip = SpUtils.getResetIp(context);
                        if(TextUtils.isEmpty(ip)){
                            ToastUtil.showShort(context, "当前不存在登录成功的ip地址");
                        }else {
                            ViseHttp.CONFIG()
                                    .baseUrl(ip);
                            SpUtils.setIp(context, ip);
                        }
                    }
                });
                setIpDialog.show();
                break;
        }
    }

    private void login() {

        final String name = etName.getText().toString();
        final String pwd = etPwd.getText().toString();
        String deviceId = SpUtils.getDeviceId(context);
        String url = "/tzapp/phone/userlogin.action?user_name=" + name + "&password=" + pwd + "&device_id=" + deviceId + "&device_type=1";
        ViseHttp.GET(url)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("123123", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getString("result").equals("1")) {
                                String ip = SpUtils.getIp(context);
                                SpUtils.setResetIp(context, ip);
                                if (isJizhu) {
                                    SpUtils.setJizhu(context, true);
                                } else {
                                    SpUtils.setJizhu(context, false);
                                }
                                SpUtils.setLoginInfo(context, data);
                                SpUtils.setUsername(context, name);
                                SpUtils.setProPwd(context, pwd);
                                SpUtils.setS_ORGAN(context, jsonObject.getString("S_ORGAN"));
                                SpUtils.setMinPic(context, jsonObject.getString("minPic"));
                                SpUtils.setMaxPic(context, jsonObject.getString("maxPic"));
                                SpUtils.setRealName(context, jsonObject.getString("name"));
                                Intent intent = new Intent(context, ProfessionalActivity.class);
                                startActivity(intent);
                                finish();
                            } else if(jsonObject.getString("result").equals("0")){
                                ToastUtil.showShort(context, "密码错误");
                            } else if(jsonObject.getString("result").equals("-1")){
                                ToastUtil.showShort(context, "用户名不存在");
                            }
                            WeiboDialogUtils.closeDialog(dialog);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        WeiboDialogUtils.closeDialog(dialog);
                        ToastUtil.showShort(context, "登录失败，请检查网络或ip地址是否正确");
                    }
                });

    }

}
