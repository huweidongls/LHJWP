package com.jingna.lhjwp.info;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/11/1.
 */

public class ProPicInfo implements Serializable {

    String picPath;
    String time;
    double latitude;
    double longitude;
    String address;
    boolean isUpload;

    public ProPicInfo(String picPath, String time, double latitude, double longitude, String address, boolean isUpload) {
        this.picPath = picPath;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.isUpload = isUpload;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public void setUpload(boolean upload) {
        isUpload = upload;
    }
}
