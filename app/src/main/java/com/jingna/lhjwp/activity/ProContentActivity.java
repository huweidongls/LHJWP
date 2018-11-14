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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.adapter.PicAddShowAdapter;
import com.jingna.lhjwp.adapter.ProPicAddShowAdapter;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.dialog.CustomDialog;
import com.jingna.lhjwp.imagepreview.Consts;
import com.jingna.lhjwp.imagepreview.ImagePreviewActivity;
import com.jingna.lhjwp.info.ProPicInfo;
import com.jingna.lhjwp.info.PublicInfo;
import com.jingna.lhjwp.utils.BitmapUtils;
import com.jingna.lhjwp.utils.Const;
import com.jingna.lhjwp.utils.DateUtils;
import com.jingna.lhjwp.utils.FileUtils;
import com.jingna.lhjwp.utils.LocalCodeUtils;
import com.jingna.lhjwp.utils.SpUtils;
import com.jingna.lhjwp.utils.ToastUtil;
import com.jingna.lhjwp.utils.WeiboDialogUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class ProContentActivity extends BaseActivity {

    private Context context = ProContentActivity.this;

    @BindView(R.id.activity_public_content_rv_pic)
    RecyclerView recyclerView;
    @BindView(R.id.activity_public_content_rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.activity_public_content_tv_bottom)
    TextView tvBottom;
    @BindView(R.id.activity_public_content_tv_top)
    TextView tvTop;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private double latitude;
    private double longitude;
    private String address = "";

    private ProPicAddShowAdapter adapter;
    private ArrayList<ProPicInfo> mList = new ArrayList<>();

    private PopupWindow popupWindow;

    private String uuid = "";
    private String username = "";
    private String title = "";
    private String S_SJ = "";
    private String S_TAB = "";
    private String type = "";
    private String cankao = "";

    private static final int REQUEST_CODE = 0x00000011;

    private Dialog dialog;

    private boolean isCode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_show_pic);
        uuid = getIntent().getStringExtra("uuid");
        title = getIntent().getStringExtra("title");
        S_SJ = getIntent().getStringExtra("S_SJ");
        S_TAB = getIntent().getStringExtra("S_TAB");
        type = getIntent().getStringExtra("type");
        cankao = getIntent().getStringExtra("cankao");
        username = SpUtils.getUsername(context);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(ProContentActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {

        tvTop.setText(title);
        Map<String, ArrayList<ProPicInfo>> map = SpUtils.getProPicInfo(context);
        if(map.get(uuid) != null){
            mList.clear();
            mList.addAll(map.get(uuid));
        }
        GridLayoutManager manager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(manager);
        adapter = new ProPicAddShowAdapter(mList);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new ProPicAddShowAdapter.OnAddImgListener() {
            @Override
            public void onAddImg() {
                showAddTypePop();
            }
        });
        adapter.setShowImgListener(new ProPicAddShowAdapter.ShowImgListener() {
            @Override
            public void showImg(int pos) {
                adapter.setEdit(false);
                ivBack.setVisibility(View.VISIBLE);
                tvCancel.setVisibility(View.GONE);
                tvBottom.setText("上传");
                tvBottom.setBackgroundColor(Color.parseColor("#2276F6"));

//                Intent intent = new Intent();
//                intent.putExtra("path", mList.get(pos).getPicPath());
//                intent.putExtra("uuid", uuid);
//                intent.putExtra("title", title);
//                intent.setClass(context, ProShowPicActivity.class);
//                startActivity(intent);

                List<String> urlList = new ArrayList<>();
                for (int i = 0; i<mList.size(); i++){
                    urlList.add("file://"+mList.get(i).getPicPath());
                }
                Intent intent = new Intent(context, ImagePreviewActivity.class);
                intent.putStringArrayListExtra("imageList", (ArrayList<String>) urlList);
                intent.putExtra(Consts.START_ITEM_POSITION, pos);
                intent.putExtra(Consts.START_IAMGE_POSITION, pos);
//                ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(getActivity(), imageView, imageView.getTransitionName());
                context.startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.photoview_open, 0);
            }
        });

    }

    private void showAddTypePop(){
        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow_pro_add_type, null);
        ScreenAdapterTools.getInstance().loadView(view);

        TextView tvCamera = view.findViewById(R.id.tv_camera);
        TextView tvPhoto = view.findViewById(R.id.tv_photo);
        TextView tvCancelPop = view.findViewById(R.id.tv_cancel);

        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setEdit(false);
                ivBack.setVisibility(View.VISIBLE);
                tvCancel.setVisibility(View.GONE);
                tvBottom.setText("上传");
                tvBottom.setBackgroundColor(Color.parseColor("#2276F6"));
                Intent intent = new Intent();
                intent.putExtra("uuid", uuid);
                intent.putExtra("title", title);
                intent.setClass(context, ProCamera1Activity.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });

        tvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setEdit(false);
                ivBack.setVisibility(View.VISIBLE);
                tvCancel.setVisibility(View.GONE);
                tvBottom.setText("上传");
                tvBottom.setBackgroundColor(Color.parseColor("#2276F6"));
                startLocate();
                //不限数量的多选
                ImageSelector.builder()
                        .useCamera(false) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(0) // 图片的最大选择数量，小于等于0时，不限数量。
