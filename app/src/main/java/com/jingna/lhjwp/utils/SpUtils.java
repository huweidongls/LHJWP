package com.jingna.lhjwp.utils;

import android.content.Context;

import com.jingna.lhjwp.info.PublicInfo;
import com.vise.xsnow.cache.SpCache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/26.
 */

public class SpUtils {

    private static SpCache spCache;
    public static String PUBLIC_INFO = "publicinfo";

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

}
