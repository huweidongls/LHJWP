package com.jingna.lhjwp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.camera.CameraView;
import com.jingna.lhjwp.imagepreview.StatusBarUtils;
import com.jingna.lhjwp.info.ProPicInfo;
import com.jingna.lhjwp.utils.DateUtils;
import com.jingna.lhjwp.utils.Gps;
import com.jingna.lhjwp.utils.PositionUtil;
import com.jingna.lhjwp.utils.SpUtils;
import com.jingna.lhjwp.utils.WeiboDialogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

public class ProCamera1Activity extends BaseActivity implements CameraView.CameraListener {

    private Context context = ProCamera1Activity.this;

    @BindView(R.id.camera)
    CameraView cameraView;

    private String uuid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_camera1);
        StatusBarUtils.setStatusBarTransparent(ProCamera1Activity.this);
        uuid = getIntent().getStringExtra("uuid");
        ButterKnife.bind(ProCamera1Activity.this);

        init();

    }

    private void init() {

        cameraView.setCameraListener(this);

    }

    @Override
    public void onCapture(Bitmap bitmap, double latitude, double longitude, String address) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File dir = new File(path+"/lhjwp/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File someFile = new File(dir.getAbsoluteFile(), System.currentTimeMillis()+".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(someFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            Map<String, ArrayList<ProPicInfo>> map = SpUtils.getProPicInfo(context);
            ArrayList<ProPicInfo> list = map.get(uuid);
            if(list == null){
                list = new ArrayList<>();
            }
            Gps gps = PositionUtil.gps84_To_Gcj02(latitude, longitude);
            list.add(new ProPicInfo(someFile.getPath(), DateUtils.stampToDateSecond(System.currentTimeMillis()+""), gps.getWgLat(), gps.getWgLon(), address, false));
            map.put(uuid, list);
            SpUtils.setProPicInfo(context, map);
            Intent intent1 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(someFile);
            intent1.setData(uri);
            context.sendBroadcast(intent1);
//            Intent intent = new Intent();
//            intent.putExtra("path", someFile.getPath());
//            intent.putExtra("uuid", uuid);
//            intent.putExtra("title", title);
//            intent.setClass(context, ProShowPicActivity.class);
//            startActivity(intent);
//            WeiboDialogUtils.closeDialog(dialog);
            finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCameraClose() {
        finish();
    }

    @Override
    public void onCameraError(Throwable th) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.onPause();
    }

}