//                        .setSelected(selected) // 把已选的图片传入默认选中。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(ProContentActivity.this, REQUEST_CODE); // 打开相册
                popupWindow.dismiss();
            }
        });

        tvCancelPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.update();

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isCode = true;
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
            for (int a = 0; a<images.size(); a++){
//                FileInputStream fis = null;
//                try {
//                    fis = new FileInputStream(images.get(a));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                Bitmap bitmap  = BitmapFactory.decodeStream(fis);
//                CodeUtils.analyzeBitmap(images.get(a), new CodeUtils.AnalyzeCallback() {
//                    @Override
//                    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
//                        Log.e("123123", result);
////                        Toast.makeText(context, "解析结果:" + result, Toast.LENGTH_LONG).show();
//                        if(!result.contains(";")){
//                            isCode = false;
//                        }
//                    }
//
//                    @Override
//                    public void onAnalyzeFailed() {
//                        Log.e("123123", "解析二维码失败");
//                        isCode = false;
////                        Toast.makeText(context, "解析二维码失败", Toast.LENGTH_LONG).show();
//                    }
//                });

                LocalCodeUtils.analyzeBitmap(images.get(a), new LocalCodeUtils.AnalyzeCallback() {
                    @Override
                    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                        Log.e("123123", result);
//                        Toast.makeText(context, "解析结果:" + result, Toast.LENGTH_LONG).show();
//                        if(!result.contains(";")){
//                            isCode = false;
//                        }
                    }

                    @Override
                    public void onAnalyzeFailed() {
                        Log.e("123123", "解析二维码失败");
                        isCode = false;
//                        Toast.makeText(context, "解析二维码失败", Toast.LENGTH_LONG).show();
                    }
                });

