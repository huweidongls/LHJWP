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
import com.jingna.lhjwp.activity.ProContentActivity;
import com.jingna.lhjwp.model.RukuListModel;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.List;

/**
 * Created by Administrator on 2018/10/31.
 */

public class ActivityProRukuListAdapter extends RecyclerView.Adapter<ActivityProRukuListAdapter.ViewHolder> {

    private Context context;
    private List<RukuListModel.XmListBean> data;

    public ActivityProRukuListAdapter(Context context, List<RukuListModel.XmListBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_ruku_list, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(data.get(position).getS_ZY().equals("1")){
            Glide.with(context).load(R.drawable.tz).into(holder.iv);
        }else {
            Glide.with(context).load(R.drawable.lou).into(holder.iv);
        }
        holder.tvTitle.setText(data.get(position).getS_XMMC());
        holder.tvAddress.setText(data.get(position).getS_ADDRESS());
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProContentActivity.class);
                intent.putExtra("uuid", data.get(position).getS_CORP_UUID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null?0:data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv;
        private TextView tvTitle;
        private TextView tvType;
        private TextView tvAddress;
        private RelativeLayout rl;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvType = itemView.findViewById(R.id.tv_type);
            tvAddress = itemView.findViewById(R.id.tv_address);
            rl = itemView.findViewById(R.id.rl);
        }
    }

}
