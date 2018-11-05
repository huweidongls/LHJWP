package com.jingna.lhjwp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jingna.lhjwp.R;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

/**
 * Created by Administrator on 2018/11/5.
 */

public class CustomDialog extends Dialog {

    private Context context;
    private String title;

    private TextView tvTitle;
    private TextView tvSure;
    private TextView tvCancel;

    private OnSureListener onSureListener;

    public CustomDialog(@NonNull Context context, String title, OnSureListener onSureListener) {
        super(context);
        this.context = context;
        this.title = title;
        this.onSureListener = onSureListener;
        initView();
    }

    private void initView() {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null);
        setContentView(view);
        ScreenAdapterTools.getInstance().loadView(view);

        tvTitle = view.findViewById(R.id.tv_title);
        tvSure = view.findViewById(R.id.tv_sure);
        tvCancel = view.findViewById(R.id.tv_cancel);

        tvTitle.setText(title);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSureListener.onSure();
                dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public interface OnSureListener{
        void onSure();
    }

}
