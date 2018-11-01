package com.jingna.lhjwp.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.info.ProPicInfo;
import com.jingna.lhjwp.utils.BitmapUtils;
import com.jingna.lhjwp.utils.DateUtils;
import com.jingna.lhjwp.utils.SpUtils;
import com.jingna.lhjwp.utils.WeiboDialogUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fotoapparat.Fotoapparat;
import io.fotoapparat.photo.BitmapPhoto;
import io.fotoapparat.result.PendingResult;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.view.CameraView;

import static io.fotoapparat.parameter.selector.FocusModeSelectors.autoFocus;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.continuousFocus;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.fixed;
import static io.fotoapparat.parameter.selector.LensPositionSelectors.back;
import static io.fotoapparat.parameter.selector.Selectors.firstAvailable;
import static io.fotoapparat.parameter.selector.SizeSelectors.biggestSize;

public class ProCameraActivity extends BaseActivity {

    private Context context = ProCameraActivity.this;

    @BindView(R.id.camera_view)
    CameraView cameraView;
    @BindView(R.id.activity_camera_rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_lat)
    TextView tvLat;
    @BindView(R.id.activity_camera_tv_top)
    TextView tvTop;

    Fotoapparat fotoapparat;

    private PopupWindow popupWindow;

    private Dialog dialog;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private double latitude;
    private double longitude;
    private String address = "";

    private String title = "";

    private String uuid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_camera);
        uuid = getIntent().getStringExtra("uuid");
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(ProCameraActivity.this);
        init();

    }

    private void init() {

//        tvTop.setText(title);
        fotoapparat = Fotoapparat
                .with(context)
                .into(cameraView)           // view which will draw the camera preview
                .photoSize(biggestSize())   // we want to have the biggest photo possible
                .lensPosition(back())       // we want back camera
                .focusMode(firstAvailable(  // (optional) use the first focus mode which is supported by device
                        continuousFocus(),
                        autoFocus(),        // in case if continuous focus is not available on device, auto focus will be used
                        fixed()             // if even auto focus is not available - fixed focus mode will be used
                ))
//                .flash(firstAvailable(      // (optional) similar to how it is done for focus mode, this time for flash
//                        autoRedEye(),
//                        autoFlash(),
//                        torch()
//                ))
//                .frameProcessor(myFrameProcessor)   // (optional) receives each frame from preview stream
//                .logger(loggers(            // (optional) we want to log camera events in 2 places at once
//                        logcat(),           // ... in logcat
//                        fileLogger(this)    // ... and to file
//                ))
                .build();
        startLocate();

    }

    @OnClick({R.id.activity_camera_rl_back, R.id.take_pic, R.id.rl_more})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_camera_rl_back:
                finish();
                break;
            case R.id.take_pic:
                takePic();
                break;
            case R.id.rl_more:
                initSetInfoPop();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void initSetInfoPop() {

        View view = LayoutInflater.from(ProCameraActivity.this).inflate(R.layout.popupwindow_camera_info, null);
        ScreenAdapterTools.getInstance().loadView(view);

        int y = rlBottom.getMeasuredHeight();
        Log.e("123123", y+"");

        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(cameraView, Gravity.RIGHT|Gravity.BOTTOM, 0, y+20);
//        popupWindow.showAsDropDown(rlBottom, 0, 0, Gravity.TOP|Gravity.RIGHT);
        // 设置popWindow的显示和消失动画
//        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.alpha = 0.5f;
//        getWindow().setAttributes(params);
        popupWindow.update();

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
//                WindowManager.LayoutParams params = getWindow().getAttributes();
//                params.alpha = 1f;
//                getWindow().setAttributes(params);
            }
        });

    }

    private void takePic(){

        dialog = WeiboDialogUtils.createLoadingDialog(context, "请等待...");

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File dir = new File(path+"/lhjwp/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final File someFile = new File(dir.getAbsoluteFile(), System.currentTimeMillis()+".jpg");
        PhotoResult photoResult = fotoapparat.takePicture();
        photoResult.saveToFile(someFile);
        photoResult
                .toBitmap()
                .whenAvailable(new PendingResult.Callback<BitmapPhoto>() {
                    @Override
                    public void onResult(BitmapPhoto result) {
                        Bitmap mBitmap;
                        String textContent = latitude+";"+longitude+";"+ DateUtils.stampToDateSecond1(System.currentTimeMillis()+"");
                        Log.e("123123", textContent);
                        mBitmap = CodeUtils.createImage(textContent, 400, 400, null);

                        Bitmap bitmap = BitmapUtils.toConformBitmap(BitmapUtils.rotateBitmap(result.bitmap, -result.rotationDegrees), BitmapUtils.getViewBitmap(llInfo));
                        Bitmap bitmap1 = BitmapUtils.toConformBitmap1(bitmap, mBitmap);
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(someFile);
                            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();

                            Map<String, ArrayList<ProPicInfo>> map = SpUtils.getProPicInfo(context);
                            ArrayList<ProPicInfo> list = map.get(uuid);
                            if(list == null){
                                list = new ArrayList<>();
                            }
                            list.add(new ProPicInfo(someFile.getPath(), DateUtils.stampToDateSecond(System.currentTimeMillis()+""), latitude, longitude, address, false));
                            map.put(uuid, list);
                            SpUtils.setProPicInfo(context, map);
                            Intent intent = new Intent();
                            intent.putExtra("path", someFile.getPath());
                            intent.putExtra("uuid", uuid);
                            intent.setClass(context, ProShowPicActivity.class);
                            startActivity(intent);
                            WeiboDialogUtils.closeDialog(dialog);
                            finish();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fotoapparat.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fotoapparat.stop();
    }

    /**
     * 定位
     */
    private void startLocate() {
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
        //开启定位
        mLocationClient.start();
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            address = location.getAddrStr();
            tvAddress.setText("地址: "+location.getAddrStr());
            tvLat.setText("经纬度: "+latitude+","+longitude);
        }
    }

}
