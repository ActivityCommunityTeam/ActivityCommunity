package com.example.administrator.activitycommunity.model;

/**
 * Created by Administrator on 2016/11/10.
 * 图片轮播model
 */

public class Carousel {


    /**
     * image_url : 地址
     * activity_id : 活动id
     * activity_title :活动title
     */

    private String image_url;
    private int activity_id;
    private String activity_title;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public String getActivity_title() {
        return activity_title;
    }

    public void setActivity_title(String activity_title) {
        this.activity_title = activity_title;
    }
}
