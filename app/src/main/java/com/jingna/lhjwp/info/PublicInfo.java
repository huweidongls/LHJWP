package com.jingna.lhjwp.info;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/10/26.
 */

public class PublicInfo implements Serializable {

    String title;
    String time;
    String createDate;
    int collect;
    boolean isShare = false;
    List<PicInfo> picList;

    public PublicInfo(String title, String time, String createDate, int collect, List<PicInfo> picList) {
        this.title = title;
        this.time = time;
        this.createDate = createDate;
        this.collect = collect;
        this.picList = picList;
    }

    public boolean getIsShare() {
        return isShare;
    }

    public void setIsShare(boolean isShare) {
        this.isShare = isShare;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<PicInfo> getPicList() {
        return picList;
    }

    public void setPicList(List<PicInfo> picList) {
        this.picList = picList;
    }

    public static class PicInfo implements Serializable{
        String picPath;
        double picLatitude;
        double picLongitude;
        String picAddress;
        String picTime;

        public PicInfo(String picPath, double picLatitude, double picLongitude, String picAddress, String picTime) {
            this.picPath = picPath;
            this.picLatitude = picLatitude;
            this.picLongitude = picLongitude;
            this.picAddress = picAddress;
            this.picTime = picTime;
        }

        public String getPicPath() {
            return picPath;
        }

        public void setPicPath(String picPath) {
            this.picPath = picPath;
        }

        public double getPicLatitude() {
            return picLatitude;
        }

        public void setPicLatitude(double picLatitude) {
            this.picLatitude = picLatitude;
        }

        public double getPicLongitude() {
            return picLongitude;
        }

        public void setPicLongitude(double picLongitude) {
            this.picLongitude = picLongitude;
        }

        public String getPicAddress() {
            return picAddress;
        }

        public void setPicAddress(String picAddress) {
            this.picAddress = picAddress;
        }

        public String getPicTime() {
            return picTime;
        }

        public void setPicTime(String picTime) {
            this.picTime = picTime;
        }
    }

}
