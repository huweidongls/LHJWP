package com.jingna.lhjwp.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.utils.BitmapUtils;
import com.jingna.lhjwp.utils.WeiboDialogUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

public class CameraActivity extends BaseActivity {

    private static final String TAG = "CameraActivity";
    private Context context = CameraActivity.this;

    @BindView(R.id.camera_view)
    CameraView cameraView;
    @BindView(R.id.activity_camera_rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;

    Fotoapparat fotoapparat;

    private PopupWindow popupWindow;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(CameraActivity.this);
        init();

    }

    private void init() {

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

        View view = LayoutInflater.from(CameraActivity.this).inflate(R.layout.popupwindow_camera_info, null);
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
                        String textContent = "地址：哈尔滨市南岗区汉广街,经纬度：126，45";
                        mBitmap = CodeUtils.createImage(textContent, 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

                        Bitmap bitmap = BitmapUtils.toConformBitmap(BitmapUtils.rotateBitmap(result.bitmap, -result.rotationDegrees), BitmapUtils.getViewBitmap(llInfo));
                        Bitmap bitmap1 = BitmapUtils.toConformBitmap1(bitmap, mBitmap);
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(someFile);
                            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();
                            Intent intent = new Intent();
                            intent.putExtra("path", someFile.getPath());
                            intent.setClass(context, PublicShowPicActivity.class);
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

}
