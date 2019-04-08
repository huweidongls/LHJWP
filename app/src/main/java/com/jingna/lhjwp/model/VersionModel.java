package com.jingna.lhjwp.model;

/**
 * Created by Administrator on 2019/4/8.
 */

public class VersionModel {

    /**
     * versionnum : 2
     * versiontype : 1
     * versionname : 1.2
     * downurl : http://tzjc.tjj.zj.gov.cn/tzapp/resource/LHjwp.apk
     * updatetime : 2019-04-01
     * versionnote :
     * result : 1
     */

    private String versionnum;
    private String versiontype;
    private String versionname;
    private String downurl;
    private String updatetime;
    private String versionnote;
    private int result;

    public String getVersionnum() {
        return versionnum;
    }

    public void setVersionnum(String versionnum) {
        this.versionnum = versionnum;
    }

    public String getVersiontype() {
        return versiontype;
    }

    public void setVersiontype(String versiontype) {
        this.versiontype = versiontype;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getVersionnote() {
        return versionnote;
    }

    public void setVersionnote(String versionnote) {
        this.versionnote = versionnote;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
