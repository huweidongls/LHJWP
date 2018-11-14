package com.jingna.lhjwp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VoiceActivity extends BaseActivity {

    private Context context = VoiceActivity.this;
    private static final String TAG = "VoiceActivity";

    @BindView(R.id.rl_result)
    RelativeLayout rlResult;
    @BindView(R.id.tv_result)
    TextView tvResult;

    // 语音听写对象
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    private boolean mTranslateEnable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(VoiceActivity.this);

    }

    @OnClick({R.id.rl_back, R.id.iv_voice, R.id.rl_result})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_voice:
                break;
            case R.id.rl_result:
                String result = tvResult.getText().toString();
                intent.putExtra("result", result);
                setResult(1, intent);
                finish();
                break;
        }
    }


}
