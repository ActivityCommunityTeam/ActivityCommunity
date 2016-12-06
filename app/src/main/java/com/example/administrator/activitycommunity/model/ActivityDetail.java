package com.example.administrator.activitycommunity.model;

/**
 * Created by Daniel on 2016/11/29.
 */

public class ActivityDetail {

    /**
     * attended : 0
     * site : 测试地点
     * image_url : http://211.149.235.17:8080/hdsq/upload/20161110164036951.png
     * price : 150
     * activity_id : 13
     * end_time : 2016-11-30 14:45:00
     * begin_time : 2016-11-09 14:45:00
     * sponsor_name : 王思聪美女队
     * activity_title : 测试活动
     */

    private int attended;
    private String site;
    private String image_url;
    private int price;
    private int activity_id;
    private String end_time;
    private String begin_time;
    private String sponsor_name;
    private String activity_title;

    public int getAttended() {
        return attended;
    }

    public void setAttended(int attended) {
        this.attended = attended;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public String getSponsor_name() {
        return sponsor_name;
    }

    public void setSponsor_name(String sponsor_name) {
        this.sponsor_name = sponsor_name;
    }

    public String getActivity_title() {
        return activity_title;
    }

    public void setActivity_title(String activity_title) {
        this.activity_title = activity_title;
    }
}
