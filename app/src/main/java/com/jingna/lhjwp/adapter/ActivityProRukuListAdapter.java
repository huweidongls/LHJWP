package com.jingna.lhjwp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.activity.ProContentActivity;
import com.jingna.lhjwp.info.ProPicInfo;
import com.jingna.lhjwp.model.RukuListModel;
import com.jingna.lhjwp.utils.SpUtils;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/31.
 */

public class ActivityProRukuListAdapter extends RecyclerView.Adapter<ActivityProRukuListAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<RukuListModel.XmListBean> data;
    private List<RukuListModel.XmListBean> mFilterList = new ArrayList<>();
    private Map<String, ArrayList<ProPicInfo>> map;

    public ActivityProRukuListAdapter(Context context, List<RukuListModel.XmListBean> data) {
        this.context = context;
        this.data = data;
        this.mFilterList = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        map = SpUtils.getProPicInfo(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_ruku_list, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (mFilterList.get(position).getS_ZY().equals("1")) {
            Glide.with(context).load(R.drawable.tz).into(holder.iv);
        } else {
            Glide.with(context).load(R.drawable.lou).into(holder.iv);
        }
        holder.tvTitle.setText(mFilterList.get(position).getS_XMMC());
        holder.tvAddress.setText(mFilterList.get(position).getS_ADDRESS());
        if(mFilterList.get(position).getS_UP_FLAG().equals("1")){
            holder.tvType.setTextColor(Color.parseColor("#009EFF"));
            holder.tvType.setText("已上传");
        }else {
            holder.tvType.setTextColor(Color.parseColor("#FF0000"));
            holder.tvType.setText("未上传");
        }

        if(map.get(mFilterList.get(position).getS_CORP_UUID())==null||map.get(mFilterList.get(position).getS_CORP_UUID()).size()==0){
            holder.rlNum.setBackgroundResource(R.drawable.bg_shape_red);
            holder.tvNum.setText("0");
        }else {
            holder.rlNum.setBackgroundResource(R.drawable.bg_shape_blue);
            holder.tvNum.setText(map.get(mFilterList.get(position).getS_CORP_UUID()).size()+"");
        }

        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProContentActivity.class);
                intent.putExtra("type", mFilterList.get(position).getType());
                intent.putExtra("S_SJ", mFilterList.get(position).getS_SJ());
                intent.putExtra("S_TAB", mFilterList.get(position).getS_TAB());
                intent.putExtra("title", mFilterList.get(position).getS_XMMC());
                intent.putExtra("uuid", mFilterList.get(position).getS_CORP_UUID());
                intent.putExtra("cankao", mFilterList.get(position).getS_COORDINATE_CK());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            //执行过滤操作
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    //没有过滤的内容，则使用源数据
                    mFilterList = data;
                } else {
                    List<RukuListModel.XmListBean> filteredList = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getS_XMMC().contains(charString)) {
                            filteredList.add(data.get(i));
                        }
                    }

                    mFilterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterList;
                return filterResults;
            }

            //把过滤后的值返回出来
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilterList = (List<RukuListModel.XmListBean>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return mFilterList == null ? 0 : mFilterList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tvTitle;
        private TextView tvType;
        private TextView tvAddress;
        private RelativeLayout rl;
        private RelativeLayout rlNum;
        private TextView tvNum;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvType = itemView.findViewById(R.id.tv_type);
            tvAddress = itemView.findViewById(R.id.tv_address);
            rl = itemView.findViewById(R.id.rl);
            rlNum = itemView.findViewById(R.id.rl_num);
            tvNum = itemView.findViewById(R.id.tv_num);
        }
    }

}
