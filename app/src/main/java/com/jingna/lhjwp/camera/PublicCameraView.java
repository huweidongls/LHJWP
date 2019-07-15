package com.jingna.lhjwp.camera;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.app.MyApp;
import com.jingna.lhjwp.utils.Base64Utils;
import com.jingna.lhjwp.utils.BitmapUtils;
import com.jingna.lhjwp.utils.DateUtils;
import com.jingna.lhjwp.utils.Distance;
import com.jingna.lhjwp.utils.Gps;
import com.jingna.lhjwp.utils.LocalCodeUtils;
import com.jingna.lhjwp.utils.NetUtil;
import com.jingna.lhjwp.utils.PositionUtil;
import com.jingna.lhjwp.utils.SpUtils;
import com.jingna.lhjwp.utils.ToastUtil;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hzwangchenyan on 2017/6/13.
 */
public class PublicCameraView extends FrameLayout implements SurfaceHolder.Callback,
        CaptureLayout.ClickListener, View.OnClickListener, SensorEventListener {
    private static final String TAG = "CameraView";
    private SurfaceView mSurfaceView;
    private View mFocusView;
//    private View mSwitchWrapper;
//    private View mSwitchView;
    private ImageView mPictureView;
    private CaptureLayout mCaptureLayout;
    private GestureDetector mGestureDetector;
    private ScaleGestureDetector mScaleGestureDetector;
    private SensorManager mSensorManager;
    private CameraListener mCameraListener;
    private Bitmap mPicture;
    private int mSensorRotation;
    private boolean isSurfaceCreated;

    //信息框
    private LinearLayout llInfo;
    private TextView tvAddress;
    private TextView tvLong;
    private TextView tvLat;
    private TextView tvTime;
    private TextView tvImei;
    private TextView tvName;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private Gps gps;
    private double latitude;
    private double longitude;
    private String address = "";
    private PopupWindow popupWindow;
    private View infoFrame;
    private boolean isText = true;
    private boolean isCode = true;
    private boolean isGps = true;
    private boolean isTime = true;
    private boolean isImei = true;

    private List<Gps> gpsList;
    private int loseCount = 0;

    public interface CameraListener {
        void onCapture(Bitmap bitmap, double latitude, double longitude, String address);

        void onCameraClose();

        void onCameraError(Throwable th);
    }

    public PublicCameraView(Context context) {
        this(context, null);
    }

    public PublicCameraView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PublicCameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(Color.BLACK);
        LayoutInflater.from(getContext()).inflate(R.layout.camera_layout, this, true);

        mSurfaceView = (SurfaceView) findViewById(R.id.camera_surface);
        mFocusView = findViewById(R.id.camera_focus);
//        mSwitchWrapper = findViewById(R.id.camera_switch_wrapper);
//        mSwitchView = findViewById(R.id.camera_switch);
        mPictureView = (ImageView) findViewById(R.id.camera_picture_preview);
        mCaptureLayout = (CaptureLayout) findViewById(R.id.camera_capture_layout);
        gpsList = new ArrayList<>();
        //信息框
        llInfo = findViewById(R.id.ll_info);
        tvTime = findViewById(R.id.tv_time);
        tvLong = findViewById(R.id.tv_long);
        tvLat = findViewById(R.id.tv_lat);
        tvAddress = findViewById(R.id.tv_address);
//        tvImei = findViewById(R.id.tv_imei);
        tvName = findViewById(R.id.tv_name);
        infoFrame = findViewById(R.id.info_frame);
        tvName.setText(MyApp.pubName);
        startLocate();
        Map<String, Boolean> map = SpUtils.getCameraSet(getContext());
        if(map != null){
            isText = map.get("isText");
            isCode = map.get("isCode");
            isGps = map.get("isGps");
            isTime = map.get("isTime");
            isImei = map.get("isImei");
        }
        if(!isText){
            llInfo.setVisibility(View.GONE);
        }
        if(!isGps){
            tvAddress.setVisibility(View.GONE);
            tvLat.setVisibility(View.GONE);
            tvLong.setVisibility(View.GONE);
        }
        if(!isTime){
            tvTime.setVisibility(View.GONE);
        }
        if(!isImei){
//            tvImei.setVisibility(View.GONE);
        }

        CameraManager.getInstance().init(getContext());

        mSurfaceView.setOnTouchListener(surfaceTouchListener);
        // fix `java.lang.RuntimeException: startPreview failed` on api 10
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            mSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        mSurfaceView.getHolder().addCallback(this);
        mCaptureLayout.setClickListener(this);
//        mSwitchWrapper.setVisibility(CameraManager.getInstance().hasMultiCamera() ? VISIBLE : GONE);
//        mSwitchWrapper.setOnClickListener(this);

        mGestureDetector = new GestureDetector(getContext(), simpleOnGestureListener);
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), onScaleGestureListener);
        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
    }

    public void setCameraListener(CameraListener listener) {
        mCameraListener = listener;
    }

    public void onResume() {
        Log.d(TAG, "onResume");

        if (!CameraManager.getInstance().isOpened() && isSurfaceCreated) {
            CameraManager.getInstance().open(new CameraManager.Callback<Boolean>() {
                @Override
                public void onEvent(Boolean success) {
                    if (!success && mCameraListener != null) {
                        mCameraListener.onCameraError(new Exception("open camera failed"));
                    }
                }
            });
        }

        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        Log.d(TAG, "onPause");

        if (CameraManager.getInstance().isOpened()) {
            CameraManager.getInstance().close();
        }

        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow");
        mCameraListener = null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");
        isSurfaceCreated = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged");
        CameraManager.getInstance().setSurfaceHolder(holder, width, height);

        if (CameraManager.getInstance().isOpened()) {
            CameraManager.getInstance().close();
        }

        CameraManager.getInstance().open(new CameraManager.Callback<Boolean>() {
            @Override
            public void onEvent(Boolean success) {
                if (!success && mCameraListener != null) {
                    mCameraListener.onCameraError(new Exception("open camera failed"));
                }
            }
        });
    }

    @Override
    public void surfaceDestroyed(final SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");
        isSurfaceCreated = false;
        CameraManager.getInstance().setSurfaceHolder(null, 0, 0);
    }

    @Override
    public void onCaptureClick() {
        double lat = 0.00;
        double lng = 0.00;
        for (int i = 0; i<gpsList.size(); i++){
            lat = lat + gpsList.get(i).getWgLat();
            lng = lng + gpsList.get(i).getWgLon();
        }
        lat = lat/gpsList.size();
        lng = lng/gpsList.size();
        double dis = Distance.getDistance(lng, lat, longitude, latitude);
        if(dis > 5.00&&loseCount<4){
            ToastUtil.showShort(getContext(), "当前GPS信号不稳定");
            loseCount = loseCount + 1;
        }else {
            CameraManager.getInstance().takePicture(new CameraManager.Callback<Bitmap>() {
                @Override
                public void onEvent(final Bitmap bitmap) {
                    if (bitmap != null) {
                        Observable<Bitmap> observable = Observable.create(new ObservableOnSubscribe<Bitmap>() {
                            @Override
                            public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {
                                Bitmap mBitmap;
                                Bitmap bmp;
                                String textContent = latitude+";"+longitude+";"+ DateUtils.stampToDateSecond1(System.currentTimeMillis()+"");
                                String textContent1 = ";;"+DateUtils.stampToDateSecond1(System.currentTimeMillis()+"");
                                textContent = Base64Utils.setEncryption(textContent);
                                textContent1 = Base64Utils.setEncryption(textContent1);
                                Log.e("123123", textContent);
                                if(NetUtil.isLocServiceEnable(getContext())){
                                    mBitmap = LocalCodeUtils.createImage(textContent, 150, 150, null);
                                }else {
                                    mBitmap = LocalCodeUtils.createRedImage(textContent1, 150, 150, null);
                                }
                                if(isText&&isCode){
                                    bmp = BitmapUtils.toConformBitmap(bitmap, BitmapUtils.getViewBitmap(llInfo));
                                    bmp = BitmapUtils.toConformBitmap1(bmp, mBitmap);
                                    e.onNext(bmp);
                                } else if(isText&&!isCode){
                                    bmp = BitmapUtils.toConformBitmap(bitmap, BitmapUtils.getViewBitmap(llInfo));
                                    e.onNext(bmp);
                                } else if(!isText&&isCode){
                                    bmp = BitmapUtils.toConformBitmap1(bitmap, mBitmap);
                                    e.onNext(bmp);
                                } else if(!isText&&!isCode){
                                    e.onNext(bitmap);
                                }
                            }
                        });
                        Observer<Bitmap> observer = new Observer<Bitmap>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Bitmap value) {
                                infoFrame.setVisibility(GONE);
                                mSurfaceView.setVisibility(GONE);
//                            mSwitchWrapper.setVisibility(GONE);
                                mPictureView.setVisibility(VISIBLE);
                                mPicture = value;
                                mPictureView.setImageBitmap(mPicture);
                                mCaptureLayout.setExpanded(true);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        };
                        observable.subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(observer);
                    } else {
                        // 不知道什么原因拍照失败，重新预览
                        onRetryClick();
                    }
                }
            });
        }
    }

    @Override
    public void onOkClick() {
        if (mPicture != null && mCameraListener != null) {
            mCameraListener.onCapture(mPicture, latitude, longitude, address);
        }
    }

    @Override
    public void onRetryClick() {
        loseCount = 0;
        mPicture = null;
        mCaptureLayout.setExpanded(false);
        infoFrame.setVisibility(VISIBLE);
        mSurfaceView.setVisibility(VISIBLE);
//        mSwitchWrapper.setVisibility(CameraManager.getInstance().hasMultiCamera() ? VISIBLE : GONE);
        mPictureView.setImageBitmap(null);
        mPictureView.setVisibility(GONE);
    }

    @Override
    public void onCloseClick() {
        if (mCameraListener != null) {
            mCameraListener.onCameraClose();
        }
        if(mLocationClient.isStarted()){
            mLocationClient.stop();
        }
    }

    @Override
    public void onMoreClick() {
        //popupwindow
        initSetInfoPop();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void initSetInfoPop() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.popupwindow_camera_info, null);
        ScreenAdapterTools.getInstance().loadView(view);

        int y = mCaptureLayout.getMeasuredHeight();
        Log.e("123123", y+"");

        final ImageView ivText = view.findViewById(R.id.iv_text);
        final ImageView ivCode = view.findViewById(R.id.iv_code);
        final ImageView ivGps = view.findViewById(R.id.iv_gps);
        final ImageView ivTime = view.findViewById(R.id.iv_time);
        final ImageView ivImei = view.findViewById(R.id.iv_imei);

        if(isText){
            Glide.with(getContext()).load(R.drawable.on).into(ivText);
        }else {
            Glide.with(getContext()).load(R.drawable.off).into(ivText);
        }
        if(isCode){
            Glide.with(getContext()).load(R.drawable.on).into(ivCode);
        }else {
            Glide.with(getContext()).load(R.drawable.off).into(ivCode);
        }
        if(isGps){
            Glide.with(getContext()).load(R.drawable.on).into(ivGps);
        }else {
            Glide.with(getContext()).load(R.drawable.off).into(ivGps);
        }
        if(isTime){
            Glide.with(getContext()).load(R.drawable.on).into(ivTime);
        }else {
            Glide.with(getContext()).load(R.drawable.off).into(ivTime);
        }
        if(isImei){
            Glide.with(getContext()).load(R.drawable.on).into(ivImei);
        }else {
            Glide.with(getContext()).load(R.drawable.off).into(ivImei);
        }

        ivText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isText){
                    Glide.with(getContext()).load(R.drawable.off).into(ivText);
                    isText = false;
                    llInfo.setVisibility(View.GONE);
                }else {
                    Glide.with(getContext()).load(R.drawable.on).into(ivText);
                    isText = true;
                    llInfo.setVisibility(View.VISIBLE);
                }
            }
        });

        ivCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCode){
                    Glide.with(getContext()).load(R.drawable.off).into(ivCode);
                    isCode = false;
                }else {
                    Glide.with(getContext()).load(R.drawable.on).into(ivCode);
                    isCode = true;
                }
            }
        });

        ivGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isGps){
                    Glide.with(getContext()).load(R.drawable.off).into(ivGps);
                    isGps = false;
                    tvAddress.setVisibility(View.GONE);
                    tvLat.setVisibility(View.GONE);
                    tvLong.setVisibility(View.GONE);
                }else {
                    Glide.with(getContext()).load(R.drawable.on).into(ivGps);
                    isGps = true;
                    tvAddress.setVisibility(View.VISIBLE);
                    tvLat.setVisibility(View.VISIBLE);
                    tvLong.setVisibility(View.VISIBLE);
                }
            }
        });

        ivTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTime){
                    Glide.with(getContext()).load(R.drawable.off).into(ivTime);
                    isTime = false;
                    tvTime.setVisibility(View.GONE);
                }else {
                    Glide.with(getContext()).load(R.drawable.on).into(ivTime);
                    isTime = true;
                    tvTime.setVisibility(View.VISIBLE);
                }
            }
        });

        ivImei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isImei){
                    Glide.with(getContext()).load(R.drawable.off).into(ivImei);
                    isImei = false;
//                    tvImei.setVisibility(View.GONE);
                }else {
                    Glide.with(getContext()).load(R.drawable.on).into(ivImei);
                    isImei = true;
//                    tvImei.setVisibility(View.VISIBLE);
                }
            }
        });

        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(mSurfaceView, Gravity.RIGHT|Gravity.BOTTOM, 0, y+20);
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
                Map<String, Boolean> map = new LinkedHashMap<>();
                map.put("isText", isText);
                map.put("isCode", isCode);
                map.put("isGps", isGps);
                map.put("isTime", isTime);
                map.put("isImei", isImei);
                SpUtils.setCameraSet(getContext(), map);
            }
        });

    }

    @Override
    public void onClick(View v) {
//        if (v == mSwitchWrapper) {
//            CameraManager.getInstance().switchCamera(new CameraManager.Callback<Boolean>() {
//                @Override
//                public void onEvent(Boolean success) {
//                    if (!success && mCameraListener != null) {
//                        mCameraListener.onCameraError(new Exception("switch camera failed"));
//                    }
//                }
//            });
//        }
    }

    private OnTouchListener surfaceTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mScaleGestureDetector.onTouchEvent(event);
            if (mScaleGestureDetector.isInProgress()) {
                return true;
            }

            return mGestureDetector.onTouchEvent(event);
        }
    };

    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(TAG, "onDown");
            if (!CameraManager.getInstance().isOpened()) {
                return false;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                mFocusView.removeCallbacks(timeoutRunnable);
                mFocusView.postDelayed(timeoutRunnable, 1500);

                mFocusView.setVisibility(VISIBLE);
                LayoutParams focusParams = (LayoutParams) mFocusView.getLayoutParams();
                focusParams.leftMargin = (int) e.getX() - focusParams.width / 2;
                focusParams.topMargin = (int) e.getY() - focusParams.height / 2;
                mFocusView.setLayoutParams(focusParams);

                ObjectAnimator scaleX = ObjectAnimator.ofFloat(mFocusView, "scaleX", 1, 0.5f);
                scaleX.setDuration(300);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(mFocusView, "scaleY", 1, 0.5f);
                scaleY.setDuration(300);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(mFocusView, "alpha", 1f, 0.3f, 1f, 0.3f, 1f, 0.3f, 1f);
                alpha.setDuration(600);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(scaleX).with(scaleY).before(alpha);
                animatorSet.start();

                CameraManager.Callback<Boolean> focusCallback = new CameraManager.Callback<Boolean>() {
                    @Override
                    public void onEvent(Boolean success) {
                        if (mFocusView.getTag() == this && mFocusView.getVisibility() == VISIBLE) {
                            mFocusView.setVisibility(INVISIBLE);
                        }
                    }
                };
                mFocusView.setTag(focusCallback);
                CameraManager.getInstance().setFocus(e.getX(), e.getY(), focusCallback);
            }

            return CameraManager.getInstance().hasMultiCamera();
        }

        /**
         * 前置摄像头可能不会回调对焦成功，因此需要手动隐藏对焦框
         */
        private Runnable timeoutRunnable = new Runnable() {
            @Override
            public void run() {
                if (mFocusView.getVisibility() == VISIBLE) {
                    mFocusView.setVisibility(INVISIBLE);
                }
            }
        };

