package com.jingna.lhjwp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.adapter.ActivityProLocationAdapter;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.imagepreview.Consts;
import com.jingna.lhjwp.imagepreview.ImagePreviewActivity;
import com.jingna.lhjwp.info.ProPicInfo;
import com.jingna.lhjwp.utils.Gps;
import com.jingna.lhjwp.utils.PositionUtil;
import com.jingna.lhjwp.utils.SpUtils;
import com.jingna.lhjwp.widget.LocateCenterHorizontalView;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProPicLocationActivity extends BaseActivity {

    private Context context = ProPicLocationActivity.this;

    @BindView(R.id.activity_public_pic_location_mapview)
    MapView mMapView;
    @BindView(R.id.activity_public_pic_location_rv)
    RecyclerView recyclerview;
    @BindView(R.id.activity_public_pic_location_tv_top)
    TextView tvTop;
    @BindView(R.id.iv_is_show)
    ImageView ivIsShow;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.rl_location)
    RelativeLayout rlLocation;
    @BindView(R.id.ll_cankaoweizhi)
    LinearLayout llCankaoweizhi;

    private ActivityProLocationAdapter adapter;
    private ArrayList<ProPicInfo> mList = new ArrayList<>();

    private BaiduMap mBaiduMap;

    private String uuid = "";
    private String title = "";
    private String type = "";
    private String cankao = "";

    private boolean isShow = true;

    private int select = 0;

    private Gps gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_pic_location);
        uuid = getIntent().getStringExtra("uuid");
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        cankao = getIntent().getStringExtra("cankao");
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(ProPicLocationActivity.this);
        mBaiduMap = mMapView.getMap();
//        initData();

    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {

        if(type.equals("1")){
            llCankaoweizhi.setVisibility(View.GONE);
            rlLocation.setVisibility(View.GONE);
        }
        tvTop.setText(title);
        Map<String, ArrayList<ProPicInfo>> map = SpUtils.getProPicInfo(context);
        if(map.get(uuid) != null){
            mList.clear();
            mList.addAll(map.get(uuid));
        }
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(manager);
        adapter = new ActivityProLocationAdapter(this, mList);
        recyclerview.setAdapter(adapter);
        adapter.setSelectListener(new ActivityProLocationAdapter.OnSelectListener() {
            @Override
            public void onSelect(int pos) {
                select = pos;
                onMap();
            }

            @Override
            public void onLongClick(int pos) {
                Intent intent = new Intent(context, ImagePreviewActivity.class);
                intent.putExtra("imageList", uuid);
                intent.putExtra(Consts.START_ITEM_POSITION, pos);
                intent.putExtra(Consts.START_IAMGE_POSITION, pos);
//                ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(getActivity(), imageView, imageView.getTransitionName());
                context.startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.photoview_open, 0);
            }
        });
        select = 0;
        onMap();

    }

    @OnClick({R.id.activity_public_pic_location_rl_back, R.id.iv_is_show})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_public_pic_location_rl_back:
                finish();
                break;
            case R.id.iv_is_show:
                if(isShow){
                    Glide.with(context).load(R.drawable.off).into(ivIsShow);
                    isShow = false;
                    onMap();
                }else {
                    Glide.with(context).load(R.drawable.on).into(ivIsShow);
                    isShow = true;
                    onMap();
                }
                break;
        }
    }

    private void onMap(){

        mBaiduMap.clear();
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.location);
        BitmapDescriptor bitmap1 = BitmapDescriptorFactory
                .fromResource(R.drawable.location_big);
        BitmapDescriptor bitmap2 = BitmapDescriptorFactory
                .fromResource(R.drawable.location_y);

        List<OverlayOptions> options = new ArrayList<OverlayOptions>();
        for (int i = 0; i<mList.size(); i++){
            if(i == select){
                options.add(new MarkerOptions().position(new LatLng(mList.get(i).getLatitude(), mList.get(i).getLongitude())).icon(bitmap1));
            }else {
                options.add(new MarkerOptions().position(new LatLng(mList.get(i).getLatitude(), mList.get(i).getLongitude())).icon(bitmap));
            }
        }

        if(!TextUtils.isEmpty(cankao)){
            if(isShow){

                String[] s = cankao.split("\\+");
                for (int i = 0; i<s.length; i++){
                    String[] ss = s[i].split(",");
                    gps = PositionUtil.gps84_To_Gcj02(Double.valueOf(ss[0]), Double.valueOf(ss[1]));
                    options.add(new MarkerOptions().position(new LatLng(gps.getWgLat(), gps.getWgLon())).icon(bitmap2));
                }

            }
        }

        mBaiduMap.addOverlays(options);

        //定位到规定路线起点
        //设定中心点坐标
        if(mList.size()>0){
            LatLng cenpt =  new LatLng(mList.get(select).getLatitude(),mList.get(select).getLongitude());
            //定义地图状态
            MapStatus mMapStatus = new MapStatus.Builder()
                    //要移动的点
                    .target(cenpt)
                    //放大地图到20倍
                    .zoom(19)
                    .build();
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            //改变地图状态
            mBaiduMap.setMapStatus(mMapStatusUpdate);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

}
