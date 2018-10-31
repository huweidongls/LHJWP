package com.jingna.lhjwp.model;

/**
 * Created by Administrator on 2018/10/31.
 */

public class TaskListModel {

    /**
     * result : 1
     * bgTask : {"tzTime":"","fdcTime":"","fdcAllXm":"","fdcFinish":"","tzFinish":"","type":"3","tzAllXm":""}
     * jgTask : {"tzTime":"09-10～12-10","fdcTime":"10-15～10-31","fdcAllXm":"0","fdcFinish":"0","tzFinish":"0","type":"4","tzAllXm":"0"}
     * xkgTask : {"tzTime":"05-01～05-31","fdcTime":"09-01～09-30","fdcAllXm":"85","fdcFinish":"0","tzFinish":"0","type":"1","tzAllXm":"5"}
     * jdTask : {"tzTime":"","fdcTime":"","fdcAllXm":"0","fdcFinish":"0","tzFinish":"0","type":"2","tzAllXm":"0"}
     */

    private String result;
    private BgTaskBean bgTask;
    private JgTaskBean jgTask;
    private XkgTaskBean xkgTask;
    private JdTaskBean jdTask;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public BgTaskBean getBgTask() {
        return bgTask;
    }

    public void setBgTask(BgTaskBean bgTask) {
        this.bgTask = bgTask;
    }

    public JgTaskBean getJgTask() {
        return jgTask;
    }

    public void setJgTask(JgTaskBean jgTask) {
        this.jgTask = jgTask;
    }

    public XkgTaskBean getXkgTask() {
        return xkgTask;
    }

    public void setXkgTask(XkgTaskBean xkgTask) {
        this.xkgTask = xkgTask;
    }

    public JdTaskBean getJdTask() {
        return jdTask;
    }

    public void setJdTask(JdTaskBean jdTask) {
        this.jdTask = jdTask;
    }

    public static class BgTaskBean {
        /**
         * tzTime :
         * fdcTime :
         * fdcAllXm :
         * fdcFinish :
         * tzFinish :
         * type : 3
         * tzAllXm :
         */

        private String tzTime;
        private String fdcTime;
        private String fdcAllXm;
        private String fdcFinish;
        private String tzFinish;
        private String type;
        private String tzAllXm;

        public String getTzTime() {
            return tzTime;
        }

        public void setTzTime(String tzTime) {
            this.tzTime = tzTime;
        }

        public String getFdcTime() {
            return fdcTime;
        }

        public void setFdcTime(String fdcTime) {
            this.fdcTime = fdcTime;
        }

        public String getFdcAllXm() {
            return fdcAllXm;
        }

        public void setFdcAllXm(String fdcAllXm) {
            this.fdcAllXm = fdcAllXm;
        }

        public String getFdcFinish() {
            return fdcFinish;
        }

        public void setFdcFinish(String fdcFinish) {
            this.fdcFinish = fdcFinish;
        }

        public String getTzFinish() {
            return tzFinish;
        }

        public void setTzFinish(String tzFinish) {
            this.tzFinish = tzFinish;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTzAllXm() {
            return tzAllXm;
        }

        public void setTzAllXm(String tzAllXm) {
            this.tzAllXm = tzAllXm;
        }
    }

    public static class JgTaskBean {
        /**
         * tzTime : 09-10～12-10
         * fdcTime : 10-15～10-31
         * fdcAllXm : 0
         * fdcFinish : 0
         * tzFinish : 0
         * type : 4
         * tzAllXm : 0
         */

        private String tzTime;
        private String fdcTime;
        private String fdcAllXm;
        private String fdcFinish;
        private String tzFinish;
        private String type;
        private String tzAllXm;

        public String getTzTime() {
            return tzTime;
        }

        public void setTzTime(String tzTime) {
            this.tzTime = tzTime;
        }

        public String getFdcTime() {
            return fdcTime;
        }

        public void setFdcTime(String fdcTime) {
            this.fdcTime = fdcTime;
        }

        public String getFdcAllXm() {
            return fdcAllXm;
        }

        public void setFdcAllXm(String fdcAllXm) {
            this.fdcAllXm = fdcAllXm;
        }

        public String getFdcFinish() {
            return fdcFinish;
        }

        public void setFdcFinish(String fdcFinish) {
            this.fdcFinish = fdcFinish;
        }

        public String getTzFinish() {
            return tzFinish;
        }

        public void setTzFinish(String tzFinish) {
            this.tzFinish = tzFinish;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTzAllXm() {
            return tzAllXm;
        }

        public void setTzAllXm(String tzAllXm) {
            this.tzAllXm = tzAllXm;
        }
    }

    public static class XkgTaskBean {
        /**
         * tzTime : 05-01～05-31
         * fdcTime : 09-01～09-30
         * fdcAllXm : 85
         * fdcFinish : 0
         * tzFinish : 0
         * type : 1
         * tzAllXm : 5
         */

        private String tzTime;
        private String fdcTime;
        private String fdcAllXm;
        private String fdcFinish;
        private String tzFinish;
        private String type;
        private String tzAllXm;

        public String getTzTime() {
            return tzTime;
        }

        public void setTzTime(String tzTime) {
            this.tzTime = tzTime;
        }

        public String getFdcTime() {
            return fdcTime;
        }

        public void setFdcTime(String fdcTime) {
            this.fdcTime = fdcTime;
        }

        public String getFdcAllXm() {
            return fdcAllXm;
        }

        public void setFdcAllXm(String fdcAllXm) {
            this.fdcAllXm = fdcAllXm;
        }

        public String getFdcFinish() {
            return fdcFinish;
        }

        public void setFdcFinish(String fdcFinish) {
            this.fdcFinish = fdcFinish;
        }

        public String getTzFinish() {
            return tzFinish;
        }

        public void setTzFinish(String tzFinish) {
            this.tzFinish = tzFinish;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTzAllXm() {
            return tzAllXm;
        }

        public void setTzAllXm(String tzAllXm) {
            this.tzAllXm = tzAllXm;
        }
    }

    public static class JdTaskBean {
        /**
         * tzTime :
         * fdcTime :
         * fdcAllXm : 0
         * fdcFinish : 0
         * tzFinish : 0
         * type : 2
         * tzAllXm : 0
         */

        private String tzTime;
        private String fdcTime;
        private String fdcAllXm;
        private String fdcFinish;
        private String tzFinish;
        private String type;
        private String tzAllXm;

        public String getTzTime() {
            return tzTime;
        }

        public void setTzTime(String tzTime) {
            this.tzTime = tzTime;
        }

        public String getFdcTime() {
            return fdcTime;
        }

        public void setFdcTime(String fdcTime) {
            this.fdcTime = fdcTime;
        }

        public String getFdcAllXm() {
            return fdcAllXm;
        }

        public void setFdcAllXm(String fdcAllXm) {
            this.fdcAllXm = fdcAllXm;
        }

        public String getFdcFinish() {
            return fdcFinish;
        }

        public void setFdcFinish(String fdcFinish) {
            this.fdcFinish = fdcFinish;
        }

        public String getTzFinish() {
            return tzFinish;
        }

        public void setTzFinish(String tzFinish) {
            this.tzFinish = tzFinish;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTzAllXm() {
            return tzAllXm;
        }

        public void setTzAllXm(String tzAllXm) {
            this.tzAllXm = tzAllXm;
        }
    }
}
