package com.example.administrator.activitycommunity.model;

/**
 * Created by Daniel on 2016/12/8.
 */

public class Event {
    String msg;
    int orderId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }



    public Event(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
