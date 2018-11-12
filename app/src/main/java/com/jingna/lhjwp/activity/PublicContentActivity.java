package com.jingna.lhjwp.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
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

import com.google.gson.Gson;
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.adapter.PicAddShowAdapter;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.dialog.CustomDialog;
import com.jingna.lhjwp.imagepreview.Consts;
import com.jingna.lhjwp.imagepreview.ImagePreviewActivity;
import com.jingna.lhjwp.info.PublicInfo;
import com.jingna.lhjwp.utils.FileUtils;
import com.jingna.lhjwp.utils.ShareUtils;
import com.jingna.lhjwp.utils.SpUtils;
import com.jingna.lhjwp.utils.ToastUtil;
import com.jingna.lhjwp.utils.WeiboDialogUtils;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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

public class PublicContentActivity extends BaseActivity {

    private Context context = PublicContentActivity.this;

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

    private PicAddShowAdapter adapter;
    private ArrayList<PublicInfo.PicInfo> mList = new ArrayList<>();

    private PopupWindow popupWindow;

    private int position;
    private String title = "";

    private PopupWindow sharePop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_content);
        position = getIntent().getIntExtra("position", 0);
        title = getIntent().getStringExtra("title");
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PublicContentActivity.this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
        ArrayList<PublicInfo> list = SpUtils.getPublicInfo(context);
        if(list.get(position).getPicList()!=null&&list.get(position).getPicList().size()>0){
            list.get(position).setCollect(1);
        }
        SpUtils.setPublicInfo(context, list);
    }

    private void initData() {

        tvTop.setText(title);
        mList.clear();
        mList.addAll(SpUtils.getPublicInfo(context).get(position).getPicList());
        GridLayoutManager manager = new GridLayoutManager(PublicContentActivity.this, 3);
        recyclerView.setLayoutManager(manager);
        adapter = new PicAddShowAdapter(mList);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new PicAddShowAdapter.OnAddImgListener() {
            @Override
            public void onAddImg() {
                adapter.setEdit(false);
                ivBack.setVisibility(View.VISIBLE);
                tvCancel.setVisibility(View.GONE);
                tvBottom.setText("分享");
                tvBottom.setBackgroundColor(Color.parseColor("#2276F6"));
                Intent intent = new Intent();
                intent.putExtra("position", position);
                intent.putExtra("title", title);
                intent.setClass(PublicContentActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });
        adapter.setShowImgListener(new PicAddShowAdapter.ShowImgListener() {
            @Override
            public void showImg(int pos) {
                adapter.setEdit(false);
                ivBack.setVisibility(View.VISIBLE);
                tvCancel.setVisibility(View.GONE);
                tvBottom.setText("分享");
                tvBottom.setBackgroundColor(Color.parseColor("#2276F6"));

                Intent intent = new Intent();
                intent.putExtra("title", title);
                intent.putExtra("path", mList.get(pos).getPicPath());
                intent.putExtra("position", position);
                intent.setClass(context, PublicShowPicActivity.class);
                startActivity(intent);

//                List<String> urlList = new ArrayList<>();
//                for (int i = 0; i<mList.size(); i++){
//                    urlList.add("file://"+mList.get(i).getPicPath());
//                }
//                Intent intent = new Intent(context, ImagePreviewActivity.class);
//                intent.putStringArrayListExtra("imageList", (ArrayList<String>) urlList);
//                intent.putExtra(Consts.START_ITEM_POSITION, pos);
//                intent.putExtra(Consts.START_IAMGE_POSITION, pos);
////                ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(getActivity(), imageView, imageView.getTransitionName());
//                startActivity(intent);
////                getActivity().overridePendingTransition(R.anim.photoview_open, 0);
            }
        });

    }

    @OnClick({R.id.activity_public_content_rl_back, R.id.activity_public_content_rl_more, R.id.activity_public_content_tv_bottom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_public_content_rl_back:
                if (adapter.getEdit()) {
                    adapter.setEdit(false);
                    ivBack.setVisibility(View.VISIBLE);
                    tvCancel.setVisibility(View.GONE);
                    tvBottom.setText("分享");
                    tvBottom.setBackgroundColor(Color.parseColor("#2276F6"));
                } else {
                    finish();
                }
                break;
            case R.id.activity_public_content_rl_more:
                if(mList.size() == 0){
                    ToastUtil.showShort(context, "当前没有照片，无法查看地图");
                }else {
                    initMorePop();
                }
                break;
            case R.id.activity_public_content_tv_bottom:
                if (adapter.getEdit()) {
                    if(adapter.getEditList().size()>0){
                        CustomDialog customDialog = new CustomDialog(context, "是否删除照片", new CustomDialog.OnSureListener() {
                            @Override
                            public void onSure() {
                                ArrayList<PublicInfo> list = SpUtils.getPublicInfo(context);
                                List<Integer> editList = adapter.getEditList();
                                Collections.sort(editList);
                                Collections.reverse(editList);
//                Log.e("121212", mList.size()+"--"+editList.size());
                                for (int i = 0; i < editList.size(); i++) {
                                    int num = editList.get(i);
                                    File file = new File(mList.get(num).getPicPath());
                                    file.delete();
                                    mList.remove(num);
                                    list.get(position).getPicList().remove(num);
                                }
                                adapter.setEdit(false);
                                adapter.notifyDataSetChanged();
                                SpUtils.setPublicInfo(context, list);
                                ivBack.setVisibility(View.VISIBLE);
                                tvCancel.setVisibility(View.GONE);
                                tvBottom.setText("分享");
                                tvBottom.setBackgroundColor(Color.parseColor("#2276F6"));
                            }
                        });
                        customDialog.show();
                    }else {
                        ToastUtil.showShort(context, "请至少选择一张照片");
                    }
                } else {
//                    List<File> fileList = new ArrayList<>();
//                    for (int i = 0; i<mList.size(); i++){
//                        fileList.add(new File(mList.get(i).getPicPath()));
//                    }
//                    ShareUtils.startShare(0, "测试", fileList, context);
//                    ToastUtil.showShort(context, "上传");
//                    uploadPic();
                    if(mList.size()<3||mList.size()>9){
                        ToastUtil.showShort(context, "只能发送3~9张照片");
                    }else {
                        showShare();
//                        ToastUtil.showShort(context, "开始压缩");
//                        uploadPic();
                    }
                }
                break;
        }
    }

    /**
     * 分享的pop
     */
    private void showShare() {
        View view = LayoutInflater.from(PublicContentActivity.this).inflate(R.layout.popupwindow_share, null);
        ScreenAdapterTools.getInstance().loadView(view);

        ImageView weixin = view.findViewById(R.id.weixin);
        ImageView qq = view.findViewById(R.id.iv_qq);

        weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<File> fileList = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    fileList.add(new File(mList.get(i).getPicPath()));
                }
                ShareUtils.startShare(0, "龙浩经纬拍", fileList, context);
                ArrayList<PublicInfo> list = SpUtils.getPublicInfo(context);
                list.get(position).setIsShare(true);
                SpUtils.setPublicInfo(context, list);
                popupWindow.dismiss();
            }
        });

        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<File> fileList = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    fileList.add(new File(mList.get(i).getPicPath()));
                }
                ShareUtils.startShare(2, "龙浩经纬拍", fileList, context);
                ArrayList<PublicInfo> list = SpUtils.getPublicInfo(context);
                list.get(position).setIsShare(true);
                SpUtils.setPublicInfo(context, list);
                popupWindow.dismiss();
            }
        });

        sharePop = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        sharePop.setTouchable(true);
        sharePop.setFocusable(true);
        // 设置点击窗口外边窗口消失
        sharePop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sharePop.setOutsideTouchable(true);
        sharePop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
