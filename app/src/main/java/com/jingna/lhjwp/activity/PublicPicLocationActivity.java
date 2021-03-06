package com.jingna.lhjwp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.jingna.lhjwp.adapter.ActivityPublicLocationAdapter;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.imagepreview.Consts;
import com.jingna.lhjwp.imagepreview.ImagePreviewActivity;
import com.jingna.lhjwp.imagepreview.PublicImagePreviewActivity;
import com.jingna.lhjwp.info.PublicInfo;
import com.jingna.lhjwp.utils.SpUtils;
import com.jingna.lhjwp.widget.LocateCenterHorizontalView;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublicPicLocationActivity extends BaseActivity {

    private Context context = PublicPicLocationActivity.this;

    @BindView(R.id.activity_public_pic_location_mapview)
    MapView mMapView;
    @BindView(R.id.activity_public_pic_location_rv)
    RecyclerView recyclerview;
    @BindView(R.id.activity_public_pic_location_tv_top)
    TextView tvTop;

    private ActivityPublicLocationAdapter adapter;
    private ArrayList<PublicInfo.PicInfo> mList = new ArrayList<>();

    private int position;
    private String title = "";

    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_pic_location);
        position = getIntent().getIntExtra("position", 0);
        title = getIntent().getStringExtra("title");
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PublicPicLocationActivity.this);
        mBaiduMap = mMapView.getMap();
//        initData();

    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {

        tvTop.setText(title);
        mList.clear();
        mList.addAll(SpUtils.getPublicInfo(context).get(position).getPicList());
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(manager);
        adapter = new ActivityPublicLocationAdapter(this, mList);
        recyclerview.setAdapter(adapter);
        adapter.setSelectListener(new ActivityPublicLocationAdapter.OnSelectListener() {
            @Override
            public void onSelect(int pos) {
                onMap(pos);
            }

            @Override
            public void onLongClick(int pos) {
//                List<String> urlList = new ArrayList<>();
//                for (int i = 0; i<data.size(); i++){
//                    urlList.add("file://"+data.get(i).getPicPath());
//                }
                Intent intent = new Intent(context, PublicImagePreviewActivity.class);
                intent.putExtra("imageList", position);
                intent.putExtra(Consts.START_ITEM_POSITION, pos);
                intent.putExtra(Consts.START_IAMGE_POSITION, pos);
//                ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(getActivity(), imageView, imageView.getTransitionName());
                context.startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.photoview_open, 0);
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
                options.add(new MarkerOptions().position(new LatLng(mList.get(i).getPicLatitude(), mList.get(i).getPicLongitude())).icon(bitmap1));
            }else {
                options.add(new MarkerOptions().position(new LatLng(mList.get(i).getPicLatitude(), mList.get(i).getPicLongitude())).icon(bitmap));
            }
        }
        mBaiduMap.addOverlays(options);

        //定位到规定路线起点
        //设定中心点坐标
        if(mList.size()>0){
            LatLng cenpt =  new LatLng(mList.get(position).getPicLatitude(),mList.get(position).getPicLongitude());
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
