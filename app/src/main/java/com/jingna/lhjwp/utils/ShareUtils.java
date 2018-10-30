package com.jingna.lhjwp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/30.
 */

public class ShareUtils {

    /**
     * @param type     0微信朋友 分享,1微信朋友圈，2 QQ好友分享 , 3 qq空间分享 ,4新浪分享
     * @param fileList 文件list，注意（是本地文件，网络图片需要上网下载到本地保存了才能分享）
     * @param context
     */
    public static void startShare(int type, String content, List<File> fileList, Context context) {
        String mPackageName = "";
        String mActivityName = "";
        String uninstallTips = "";
        switch (type) {
            case 0:  //微信
                mPackageName = "com.tencent.mm";
                mActivityName = "com.tencent.mm.ui.tools.ShareImgUI";
                uninstallTips = "0";
                break;
            case 1: //微信朋友圈
                mPackageName = "com.tencent.mm";
                mActivityName = "com.tencent.mm.ui.tools.ShareToTimeLineUI";
                uninstallTips = "1";
                break;
            case 2: //qq好友分享
                mPackageName = "com.tencent.mobileqq";
                mActivityName = "com.tencent.mobileqq.activity.JumpActivity";
                uninstallTips = "2";
                break;
            case 3: //QQ空间，貌似我找不到qq空间的分享，所以暂时不支持
                uninstallTips = "暂时不支持";
                break;
            case 4:  //新浪分享
                mPackageName = "com.sina.weibo";
                mActivityName = "com.sina.weibo.EditActivity";
                uninstallTips = "4";
                break;
        }

        if (!uninstallSoftware(context, mPackageName)) {
            Toast.makeText(context, uninstallTips, Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<Uri> uriList = new ArrayList<>();
        for (File file : fileList) {
            if (Build.VERSION.SDK_INT >= 24) {
//                uriList.add(FileProvider.getUriForFile(context, "com.jingna.lhjwp", file));
                uriList.add(getImageContentUri(context, file));
            } else {
                uriList.add(Uri.fromFile(file));
            }
        }
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (type == 4) {  //如果是新浪微博就不用设置activity接收（重点，因为部分微博客户端是不需要设置的，否则会显示无法打开微博客户端）
            shareIntent.setPackage(mPackageName);
        } else {
            shareIntent.setClassName(mPackageName, mActivityName);
        }
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        shareIntent.putExtra("Kdescription", content);  //设置该属性就能设置分享文本内容
//        shareIntent.putExtra(Intent.EXTRA_STREAM, uriList.get(0)); //单张图片分享
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);  //多张图片
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "Share images to.."));
    }

    /**
     * 检测客户端有木有安装
     *
     * @param context
     * @param packageName
     * @return
     */
    private static boolean uninstallSoftware(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
            if (packageInfo != null) {
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param context
     * @param imageFile
     * @return content Uri
     */
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

}
