package com.jingna.lhjwp.utils;

import android.content.Context;

import com.jingna.lhjwp.info.PublicInfo;
import com.vise.xsnow.cache.SpCache;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/10/26.
 */

public class SpUtils {

    private static SpCache spCache;
    public static String PUBLIC_FILENAME = "publicinfo";
    public static String PUBLIC_INFO = "publicinfo";
    public static String PUBLIC_STRING = "publicSTRING";

    public static void setPublicInfo(Context context, ArrayList<PublicInfo> publicInfo){
        spCache = new SpCache(context, "public_info");
        spCache.put(PUBLIC_INFO, publicInfo);
    }

    public static Object getPublicInfo(Context context){
        spCache = new SpCache(context, "public_info");
//        if(spCache.get(PUBLIC_INFO) == null){
//            return new ArrayList<PublicInfo>();
//        }else {
            return spCache.get(PUBLIC_INFO);
//        }
    }

    public static void setString(Context context, String publicInfo){
        spCache = new SpCache(context, "public_info");
        spCache.put(PUBLIC_STRING, publicInfo);
    }

    public static String getString(Context context){
        spCache = new SpCache(context, "public_info");
        return spCache.get(PUBLIC_STRING, "1");
    }

}
