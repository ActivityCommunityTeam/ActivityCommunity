package com.example.administrator.activitycommunity.model;

/**
 * Created by Daniel on 2016/11/15.
 */

public class SaveOrder {


    /**
     * order_no : 161205093310368454
     * code : 1
     * message :
     * order_id : 12
     */

    private String order_no;
    private int code;
    private String message;
    private int order_id;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}
