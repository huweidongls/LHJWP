package com.jingna.lhjwp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.utils.ToastUtil;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

/**
 * Created by Administrator on 2018/10/24.
 */

public class AddDialog extends Dialog {

    private Context context;
    private RelativeLayout rlClose;
    private EditText et;
    private TextView tvOk;
    private OnOkReturnListener listener;

    public AddDialog(@NonNull Context context, OnOkReturnListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        initView();
    }

    private void initView() {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add, null);
        setContentView(view);
        ScreenAdapterTools.getInstance().loadView(view);

        rlClose = view.findViewById(R.id.rl_close);
        et = view.findViewById(R.id.et);
        tvOk = view.findViewById(R.id.tv_ok);

        rlClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et.getText().toString())){
                    ToastUtil.showShort(context, "相册名称不能为空");
                }else if(et.getText().toString().length()>10||et.getText().toString().length()<4){
                    ToastUtil.showShort(context, "请输入4-10个字符");
                }else {
                    listener.onReturn(et.getText().toString());
                    dismiss();
                }
            }
        });

    }

    public interface OnOkReturnListener{
        void onReturn(String title);
    }

}
