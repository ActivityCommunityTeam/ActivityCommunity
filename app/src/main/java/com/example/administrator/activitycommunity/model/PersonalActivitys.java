package com.example.administrator.activitycommunity.model;

import java.io.Serializable;

/**
 * Created by Daniel on 2016/12/5.
 */

public class PersonalActivitys implements Serializable {

    /**
     * site : 万安山dfd
     * enrol_time : 2016-12-02 15:24:15
     * image_url : http://211.149.235.17:8080/hdsq/upload/20161110164426710.jpg
     * pay_amount : 333
     * activity_id : 6
     * end_time : 2017-03-03 19:00:00
     * begin_time : 2016-10-07 00:00:00
     * activity_title : 测试活动
     */

    private String enrol_time;
    private String site;
    private String image_url;
    private int pay_amount;
    private int activity_id;
    private String end_time;
    private String begin_time;
    private String activity_title;
    private String order_no;
    private String pay_status;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getEnrol_time() {
        return enrol_time;
    }

    public void setEnrol_time(String enrol_time) {
        this.enrol_time = enrol_time;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(int pay_amount) {
        this.pay_amount = pay_amount;
    }

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getActivity_title() {
        return activity_title;
    }

    public void setActivity_title(String activity_title) {
        this.activity_title = activity_title;
    }
}
