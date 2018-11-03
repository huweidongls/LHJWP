package com.jingna.lhjwp.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

import com.jingna.lhjwp.info.ProPicInfo;
import com.jingna.lhjwp.info.PublicInfo;
import com.vise.xsnow.cache.SpCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/26.
 */

public class SpUtils {

    private static SpCache spCache;
    public static String PUBLIC_INFO = "publicinfo";
    public static String PRO_USERNAME = "prousername";
    public static String PRO_S_ORGAN = "S_ORGAN";
    public static String PRO_PIC_INFO = "pro_pic_info";
    public static String PRO_LAST_TIME = "pro_last_time";
    public static String MAX_PIC = "max_pic";
    public static String MIN_PIC = "min_pic";

    public static String getMinPic(Context context){
        spCache = new SpCache(context, "pro_info");
        return spCache.get(MIN_PIC, "3");
    }

    public static void setMinPic(Context context, String minpic){
        spCache = new SpCache(context, "pro_info");
        spCache.put(MIN_PIC, minpic);
    }

    public static String getMaxPic(Context context){
        spCache = new SpCache(context, "pro_info");
        return spCache.get(MAX_PIC, "10");
    }

    public static void setMaxPic(Context context, String maxpic){
        spCache = new SpCache(context, "pro_info");
        spCache.put(MAX_PIC, maxpic);
    }

    public static void setLastTime(Context context, String time){
        spCache = new SpCache(context, "pro_info");
        spCache.put(PRO_LAST_TIME, time);
    }

    public static String getLastTime(Context context){
        spCache = new SpCache(context, "pro_info");
        return spCache.get(PRO_LAST_TIME, "");
    }

    public static void setProPicInfo(Context context, Map<String, ArrayList<ProPicInfo>> map){
        spCache = new SpCache(context, "pro_info");
        spCache.put(PRO_PIC_INFO, map);
    }

    public static Map<String, ArrayList<ProPicInfo>> getProPicInfo(Context context){
        spCache = new SpCache(context, "pro_info");
        if(spCache.get(PRO_PIC_INFO) == null){
            return new HashMap<String, ArrayList<ProPicInfo>>();
        }else {
            return (Map<String, ArrayList<ProPicInfo>>) spCache.get(PRO_PIC_INFO);
        }
    }

    public static void setPublicInfo(Context context, ArrayList<PublicInfo> publicInfo){
        spCache = new SpCache(context, "public_info");
        spCache.put(PUBLIC_INFO, publicInfo);
    }

    public static ArrayList<PublicInfo> getPublicInfo(Context context){
        spCache = new SpCache(context, "public_info");
        if(spCache.get(PUBLIC_INFO) == null){
            return new ArrayList<PublicInfo>();
        }else {
            return (ArrayList<PublicInfo>) spCache.get(PUBLIC_INFO);
        }
    }

    public static void setUsername(Context context, String username){
        spCache = new SpCache(context, "pro_info");
        spCache.put(PRO_USERNAME, username);
    }

    public static String getUsername(Context context){
        spCache = new SpCache(context, "pro_info");
        return spCache.get(PRO_USERNAME, "0");
    }

    public static void setS_ORGAN(Context context, String S_ORGAN){
        spCache = new SpCache(context, "pro_info");
        spCache.put(PRO_S_ORGAN, S_ORGAN);
    }

    public static String getS_ORGAN(Context context){
        spCache = new SpCache(context, "pro_info");
        return spCache.get(PRO_S_ORGAN, "0");
    }

    public static String getDeviceId(Context context){
        String  deviceId = null;
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        }
        return deviceId;
    }

}
