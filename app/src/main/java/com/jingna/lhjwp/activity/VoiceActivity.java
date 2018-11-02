package com.jingna.lhjwp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.jingna.lhjwp.R;
import com.jingna.lhjwp.base.BaseActivity;
import com.jingna.lhjwp.utils.JsonParser;
import com.jingna.lhjwp.utils.ToastUtil;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.json.JSONException;
import org.json.JSONObject;

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
    private SpeechRecognizer mIat;
    RecognizerDialog mIatDialog;
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    private boolean mTranslateEnable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(VoiceActivity.this);
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(VoiceActivity.this, mInitListener);
        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(VoiceActivity.this, mInitListener);

    }

    @OnClick({R.id.rl_back, R.id.iv_voice, R.id.rl_result})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_voice:
                //2.设置accent、 language等参数
                mIatDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");//简体中文：zh_cn（默认）；美式英文：en_us：poi；音乐：music
                mIatDialog.setParameter(SpeechConstant.ACCENT, "mandarin");//方言普通话：mandarin(默认);粤 语：cantonese四川话：lmz;河南话：henanese
                mIatDialog.setParameter(SpeechConstant.ASR_AUDIO_PATH, "./tts_test.pcm");    //识别完成后在本地保存一个音频文件
                //若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解
                //结果
                // mDialog.setParameter("asr_sch", "1");
                // mDialog.setParameter("nlp_version", "2.0");
                //开始识别并设置监听器
                mIatDialog.setListener(mRecognizerDialogListener);
                //获取字体所在的控件，设置为"",隐藏字体，
//                TextView txt = (TextView)mIatDialog.getWindow().getDecorView().findViewWithTag("textlink");
//                txt.setText(" ");
                //显示听写对话框
                mIatDialog.show();
                break;
            case R.id.rl_result:
                String result = tvResult.getText().toString();
                intent.putExtra("result", result);
                setResult(1, intent);
                finish();
                break;
        }
    }

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results);
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            ToastUtil.showShort(context, "翻译失败");
        }

    };

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                ToastUtil.showShort(context, "初始化失败，错误码：" + code);
            }
        }
    };

    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        String result = resultBuffer.toString().replace("。", "");
        tvResult.setText(result);
        rlResult.setVisibility(View.VISIBLE);
//        mResultText.setText(resultBuffer.toString());
//        mResultText.setSelection(mResultText.length());
    }

}
