package com.jingna.lhjwp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.imagepreview.Consts;
import com.jingna.lhjwp.imagepreview.ImagePreviewActivity;
import com.jingna.lhjwp.info.PublicInfo;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/26.
 */

public class PicAddShowAdapter extends RecyclerView.Adapter<PicAddShowAdapter.ViewHolder> {

    private Context context;
    private List<PublicInfo.PicInfo> data;
    private boolean isEdit = false;
    private List<Integer> editList = new ArrayList<>();

    private static final int TYPE_ADD = 1;
    private static final int TYPE_PIC = 2;
    private static final int MAX_SIZE = 9;

    private OnAddImgListener addListener;
    private ShowImgListener showImgListener;
    private ViewHolder holder;

    public void setShowImgListener(ShowImgListener showImgListener) {
        this.showImgListener = showImgListener;
    }

    public void setListener(OnAddImgListener addListener) {
        this.addListener = addListener;
    }

    public PicAddShowAdapter(List<PublicInfo.PicInfo> data) {
        this.data = data;
    }

    public void setEdit(boolean isEdit){
        this.isEdit = isEdit;
        editList.clear();
        notifyDataSetChanged();
    }

    public boolean getEdit(){
        return isEdit;
    }

    public List<Integer> getEditList(){
        return editList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_add_show_pic, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        if (data.size() >= MAX_SIZE) {
//            //最多9张
////            holder.rlAdd.setVisibility(View.GONE);
//        } else {
//            holder.rlImg.setVisibility(View.VISIBLE);
//            holder.rlAdd.setVisibility(View.VISIBLE);
//        }
        if (getItemViewType(position) == TYPE_ADD) {
            if(isEdit){
                holder.rlAdd.setVisibility(View.GONE);
            }else {
                holder.rlAdd.setVisibility(View.VISIBLE);
            }
            holder.rlImg.setVisibility(View.GONE);
        } else {
            if(isEdit){
                holder.ivSure.setVisibility(View.VISIBLE);
                holder.ivSure.setImageResource(R.drawable.sure_kong);
            }else {
                holder.ivSure.setVisibility(View.GONE);
            }
            holder.rlAdd.setVisibility(View.GONE);
            holder.rlImg.setVisibility(View.VISIBLE);
            Glide.with(context).load("file://" + data.get(position).getPicPath()).into(holder.iv);
            holder.tvTime.setText(data.get(position).getPicTime());
        }
        holder.rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addListener.onAddImg();
            }
        });
//        holder.ivSure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean is = false;
//                for (int i = 0; i<editList.size(); i++){
//                    if(editList.get(i) == position){
//                        holder.ivSure.setImageResource(R.drawable.sure_kong);
//                        editList.remove(i);
//                        is = true;
//                    }
//                }
//                if(!is){
//                    holder.ivSure.setImageResource(R.drawable.sure);
//                    editList.add(position);
//                }
//            }
//        });
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEdit){
                    boolean is = false;
                    for (int i = 0; i<editList.size(); i++){
                        if(editList.get(i) == position){
                            holder.ivSure.setImageResource(R.drawable.sure_kong);
                            editList.remove(i);
                            is = true;
                        }
                    }
                    if(!is){
                        holder.ivSure.setImageResource(R.drawable.sure);
                        editList.add(position);
                    }
                }else {
                    showImgListener.showImg(position);
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_ADD;
        } else {
            return TYPE_PIC;
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlAdd;
        private RelativeLayout rlImg;
        private ImageView iv;
        private TextView tvTime;
        private ImageView ivSure;

        public ViewHolder(View itemView) {
            super(itemView);
            rlAdd = itemView.findViewById(R.id.activity_create_intercalation_rv_rl_add);
            rlImg = itemView.findViewById(R.id.activity_create_intercalation_rv_rl_img);
            iv = itemView.findViewById(R.id.activity_create_intercalation_rv_iv_img);
            tvTime = itemView.findViewById(R.id.tv_time);
            ivSure = itemView.findViewById(R.id.iv_sure);
        }
    }

    public interface OnAddImgListener {
        void onAddImg();
    }

    public interface ShowImgListener{
        void showImg(int pos);
    }

}
