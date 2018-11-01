package com.jingna.lhjwp.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
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

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.adapter.PicAddShowAdapter;
import com.jingna.lhjwp.adapter.ProPicAddShowAdapter;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.info.ProPicInfo;
import com.jingna.lhjwp.info.PublicInfo;
import com.jingna.lhjwp.utils.SpUtils;
import com.jingna.lhjwp.utils.ToastUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

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

    private ProPicAddShowAdapter adapter;
    private ArrayList<ProPicInfo> mList = new ArrayList<>();

    private PopupWindow popupWindow;

    private String uuid = "";
    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_show_pic);
        uuid = getIntent().getStringExtra("uuid");
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
                Intent intent = new Intent();
                intent.putExtra("uuid", uuid);
                intent.setClass(context, ProCameraActivity.class);
                startActivity(intent);
            }
        });

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
                }else {
                    ToastUtil.showShort(context, "上传");
                    uploadPic();
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
                final List<String> list = new ArrayList<>();
                for (int i = 0; i<mList.size(); i++){
                    list.add(mList.get(i).getPicPath());
                }
                final List<File> listFile = new ArrayList<>();
                Luban.with(context)
                        .load(list)
                        .ignoreBy(500)
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

                                listFile.add(file);

                                Log.e("123123", file.getName());

                                if (listFile.size() == list.size()) {
                                    Map<String, File> fileMap = new LinkedHashMap<>();
                                    for (int i = 0; i < listFile.size(); i++) {
                                        fileMap.put("file"+i, listFile.get(i));
                                    }
                                    e.onNext(fileMap);
                                }
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
                        .addParam("S_CORP_UUID", uuid)
                        .addParam("S_SJ", "201805")
                        .addParam("S_TAB", "S812_2018_TZ_RKSQ")
                        .addParam("S_TASK_ID", "")
                        .addParam("type", "1")
                        .addParam("user_name", username)
                        .addFiles(value)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("123123", data+"返回的json");
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

}