//        popupWindow.showAsDropDown(rlBottom, 0, 0, Gravity.TOP|Gravity.RIGHT);
        // 设置popWindow的显示和消失动画
        sharePop.setAnimationStyle(R.style.mypopwindow_anim_style);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        sharePop.update();

        sharePop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    /**
     * 上传图片
     */
    private void uploadPic() {

        Observable<Map<String, File>> observable = Observable.create(new ObservableOnSubscribe<Map<String, File>>() {
            @Override
            public void subscribe(final ObservableEmitter<Map<String, File>> e) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    list.add(mList.get(i).getPicPath());
                }
                final List<File> listFile = new ArrayList<>();
                Luban.with(context)
                        .load(list)
                        .ignoreBy(1024)
                        .filter(new CompressionPredicate() {
                            @Override
                            public boolean apply(String path) {
                                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                            }
                        })
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                // TODO 压缩开始前调用，可以在方法内启动 loading UI
                            }

                            @Override
                            public void onSuccess(File file) {
                                // TODO 压缩成功后调用，返回压缩后的图片文件
                                String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                                String newPath = path+"/lhjwp/"+System.currentTimeMillis()+11111111+".jpg";
                                FileUtils.copyFile(file.getPath(), newPath);

//                                listFile.add(file);

//                                Log.e("123123", file.getName());
//
//                                if (listFile.size() == list.size()) {
//                                    Map<String, File> fileMap = new LinkedHashMap<>();
//                                    for (int i = 0; i < listFile.size(); i++) {
//                                        fileMap.put("file" + i, listFile.get(i));
//                                    }
//                                    e.onNext(fileMap);
//                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                            }
                        }).launch();
            }
        });
        Observer<Map<String, File>> observer = new Observer<Map<String, File>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Map<String, File> value) {
                ViseHttp.UPLOAD("/tzapp/phone/upPic.action")
                        .addParam("S_CORP_UUID", "cc7c8fbb99654caa9017031c97c779a0")
                        .addParam("S_SJ", "201805")
                        .addParam("S_TAB", "S812_2018_TZ_RKSQ")
                        .addParam("S_TASK_ID", "")
                        .addParam("type", "1")
                        .addParam("user_name", "230102")
                        .addFiles(value)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("123123", data + "返回的json");
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                Log.e("123123", errMsg);
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
        observable.observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void initMorePop() {

        View view = LayoutInflater.from(PublicContentActivity.this).inflate(R.layout.popupwindow_more, null);
        ScreenAdapterTools.getInstance().loadView(view);

        final Intent intent = new Intent();
        LinearLayout ll1 = view.findViewById(R.id.ll1);
        LinearLayout ll2 = view.findViewById(R.id.ll2);
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.size() > 0) {
                    adapter.setEdit(false);
                    ivBack.setVisibility(View.VISIBLE);
                    tvCancel.setVisibility(View.GONE);
                    tvBottom.setText("分享");
                    tvBottom.setBackgroundColor(Color.parseColor("#2276F6"));
                    intent.putExtra("position", position);
                    intent.putExtra("title", title);
                    intent.setClass(PublicContentActivity.this, PublicPicLocationActivity.class);
                    startActivity(intent);
                } else {
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
            tvBottom.setText("分享");
            tvBottom.setBackgroundColor(Color.parseColor("#2276F6"));
        } else {
            finish();
        }
    }
}
