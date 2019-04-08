package com.jingna.lhjwp.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.app.MyApp;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.dialog.ProgressDialog;
import com.jingna.lhjwp.dialog.VersionDialog;
import com.jingna.lhjwp.imagepreview.StatusBarUtils;
import com.jingna.lhjwp.model.VersionModel;
import com.jingna.lhjwp.utils.SpUtils;
import com.jingna.lhjwp.utils.VersionUtils;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.mode.DownProgress;
import com.vise.xsnow.permission.OnPermissionCallback;
import com.vise.xsnow.permission.PermissionManager;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

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
        checkVersion();

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

    /**
     * 检查当前是否有版本更新
     */
    private void checkVersion() {

        ViseHttp.GET("tzapp/cellphone/checkVersion.do?clienttype=1")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            Log.e("123123", data);
                            Gson gson = new Gson();
                            final VersionModel model = gson.fromJson(data, VersionModel.class);
                            int currentVersionCode = VersionUtils.packageCode(context);
                            int versionCode = 0;
                            if(!TextUtils.isEmpty(model.getVersionnum())){
                                versionCode = Integer.valueOf(model.getVersionnum());
                            }
                            if(model.getVersiontype().equals("1")){
                                if(versionCode>currentVersionCode){
                                    //弹出dialog
                                    VersionDialog versionDialog = new VersionDialog(context, model.getUpdatetime(), model.getVersionnote(), new VersionDialog.ClickListener() {
                                        @Override
                                        public void onUpdata() {
                                            final ProgressDialog progressDialog = new ProgressDialog(context);
                                            progressDialog.setCancelable(false);
                                            progressDialog.setCanceledOnTouchOutside(false);
                                            progressDialog.show();
                                            String downloadUrl = model.getDownurl();
                                            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                                            ViseHttp.DOWNLOAD("/tzapp/resource/LHjwp.apk")
                                                    .setRootName(path)
                                                    .setDirName("lhjwp")
                                                    .setFileName("LHjwp.apk")
                                                    .request(new ACallback<DownProgress>() {
                                                        @Override
                                                        public void onSuccess(DownProgress data) {
                                                            Log.e("123123", data.getFormatStatusString()+"--"+data.getPercent());
                                                            progressDialog.setInfo(data.getFormatStatusString(), data.getPercent());
                                                            if (data.isDownComplete()){
                                                                progressDialog.dismiss();
                                                                String appFile = Environment.getExternalStorageDirectory().getAbsolutePath()+"/lhjwp/"+"LHjwp.apk";
                                                                openAPK(appFile);
                                                            }
                                                        }

                                                        @Override
                                                        public void onFail(int errCode, String errMsg) {

                                                        }
                                                    });
                                        }

                                        @Override
                                        public void onCancel() {
                                            MyApp.getInstance().exit();
                                        }
                                    });
                                    versionDialog.setCancelable(false);
                                    versionDialog.setCanceledOnTouchOutside(false);
                                    versionDialog.show();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

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

    /**
     * 安装apk
     * @param fileSavePath
     */
    private void openAPK(String fileSavePath){
        File file=new File(Uri.parse(fileSavePath).getPath());
        String filePath = file.getAbsolutePath();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//判断版本大于等于7.0
            // 生成文件的uri，，
            // 注意 下面参数com.ausee.fileprovider 为apk的包名加上.fileprovider，
            data = FileProvider.getUriForFile(context, "com.jingna.lhjwp.fileprovider", new File(filePath));
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);// 给目标应用一个临时授权
        } else {
            data = Uri.fromFile(file);
        }

        intent.setDataAndType(data, "application/vnd.android.package-archive");
        startActivity(intent);
    }

}