//        @Override
//        public boolean onDoubleTap(MotionEvent e) {
//            Log.d(TAG, "onDoubleTap");
//            CameraManager.getInstance().switchCamera(new CameraManager.Callback<Boolean>() {
//                @Override
//                public void onEvent(Boolean success) {
//                    if (!success && mCameraListener != null) {
//                        mCameraListener.onCameraError(new Exception("switch camera failed"));
//                    }
//                }
//            });
//            return true;
//        }
    };

    private ScaleGestureDetector.OnScaleGestureListener onScaleGestureListener = new ScaleGestureDetector.OnScaleGestureListener() {
        private float mLastSpan;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float span = detector.getCurrentSpan() - mLastSpan;
            mLastSpan = detector.getCurrentSpan();
            if (CameraManager.getInstance().isOpened()) {
                CameraManager.getInstance().setZoom(span);
                return true;
            }
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Log.d(TAG, "onScaleBegin");
            mLastSpan = detector.getCurrentSpan();
            return CameraManager.getInstance().isOpened();
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            Log.d(TAG, "onScaleEnd");
        }
    };

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
            return;
        }

        int rotation = CameraUtils.calculateSensorRotation(event.values[0], event.values[1]);
        if (rotation >= 0 && rotation != mSensorRotation) {
            Log.d(TAG, "screen rotation changed from " + mSensorRotation + " to " + rotation);
            playRotateAnimation(mSensorRotation, rotation);
            CameraManager.getInstance().setSensorRotation(rotation);
            mSensorRotation = rotation;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void playRotateAnimation(int oldRotation, int newRotation) {
        if (!CameraManager.getInstance().hasMultiCamera()) {
            return;
        }

        int diff = newRotation - oldRotation;
        if (diff > 180) {
            diff = diff - 360;
            int h = llInfo.getHeight()/2;
            RotateAnimation rotate = new RotateAnimation(-oldRotation, -oldRotation - diff, h, h);
            rotate.setDuration(200);
            rotate.setFillAfter(true);
//        mSwitchView.startAnimation(rotate);
            llInfo.startAnimation(rotate);
        } else if (diff < -180) {
            diff = diff + 360;
            int h = llInfo.getHeight()/2;
            RotateAnimation rotate = new RotateAnimation(-oldRotation, -oldRotation - diff, h, h);
            rotate.setDuration(200);
            rotate.setFillAfter(true);
//        mSwitchView.startAnimation(rotate);
            llInfo.startAnimation(rotate);
        }
    }

    /**
     * 定位
     */
    private void startLocate() {
        mLocationClient = new LocationClient(getContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("gcj02");//可选，默认gcj02，设置返回的定位结果坐标系
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
//            latitude = location.getLatitude();
//            longitude = location.getLongitude();
            address = location.getAddrStr();
            if(!TextUtils.isEmpty(address)&&address.substring(0, 2).equals("中国")){
                address = address.substring(2);
            }
            tvTime.setText(DateUtils.stampToDateSecond1(System.currentTimeMillis()+""));
            if(NetUtil.isLocServiceEnable(getContext())){
                gps = PositionUtil.gcj_To_Gps84(location.getLatitude(), location.getLongitude());
                latitude = gps.getWgLat();
                longitude = gps.getWgLon();
                tvAddress.setText("地址: "+address);
                tvLong.setText("经度: "+longitude);
                tvLat.setText("纬度: "+latitude);
                if(gpsList.size() > 4){
                    gpsList.add(gps);
                    gpsList.remove(0);
                }else {
                    gpsList.add(gps);
                }
            }else {
                tvAddress.setText("地址: 未获取");
                tvLong.setText("经度: 0.0");
                tvLat.setText("纬度: 0.0");
            }
//            tvImei.setText("IMEI: "+ SpUtils.getDeviceId(getContext()));
//            if(!isMove){
//                int w = llInfo.getWidth();
//                int h = llInfo.getHeight();
//                int a = (h-w);
//                Log.e("123123", w+"--"+h+"--"+a);
//                llInfo.setTranslationX(-a);
//                llInfo.setTranslationY(a);
//                isMove = true;
//            }
        }
    }

}
