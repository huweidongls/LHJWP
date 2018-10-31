package com.jingna.lhjwp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.imagepreview.StatusBarUtils;
import com.jingna.lhjwp.utils.SpUtils;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
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

    @OnClick({R.id.btn_login})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                login();
                break;
        }
    }

    private void login() {

        final String name = etName.getText().toString();
        String pwd = etPwd.getText().toString();
        String deviceId = SpUtils.getDeviceId(context);
        String url = "/tzapp/phone/userlogin.action?user_name="+name+"&password="+pwd+"&device_id="+deviceId+"&device_type=1";
        ViseHttp.GET(url)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("123123", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getString("result").equals("1")){
                                SpUtils.setUsername(context, name);
                                SpUtils.setS_ORGAN(context, jsonObject.getString("S_ORGAN"));
                                Intent intent = new Intent(context, ProfessionalActivity.class);
                                startActivity(intent);
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

}
