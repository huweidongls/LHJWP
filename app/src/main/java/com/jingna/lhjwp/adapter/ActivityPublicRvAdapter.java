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

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.activity.PublicContentActivity;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/24.
 */

public class ActivityPublicRvAdapter extends RecyclerView.Adapter<ActivityPublicRvAdapter.ViewHolder> {

    private Context context;
    private List<String> data;
    private boolean isEdit = false;
    private List<Integer> editList = new ArrayList<>();

    public ActivityPublicRvAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    public void setEdit(boolean isEdit){
        this.isEdit = isEdit;
        notifyDataSetChanged();
    }

    public List<Integer> getEditList(){
        return editList;
    }

    public boolean getIsEdit(){
        return isEdit;
    }

    @Override
    public int getItemViewType(int position) {
        if(isEdit){
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_activity_public, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(getItemViewType(position) == 0){
            holder.rlEdit.setVisibility(View.GONE);
            holder.ivRight.setVisibility(View.VISIBLE);
            holder.ivEdit.setImageResource(R.drawable.sure_kong);
            editList.clear();
        }else if(getItemViewType(position) == 1){
            holder.rlEdit.setVisibility(View.VISIBLE);
            holder.ivRight.setVisibility(View.GONE);
            holder.ivEdit.setImageResource(R.drawable.sure_kong);
            editList.clear();
        }

        holder.rlEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean is = false;
                for (int i = 0; i<editList.size(); i++){
                    if(editList.get(i) == position){
                        holder.ivEdit.setImageResource(R.drawable.sure_kong);
                        editList.remove(i);
                        is = true;
                    }
                }
                if(!is){
                    holder.ivEdit.setImageResource(R.drawable.sure);
                    editList.add(position);
                }
            }
        });

        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getItemViewType(position) == 0){
                    Intent intent = new Intent();
                    intent.setClass(context, PublicContentActivity.class);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data == null?0:data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivEdit;
        private ImageView ivTitle;
        private TextView tvTitle;
        private TextView tvTime;
        private ImageView ivRight;
        private RelativeLayout rlEdit;
        private RelativeLayout rl;

        public ViewHolder(View itemView) {
            super(itemView);
            ivEdit = itemView.findViewById(R.id.iv_sure);
            ivTitle = itemView.findViewById(R.id.iv);
            tvTitle = itemView.findViewById(R.id.tv1);
            tvTime = itemView.findViewById(R.id.tv2);
            ivRight = itemView.findViewById(R.id.iv_right);
            rlEdit = itemView.findViewById(R.id.rl_edit);
            rl = itemView.findViewById(R.id.rl);
        }
    }

}