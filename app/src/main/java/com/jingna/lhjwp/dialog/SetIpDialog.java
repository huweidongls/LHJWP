package com.jingna.lhjwp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.utils.SpUtils;
import com.jingna.lhjwp.utils.ToastUtil;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

/**
 * Created by Administrator on 2018/10/24.
 */

public class SetIpDialog extends Dialog {

    private Context context;
    private RelativeLayout rlClose;
    private TextView tvTitle;
    private EditText et;
    private TextView tvOk;
    private TextView tvReset;
    private OnOkReturnListener listener;

    public SetIpDialog(@NonNull Context context, OnOkReturnListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        initView();
    }

    private void initView() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_set_ip, null);
        setContentView(view);
        ScreenAdapterTools.getInstance().loadView(view);

        rlClose = view.findViewById(R.id.rl_close);
        tvTitle = view.findViewById(R.id.tv_title);
        et = view.findViewById(R.id.et);
        tvOk = view.findViewById(R.id.tv_ok);
        tvReset = view.findViewById(R.id.tv_huifu);

        String ip = SpUtils.getIp(context);
        String newIp = ip.substring(7, ip.length()-1);
        et.setText(newIp);

        rlClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onReturn(et.getText().toString());
                dismiss();
            }
        });

        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onReset();
                dismiss();
            }
        });

    }

    public interface OnOkReturnListener {
        void onReturn(String title);

        void onReset();
    }

}
