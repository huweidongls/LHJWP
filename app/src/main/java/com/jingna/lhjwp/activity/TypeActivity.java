package com.jingna.lhjwp.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.imagepreview.StatusBarUtils;
import com.jingna.lhjwp.utils.SpUtils;
import com.vise.xsnow.permission.OnPermissionCallback;
import com.vise.xsnow.permission.PermissionManager;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TypeActivity extends BaseActivity {

    private Context context = TypeActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        StatusBarUtils.setStatusBarTransparent(TypeActivity.this);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(TypeActivity.this);

        PermissionManager.instance().request(this, new OnPermissionCallback() {
                    @Override
                    public void onRequestAllow(String permissionName) {
//                DialogUtil.showTips(mContext, getString(R.string.permission_control),
//                        getString(R.string.permission_allow) + "\n" + permissionName);
                        Log.e("123123", "1"+permissionName);
                    }

                    @Override
                    public void onRequestRefuse(String permissionName) {
//                DialogUtil.showTips(mContext, getString(R.string.permission_control),
//                        getString(R.string.permission_refuse) + "\n" + permissionName);
                        Log.e("123123", "2"+permissionName);
                    }

                    @Override
                    public void onRequestNoAsk(String permissionName) {
//                DialogUtil.showTips(mContext, getString(R.string.permission_control),
//                        getString(R.string.permission_noAsk) + "\n" + permissionName);
                        Log.e("123123", "3");
                    }
                }, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE);

    }

    @OnClick({R.id.btn1, R.id.btn2})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.btn1:
                intent.setClass(TypeActivity.this, PublicActivity.class);
                startActivity(intent);
                break;
            case R.id.btn2:
                if(SpUtils.getUsername(context).equals("0")||!SpUtils.getIp(context).equals(SpUtils.getResetIp(context))){
                    intent.setClass(TypeActivity.this, ProfessionalLoginActivity.class);
                    startActivity(intent);
                }else {
                    intent.setClass(context, ProfessionalActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

}
