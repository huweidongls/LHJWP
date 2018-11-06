package com.jingna.lhjwp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.info.PublicInfo;
import com.jingna.lhjwp.widget.LocateCenterHorizontalView;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.List;

/**
 * Created by Administrator on 2018/10/29.
 */

public class ActivityPublicLocationAdapter extends RecyclerView.Adapter<ActivityPublicLocationAdapter.ViewHolder> {

    private Context context;
    private List<PublicInfo.PicInfo> data;
    private View view;
    private int select = 0;
    private OnSelectListener selectListener;

    public void setSelectListener(OnSelectListener selectListener) {
        this.selectListener = selectListener;
    }

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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(getItemViewType(position) == 1){
            Glide.with(context).load(data.get(position).getPicPath()).into(holder.iv1);
        }else {
            Glide.with(context).load(data.get(position).getPicPath()).into(holder.iv);
        }
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = position;
                notifyDataSetChanged();
                selectListener.onSelect(position);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if(select == position){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return data == null?0:data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv;
        private ImageView iv1;
        private RelativeLayout rl;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            iv1 = itemView.findViewById(R.id.iv1);
            rl = itemView.findViewById(R.id.rl);
        }
    }

    public interface OnSelectListener{
        void onSelect(int pos);
    }

}