//                if (bitmap != null) {
//                    bitmap.recycle();
//                }
            }
            if(isCode){
                String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                File dir = new File(path+"/lhjwp/");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                Map<String, ArrayList<ProPicInfo>> map = SpUtils.getProPicInfo(context);
                ArrayList<ProPicInfo> list = map.get(uuid);
                if(list == null){
                    list = new ArrayList<>();
                }
                for (int i = 0; i<images.size(); i++){
                    String newPath = path+"/lhjwp/"+System.currentTimeMillis()+i+".jpg";
                    boolean isSuccess = FileUtils.copyFile(images.get(i), newPath);
                    if(isSuccess){
                        list.add(new ProPicInfo(newPath, DateUtils.stampToDateSecond(System.currentTimeMillis()+""), latitude, longitude, address, false));
                    }
                }
                map.put(uuid, list);
                SpUtils.setProPicInfo(context, map);
                mLocationClient.stop();
                initData();
            }else {
                ToastUtil.showShort(context, "有不存在二维码的照片，请重新选择");
            }
        }
    }

    @OnClick({R.id.activity_public_content_rl_back, R.id.activity_public_content_rl_more, R.id.activity_public_content_tv_bottom})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_public_content_rl_back:
                if(adapter.getEdit()){
                    adapter.setEdit(false);
                    ivBack.setVisibility(View.VISIBLE);
                    tvCancel.setVisibility(View.GONE);
                    tvBottom.setText("上传");
                    tvBottom.setBackgroundColor(Color.parseColor("#2276F6"));
                }else {
                    finish();
                }
                break;
            case R.id.activity_public_content_rl_more:
                initMorePop();
                break;
            case R.id.activity_public_content_tv_bottom:
                if(adapter.getEdit()){
                    if(adapter.getEditList().size()>0){
                        CustomDialog customDialog = new CustomDialog(context, "是否删除照片", new CustomDialog.OnSureListener() {
                            @Override
                            public void onSure() {
                                Map<String, ArrayList<ProPicInfo>> map = SpUtils.getProPicInfo(context);
                                ArrayList<ProPicInfo> list = map.get(uuid);
                                List<Integer> editList = adapter.getEditList();
                                Collections.sort(editList);
                                Collections.reverse(editList);
//                Log.e("121212", mList.size()+"--"+editList.size());
                                for (int i = 0; i<editList.size(); i++){
                                    int num = editList.get(i);
                                    File file = new File(mList.get(num).getPicPath());
                                    file.delete();
                                    mList.remove(num);
                                    list.remove(num);
                                }
                                adapter.setEdit(false);
                                adapter.notifyDataSetChanged();
                                map.put(uuid, list);
                                SpUtils.setProPicInfo(context, map);
                                ivBack.setVisibility(View.VISIBLE);
                                tvCancel.setVisibility(View.GONE);
                                tvBottom.setText("上传");
                                tvBottom.setBackgroundColor(Color.parseColor("#2276F6"));
                            }
                        });
                        customDialog.show();
                    }else {
                        ToastUtil.showShort(context, "请至少选择一张照片");
                    }
                }else {
                    Log.e("123123", SpUtils.getMaxPic(context)+"~~"+SpUtils.getMinPic(context));
                    int maxPic = Integer.parseInt(SpUtils.getMaxPic(context));
                    int minPic = Integer.parseInt(SpUtils.getMinPic(context));
                    if(mList.size()<minPic||mList.size()>maxPic){
                        ToastUtil.showShort(context, "只能上传"+minPic+"~"+maxPic+"张照片");
                    }else {
                        ToastUtil.showShort(context, "开始上传");
                        dialog = WeiboDialogUtils.createLoadingDialog(context, "请等待...");
                        uploadPic();
                    }
                }
                break;
        }
    }

    /**
     * 上传图片
     */
    private void uploadPic() {
        Observable<Map<String, File>> observable = Observable.create(new ObservableOnSubscribe<Map<String, File>>() {
            @Override
            public void subscribe(final ObservableEmitter<Map<String, File>> e) throws Exception {

                Map<String, File> fileMap = new LinkedHashMap<>();

//                FileOutputStream fos = null;
                for (int i = 0; i<mList.size(); i++){
//                    Bitmap bitmap = BitmapFactory.decodeFile(mList.get(i).getPicPath());
//                    Bitmap bitmap1 = BitmapUtils.compressImage(bitmap);
//                    fos = new FileOutputStream(mList.get(i).getPicPath());
//                    if(bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, fos)){
//                        Log.e("123123", i+"");
                        fileMap.put("file"+i, new File(mList.get(i).getPicPath()));
//                    }
                }
//                fos.flush();
//                fos.close();
                Log.e("123123", "file数组长度"+fileMap.size());
                e.onNext(fileMap);
//                WeiboDialogUtils.closeDialog(dialog);

//                final List<String> list = new ArrayList<>();
//                for (int i = 0; i<mList.size(); i++){
//                    list.add(mList.get(i).getPicPath());
//                }
//                final List<File> listFile = new ArrayList<>();
//                Luban.with(context)
//                        .load(list)
//                        .ignoreBy(500)
//                        .filter(new CompressionPredicate() {
//                            @Override
//                            public boolean apply(String path) {
//                                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
//                            }
//                        })
//                        .setCompressListener(new OnCompressListener() {
//                            @Override
//                            public void onStart() {
//                                // TODO 压缩开始前调用，可以在方法内启动 loading UI
//                            }
//
//                            @Override
//                            public void onSuccess(File file) {
//                                // TODO 压缩成功后调用，返回压缩后的图片文件
//
//                                listFile.add(file);
//
//                                Log.e("123123", file.getName());
//
//                                if (listFile.size() == list.size()) {
//                                    Map<String, File> fileMap = new LinkedHashMap<>();
//                                    for (int i = 0; i < listFile.size(); i++) {
//                                        fileMap.put("file"+i, listFile.get(i));
//                                    }
//                                    e.onNext(fileMap);
//                                }
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                // TODO 当压缩过程出现问题时调用
//                            }
//                        }).launch();
            }
        });
        Observer<Map<String, File>> observer = new Observer<Map<String, File>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Map<String, File> value) {
                ToastUtil.showShort(context, Const.BASE_URL+"/tzapp/phone/upPic.action");
                ViseHttp.UPLOAD("/tzapp/phone/upPic.action")
                        .addParam("S_CORP_UUID", uuid)
                        .addParam("S_SJ", S_SJ)
                        .addParam("S_TAB", S_TAB)
                        .addParam("S_TASK_ID", "")
                        .addParam("type", type)
                        .addParam("user_name", username)
                        .addFiles(value)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("123123", data+"返回的json");
//                                ToastUtil.showShort(context, "返回的json--"+data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getString("result").equals("1")){
                                        for (int i = 0; i<mList.size(); i++){
                                            mList.get(i).setUpload(true);
                                        }
                                        Map<String, ArrayList<ProPicInfo>> map = SpUtils.getProPicInfo(context);
                                        map.put(uuid, mList);
                                        SpUtils.setProPicInfo(context, map);
                                        adapter.notifyDataSetChanged();
                                        ToastUtil.showShort(context, "上传照片成功");
                                    }else if(jsonObject.getString("result").equals("0")){
                                        ToastUtil.showShort(context, "上传照片失败");
                                    }else if(jsonObject.getString("result").equals("-1")){
                                        ToastUtil.showShort(context, "图像内二维码不存在");
                                    }else if(jsonObject.getString("result").equals("-2")){
                                        ToastUtil.showShort(context, jsonObject.getString("errorInfo"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                WeiboDialogUtils.closeDialog(dialog);
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                Log.e("123123", errMsg);
                                WeiboDialogUtils.closeDialog(dialog);
                            }
                        });
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void initMorePop() {

        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow_more, null);
        ScreenAdapterTools.getInstance().loadView(view);

        final Intent intent = new Intent();
        LinearLayout ll1 = view.findViewById(R.id.ll1);
        LinearLayout ll2 = view.findViewById(R.id.ll2);
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.size()>0){
                    adapter.setEdit(false);
                    ivBack.setVisibility(View.VISIBLE);
                    tvCancel.setVisibility(View.GONE);
                    tvBottom.setText("上传");
                    tvBottom.setBackgroundColor(Color.parseColor("#2276F6"));
                    intent.putExtra("uuid", uuid);
                    intent.putExtra("title", title);
                    intent.putExtra("type", type);
                    intent.putExtra("cankao", cankao);
                    intent.setClass(context, ProPicLocationActivity.class);
                    startActivity(intent);
                }else {
                    ToastUtil.showShort(context, "暂无图片信息");
                }
                popupWindow.dismiss();
            }
        });
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setEdit(true);
                ivBack.setVisibility(View.GONE);
                tvCancel.setVisibility(View.VISIBLE);
                tvBottom.setText("删除");
                tvBottom.setBackgroundColor(Color.parseColor("#FF3A30"));
                popupWindow.dismiss();
            }
        });

        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
//        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        popupWindow.showAsDropDown(rlTop, 0, 20, Gravity.RIGHT);
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

    @Override
    public void onBackPressed() {
        if (adapter.getEdit()) {
            adapter.setEdit(false);
            ivBack.setVisibility(View.VISIBLE);
            tvCancel.setVisibility(View.GONE);
            tvBottom.setText("上传");
            tvBottom.setBackgroundColor(Color.parseColor("#2276F6"));
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        }
    }

}