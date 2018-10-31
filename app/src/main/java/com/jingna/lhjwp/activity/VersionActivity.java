package com.jingna.lhjwp.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VersionActivity extends BaseActivity {

    private Context context = VersionActivity.this;

    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(VersionActivity.this);

        init();

    }

    private void init() {

        String url = "/tzapp/phone/getVersion.action?device_type=1";
        ViseHttp.GET(url)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("123123", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getString("result").equals("1")){
                                tvVersion.setText(jsonObject.getString("versionname"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

    }

    @OnClick({R.id.rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
        }
    }

}
