package com.jingna.lhjwp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.info.PublicInfo;
import com.jingna.lhjwp.widget.LocateCenterHorizontalView;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.List;

/**
 * Created by Administrator on 2018/10/29.
 */

public class ActivityPublicLocationAdapter extends RecyclerView.Adapter<ActivityPublicLocationAdapter.ViewHolder> implements LocateCenterHorizontalView.IAutoLocateHorizontalView {

    private Context context;
    private List<PublicInfo.PicInfo> data;
    private View view;

    public ActivityPublicLocationAdapter(Context context, List<PublicInfo.PicInfo> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_location_public, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(data.get(position).getPicPath()).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return data == null?0:data.size();
    }

    @Override
    public View getItemView() {
        return view;
    }

    @Override
    public void onViewSelected(boolean isSelected, int pos, RecyclerView.ViewHolder holder, int itemWidth) {
        if (isSelected) {
            ((ViewHolder) holder).iv1.setVisibility(View.VISIBLE);
            Glide.with(context).load(data.get(pos).getPicPath()).into(((ViewHolder) holder).iv1);
        } else {
            ((ViewHolder) holder).iv1.setVisibility(View.GONE);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv;
        private ImageView iv1;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            iv1 = itemView.findViewById(R.id.iv1);
        }
    }

}
