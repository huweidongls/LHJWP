package com.jingna.lhjwp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jingna.lhjwp.R;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

/**
 * Created by Administrator on 2019/4/8.
 */

public class VersionDialog extends Dialog {

    private Context context;
    private TextView tvUpData;
    private ImageView ivCancel;
    private TextView tvUpdataTime;
    private TextView tvInfo;

    private ClickListener listener;

    private String time;
    private String info;

    public VersionDialog(@NonNull Context context, String time, String info, ClickListener listener) {
        super(context, R.style.RoundCornerDialog);
        this.context = context;
        this.listener = listener;
        this.time = time;
        this.info = info;
        initView();
    }

    private void initView() {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_version, null);
        setContentView(view);
        ScreenAdapterTools.getInstance().loadView(view);

        tvUpData = view.findViewById(R.id.tv_updata);
        ivCancel = view.findViewById(R.id.iv_cancel);
        tvUpdataTime = view.findViewById(R.id.tv_updata_time);
        tvInfo = view.findViewById(R.id.tv_info);

        tvUpdataTime.setText("更新时间: "+time);
        tvInfo.setText(info);

        tvUpData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUpdata();
                dismiss();
            }
        });

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel();
                dismiss();
            }
        });

    }

    public interface ClickListener{
        void onUpdata();
        void onCancel();
    }

}
