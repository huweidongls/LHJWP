package com.jingna.lhjwp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
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
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.adapter.ActivityProLocationAdapter;
import com.jingna.lhjwp.adapter.ActivityPublicLocationAdapter;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.info.ProPicInfo;
import com.jingna.lhjwp.info.PublicInfo;
import com.jingna.lhjwp.utils.SpUtils;
import com.jingna.lhjwp.widget.LocateCenterHorizontalView;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class ProPicLocationActivity extends BaseActivity {

    private Context context = ProPicLocationActivity.this;

    @BindView(R.id.activity_public_pic_location_mapview)
    MapView mMapView;
    @BindView(R.id.activity_public_pic_location_rv)
    LocateCenterHorizontalView recyclerview;
    @BindView(R.id.activity_public_pic_location_tv_top)
    TextView tvTop;

    private ActivityProLocationAdapter adapter;
    private ArrayList<ProPicInfo> mList = new ArrayList<>();

    private BaiduMap mBaiduMap;

    private String uuid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_pic_location);
        uuid = getIntent().getStringExtra("uuid");
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(ProPicLocationActivity.this);
        mBaiduMap = mMapView.getMap();
        initData();

    }

    private void initData() {

        Map<String, ArrayList<ProPicInfo>> map = SpUtils.getProPicInfo(context);
        if(map.get(uuid) != null){
            mList.clear();
            mList.addAll(map.get(uuid));
        }
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new ActivityProLocationAdapter(this, mList);
        recyclerview.setAdapter(adapter);
        recyclerview.setOnSelectedPositionChangedListener(new LocateCenterHorizontalView.OnSelectedPositionChangedListener() {
            @Override
            public void selectedPositionChanged(int pos) {
                onMap(pos);
            }
        });

        onMap(0);

    }

    @OnClick({R.id.activity_public_pic_location_rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_public_pic_location_rl_back:
                finish();
                break;
        }
    }

    private void onMap(int position){

        mBaiduMap.clear();
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.location);
        BitmapDescriptor bitmap1 = BitmapDescriptorFactory
                .fromResource(R.drawable.location_big);

        List<OverlayOptions> options = new ArrayList<OverlayOptions>();
        for (int i = 0; i<mList.size(); i++){
            if(i == position){
                options.add(new MarkerOptions().position(new LatLng(mList.get(i).getLatitude(), mList.get(i).getLongitude())).icon(bitmap1));
            }else {
                options.add(new MarkerOptions().position(new LatLng(mList.get(i).getLatitude(), mList.get(i).getLongitude())).icon(bitmap));
            }
        }
        mBaiduMap.addOverlays(options);

        //定位到规定路线起点
        //设定中心点坐标
        LatLng cenpt =  new LatLng(mList.get(position).getLatitude(),mList.get(position).getLongitude());
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                //要移动的点
                .target(cenpt)
                //放大地图到20倍
                .zoom(16)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
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
