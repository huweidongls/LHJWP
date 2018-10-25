package com.jingna.lhjwp.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.camerakit.CameraKitView;
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.camera.CameraSurfaceView;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fotoapparat.Fotoapparat;
import io.fotoapparat.photo.BitmapPhoto;
import io.fotoapparat.result.PendingResult;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.view.CameraView;

import static io.fotoapparat.log.Loggers.fileLogger;
import static io.fotoapparat.log.Loggers.logcat;
import static io.fotoapparat.log.Loggers.loggers;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.autoFocus;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.continuousFocus;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.fixed;
import static io.fotoapparat.parameter.selector.LensPositionSelectors.back;
import static io.fotoapparat.parameter.selector.Selectors.firstAvailable;
import static io.fotoapparat.parameter.selector.SizeSelectors.biggestSize;

public class CameraActivity extends BaseActivity {

    private static final String TAG = "CameraActivity";
    private Context context = CameraActivity.this;

//    @BindView(R.id.camera_surface_view)
//    CameraSurfaceView mCameraSurfaceView;
//    @BindView(R.id.camera)
//    CameraKitView cameraKitView;
    @BindView(R.id.camera_view)
    CameraView cameraView;

    Fotoapparat fotoapparat;

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

    @OnClick({R.id.activity_camera_rl_back, R.id.take_pic})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_camera_rl_back:
                finish();
                break;
            case R.id.take_pic:
                takePic();
                break;
        }
    }

    private void takePic(){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File dir = new File(path+"/lhjwp/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File someFile = new File(dir.getAbsoluteFile(), System.currentTimeMillis()+".jpg");
        PhotoResult photoResult = fotoapparat.takePicture();
        photoResult.saveToFile(someFile);
        photoResult
                .toBitmap()
                .whenAvailable(new PendingResult.Callback<BitmapPhoto>() {
                    @Override
                    public void onResult(BitmapPhoto result) {
                        ImageView imageView = (ImageView) findViewById(R.id.result);

                        imageView.setImageBitmap(result.bitmap);
                        imageView.setRotation(-result.rotationDegrees);
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
