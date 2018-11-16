package com.jingna.lhjwp.imagepreview;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.jingna.lhjwp.dialog.CustomDialog;
import com.jingna.lhjwp.info.PublicInfo;
import com.jingna.lhjwp.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/5 0005.
 */

public class PublicImagePreviewAdapter extends PagerAdapter {
    private Context context;
    private List<PublicInfo.PicInfo> imageList;
    private int itemPosition;
    private PhotoView photoView;
    private int pos;
    private DeleteListener deleteListener;
    public PublicImagePreviewAdapter(Context context, List<PublicInfo.PicInfo> imageList, int itemPosition, DeleteListener deleteListener) {
        this.context = context;
        this.imageList = imageList;
        this.itemPosition = itemPosition;
        this.deleteListener = deleteListener;
    }

    @Override
    public int getCount() {
        return imageList==null?0:imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final PhotoView image = new PhotoView(context);
        image.setEnabled(true);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        image.setMaximumScale(2.0F);
        image.setMinimumScale(0.8F);

        Glide.with(context).load("file://"+imageList.get(position).getPicPath()).into(image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setEnabled(false);
                ((Activity)context).onBackPressed();
            }
        });

        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CustomDialog customDialog = new CustomDialog(context, "是否删除当前照片", new CustomDialog.OnSureListener() {
                    @Override
                    public void onSure() {
                        deleteListener.onDelete(position);
                    }
                });
                customDialog.show();
                return true;
            }
        });
        container.addView(image);
        return image;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        photoView = (PhotoView) object;
//        photoView.setTag(Utils.getNameByPosition(itemPosition,position));
//        photoView.setTransitionName(Utils.getNameByPosition(itemPosition,position));
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    //解决ViewPager数据源改变时,刷新无效的解决办法
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public PhotoView getPhotoView() {
        return photoView;
    }

    public interface DeleteListener{
        void onDelete(int pos);
    }

}
