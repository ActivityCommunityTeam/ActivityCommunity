package com.example.administrator.activitycommunity.model;

/**
 * 活动model
 * Created by Administrator on 2016/11/10.
 */

public class Activitys {

    /**
     * attended : 已报名人数
     * site : 地点
     * image_url : 图片地址
     * price : 价格
     * activity_id : 活动id
     * end_time : 活动结束时间
     * begin_time : 活动开始时间
     * max_num_people : 可报名人数
     * activity_title : 活动名称
     */

    private int attended;
    private String site;
    private String image_url;
    private int price;
    private int activity_id;
    private String end_time;
    private String begin_time;
    private int max_num_people;
    private String activity_title;

    public String getSponsor_name() {
        return sponsor_name;
    }

    public void setSponsor_name(String sponsor_name) {
        this.sponsor_name = sponsor_name;
    }

    private String sponsor_name;

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

    public int getMax_num_people() {
        return max_num_people;
    }

    public void setMax_num_people(int max_num_people) {
        this.max_num_people = max_num_people;
    }

    public String getActivity_title() {
        return activity_title;
    }

    public void setActivity_title(String activity_title) {
        this.activity_title = activity_title;
    }
}
