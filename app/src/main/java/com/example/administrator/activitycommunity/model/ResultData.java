package com.example.administrator.activitycommunity.model;

/**
 * Created by Daniel on 2016/11/16.
 */

public class ResultData<T> {
    private int code;
    private String message;
    private T user;
    private T saveOrder;

    public T getSaveOrder() {
        return saveOrder;
    }

    public void setSaveOrder(T saveOrder) {
        this.saveOrder = saveOrder;
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

    public T getUser() {
        return user;
    }

    public void setUser(T user) {
        this.user = user;
    }
}
