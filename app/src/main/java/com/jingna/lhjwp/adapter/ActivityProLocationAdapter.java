package com.jingna.lhjwp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.imagepreview.Consts;
import com.jingna.lhjwp.imagepreview.ImagePreviewActivity;
import com.jingna.lhjwp.info.ProPicInfo;
import com.jingna.lhjwp.info.PublicInfo;
import com.jingna.lhjwp.widget.LocateCenterHorizontalView;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/29.
 */

public class ActivityProLocationAdapter extends RecyclerView.Adapter<ActivityProLocationAdapter.ViewHolder> {

    private Context context;
    private List<ProPicInfo> data;
    private View view;
    private int select = 0;
    private OnSelectListener selectListener;

    public void setSelectListener(OnSelectListener selectListener) {
        this.selectListener = selectListener;
    }

    public ActivityProLocationAdapter(Context context, List<ProPicInfo> data) {
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
            holder.iv2.setVisibility(View.VISIBLE);
            holder.rl1.setVisibility(View.VISIBLE);
        }else {
            Glide.with(context).load(data.get(position).getPicPath()).into(holder.iv);
            holder.iv2.setVisibility(View.GONE);
            holder.rl1.setVisibility(View.GONE);
        }
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = position;
                notifyDataSetChanged();
                selectListener.onSelect(position);
            }
        });
        holder.rl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                List<String> urlList = new ArrayList<>();
                for (int i = 0; i<data.size(); i++){
                    urlList.add("file://"+data.get(i).getPicPath());
                }
                Intent intent = new Intent(context, ImagePreviewActivity.class);
                intent.putStringArrayListExtra("imageList", (ArrayList<String>) urlList);
                intent.putExtra(Consts.START_ITEM_POSITION, position);
                intent.putExtra(Consts.START_IAMGE_POSITION, position);
//                ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(getActivity(), imageView, imageView.getTransitionName());
                context.startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.photoview_open, 0);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null?0:data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(select == position){
            return 1;
        }else {
            return 2;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv;
        private ImageView iv1;
        private RelativeLayout rl;
        private ImageView iv2;
        private RelativeLayout rl1;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            iv1 = itemView.findViewById(R.id.iv1);
            rl = itemView.findViewById(R.id.rl);
            iv2 = itemView.findViewById(R.id.iv2);
            rl1 = itemView.findViewById(R.id.rl1);
        }
    }

    public interface OnSelectListener{
        void onSelect(int pos);
    }

}
