package com.jingna.lhjwp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.camera.PublicCameraView;
import com.jingna.lhjwp.imagepreview.StatusBarUtils;
import com.jingna.lhjwp.info.PublicInfo;
import com.jingna.lhjwp.utils.DateUtils;
import com.jingna.lhjwp.utils.Gps;
import com.jingna.lhjwp.utils.PositionUtil;
import com.jingna.lhjwp.utils.SpUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Camera1Activity extends BaseActivity implements PublicCameraView.CameraListener {

    private Context context = Camera1Activity.this;

    @BindView(R.id.camera)
    PublicCameraView cameraView;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera1);
        StatusBarUtils.setStatusBarTransparent(Camera1Activity.this);
        position = getIntent().getIntExtra("position", 0);
        ButterKnife.bind(Camera1Activity.this);

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
            ArrayList<PublicInfo> list = SpUtils.getPublicInfo(context);
            list.get(position).setTime(DateUtils.stampToDateSecond(System.currentTimeMillis()+""));
            Gps gps = PositionUtil.gps84_To_Gcj02(latitude, longitude);
            if(gps!=null){
                list.get(position).getPicList().add(new PublicInfo.PicInfo(someFile.getPath(), gps.getWgLat(), gps.getWgLon(), address, DateUtils.stampToDateSecond(System.currentTimeMillis()+"")));
            }else {
                list.get(position).getPicList().add(new PublicInfo.PicInfo(someFile.getPath(), 0.0, 0.0, address, DateUtils.stampToDateSecond(System.currentTimeMillis()+"")));
            }
            SpUtils.setPublicInfo(context, list);
            Intent intent1 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(someFile);
            intent1.setData(uri);
            context.sendBroadcast(intent1);
//            Intent intent = new Intent();
//            intent.putExtra("path", someFile.getPath());
//            intent.putExtra("position", position);
//            intent.putExtra("title", title);
//            intent.setClass(context, PublicShowPicActivity.class);
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
