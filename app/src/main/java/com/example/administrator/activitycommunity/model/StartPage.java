package com.example.administrator.activitycommunity.model;

/**
 * Created by Daniel on 2016/11/15.
 */

public class StartPage {

    /**
     * ad_id : 2
     * ad_remark :描述
     * ad_url : http://localhost:8080/hdsq/upload/20161023141959460.jpg
     * ad_sort : 1 排序
     * ad_status : 01
     */

    private int ad_id;
    private String ad_remark;
    private String ad_url;
    private int ad_sort;
    private String ad_status;

    public int getAd_id() {
        return ad_id;
    }

    public void setAd_id(int ad_id) {
        this.ad_id = ad_id;
    }

    public String getAd_remark() {
        return ad_remark;
    }

    public void setAd_remark(String ad_remark) {
        this.ad_remark = ad_remark;
    }

    public String getAd_url() {
        return ad_url;
    }

    public void setAd_url(String ad_url) {
        this.ad_url = ad_url;
    }

    public int getAd_sort() {
        return ad_sort;
    }

    public void setAd_sort(int ad_sort) {
        this.ad_sort = ad_sort;
    }

    public String getAd_status() {
        return ad_status;
    }

    public void setAd_status(String ad_status) {
        this.ad_status = ad_status;
    }

    @Override
    public String toString() {
        return "StartPage{" +
                "ad_id=" + ad_id +
                ", ad_remark='" + ad_remark + '\'' +
                ", ad_url='" + ad_url + '\'' +
                ", ad_sort=" + ad_sort +
                ", ad_status='" + ad_status + '\'' +
                '}';
    }
}
