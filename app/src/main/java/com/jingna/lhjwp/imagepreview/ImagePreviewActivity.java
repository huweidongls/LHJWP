package com.jingna.lhjwp.imagepreview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.info.ProPicInfo;
import com.jingna.lhjwp.utils.SpUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImagePreviewActivity extends AppCompatActivity {

    private Context context = ImagePreviewActivity.this;
    private int itemPosition;
    private ArrayList<ProPicInfo> imageList;
    private CustomViewPager viewPager;
    private LinearLayout main_linear;
    private boolean mIsReturning;
    private int mStartPosition;
    private int mCurrentPosition;
    private ImagePreviewAdapter adapter;
    private String uuid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);

        StatusBarUtils.setStatusBarTransparent(ImagePreviewActivity.this);
//        initShareElement();
        if (getIntent() != null) {
            mStartPosition = getIntent().getIntExtra(Consts.START_IAMGE_POSITION, 0);
            mCurrentPosition = mStartPosition;
            itemPosition = getIntent().getIntExtra(Consts.START_ITEM_POSITION, 0);
            uuid = getIntent().getStringExtra("imageList");
        }
        Map<String, ArrayList<ProPicInfo>> map = SpUtils.getProPicInfo(context);
        imageList = map.get(uuid);
//        getIntentData();
        initView();
        renderView();
        getData();
        setListener();

    }

    private void setListener() {
        main_linear.getChildAt(mCurrentPosition).setEnabled(true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                hideAllIndicator(position);
                main_linear.getChildAt(position).setEnabled(true);
                mCurrentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                final float normalizedposition = Math.abs(Math.abs(position) - 1);
//                page.setScaleX(normalizedposition / 2 + 0.5f);
//                page.setScaleY(normalizedposition / 2 + 0.5f);
//            }
//        });
    }

    private void hideAllIndicator(int position) {
        for (int i = 0; i < imageList.size(); i++) {
            if (i != position) {
                main_linear.getChildAt(i).setEnabled(false);
            }
        }
    }

    private void initView() {
        viewPager = (CustomViewPager) findViewById(R.id.imageBrowseViewPager);
        main_linear = (LinearLayout) findViewById(R.id.main_linear);
    }

    private void renderView() {
        if (imageList == null) return;
        if (imageList.size() == 1) {
            main_linear.setVisibility(View.GONE);
        } else {
            main_linear.setVisibility(View.VISIBLE);
        }
        adapter = new ImagePreviewAdapter(this, imageList, itemPosition, new ImagePreviewAdapter.DeleteListener() {
            @Override
            public void onDelete(int pos) {
                Map<String, ArrayList<ProPicInfo>> map = SpUtils.getProPicInfo(context);
                File file = new File(imageList.get(pos).getPicPath());
                if(file.delete()){
                    imageList.remove(pos);
                    map.put(uuid, imageList);
                    SpUtils.setProPicInfo(context, map);
                }
                adapter.notifyDataSetChanged();
                if(imageList.size() == 0){
                    onBackPressed();
                }else {
                    getData();
                    hideAllIndicator(viewPager.getCurrentItem());
                    main_linear.getChildAt(viewPager.getCurrentItem()).setEnabled(true);
                    mCurrentPosition = viewPager.getCurrentItem();
                    if (imageList == null) return;
                    if (imageList.size() == 1) {
                        main_linear.setVisibility(View.GONE);
                    } else {
                        main_linear.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(mCurrentPosition);
    }

    private void getIntentData() {
        if (getIntent() != null) {
            mStartPosition = getIntent().getIntExtra(Consts.START_IAMGE_POSITION, 0);
            mCurrentPosition = mStartPosition;
            itemPosition = getIntent().getIntExtra(Consts.START_ITEM_POSITION, 0);
            uuid = getIntent().getStringExtra("imageList");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(0, R.anim.photoview_close);
    }

    /**
     * 获取数据
     */
    private void getData() {

        View view;
        main_linear.removeAllViews();
        for (int i = 0; i<imageList.size(); i++){
            //创建底部指示器(小圆点)
            view = new View(ImagePreviewActivity.this);
            view.setBackgroundResource(R.drawable.indicator);
            view.setEnabled(false);
            //设置宽高
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
            //设置间隔
//            if (!pic.equals(imageList.get(0))) {
            layoutParams.rightMargin = 20;
//            }
            //添加到LinearLayout
            main_linear.addView(view, layoutParams);
        }
//        for (String pic : imageList) {
//
//            //创建底部指示器(小圆点)
//            view = new View(ImagePreviewActivity.this);
//            view.setBackgroundResource(R.drawable.indicator);
//            view.setEnabled(false);
//            //设置宽高
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
//            //设置间隔
////            if (!pic.equals(imageList.get(0))) {
//            layoutParams.rightMargin = 20;
////            }
//            //添加到LinearLayout
//            main_linear.addView(view, layoutParams);
//        }
    }


//    @Override
//    public void finishAfterTransition() {
//        mIsReturning = true;
//        Intent data = new Intent();
//        data.putExtra(Consts.START_IAMGE_POSITION, mStartPosition);
//        data.putExtra(Consts.CURRENT_IAMGE_POSITION, mCurrentPosition);
//        data.putExtra(Consts.CURRENT_ITEM_POSITION, itemPosition);
//        setResult(RESULT_OK, data);
//        super.finishAfterTransition();
//    }


//    private final SharedElementCallback mCallback = new SharedElementCallback() {
//
//        @Override
//        public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
//            if (mIsReturning) {
//                ImageView sharedElement = adapter.getPhotoView();
//                if (sharedElement == null) {
//                    names.clear();
//                    sharedElements.clear();
//                } else if (mStartPosition != mCurrentPosition) {
//                    names.clear();
//                    names.add(sharedElement.getTransitionName());
//                    sharedElements.clear();
//                    sharedElements.put(sharedElement.getTransitionName(), sharedElement);
//                }
//            }
//        }
//    };

}
