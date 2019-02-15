package com.jingna.lhjwp.model;

/**
 * Created by Administrator on 2019/2/13.
 */

public class GpsResult {

    private String latitude;
    private String longitude;

    public GpsResult(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
