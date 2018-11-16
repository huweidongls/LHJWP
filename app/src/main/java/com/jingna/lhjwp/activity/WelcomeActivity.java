package com.jingna.lhjwp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.imagepreview.StatusBarUtils;
import com.jingna.lhjwp.utils.Const;
import com.jingna.lhjwp.utils.SpUtils;
import com.vise.xsnow.http.ViseHttp;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {

    private Context context = WelcomeActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        StatusBarUtils.setStatusBarTransparent(WelcomeActivity.this);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        init();

    }

    private void init() {

        String ip = SpUtils.getIp(context);
        if(TextUtils.isEmpty(ip)){
            ViseHttp.CONFIG()
                    .baseUrl(Const.BASE_URL);
        }else {
            ViseHttp.CONFIG()
                    .baseUrl(ip);
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this, TypeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

    }
}
