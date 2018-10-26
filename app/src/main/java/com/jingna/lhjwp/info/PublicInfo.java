package com.jingna.lhjwp.info;

import java.util.List;

/**
 * Created by Administrator on 2018/10/26.
 */

public class PublicInfo {

    String title;
    String time;
    List<PicInfo> picList;

    public PublicInfo(String title) {
        this.title = title;
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

    class PicInfo{
        String picPath;
        String picLocation;
        String picAddress;
        String picTime;

        public String getPicPath() {
            return picPath;
        }

        public void setPicPath(String picPath) {
            this.picPath = picPath;
        }

        public String getPicLocation() {
            return picLocation;
        }

        public void setPicLocation(String picLocation) {
            this.picLocation = picLocation;
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
