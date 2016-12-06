package com.example.administrator.activitycommunity.model;

/**
 * Created by Daniel on 2016/11/15.
 */

public class Login {

    /**
     * code : 1
     * message :
     * user : null
     */

    private int code;
    private String message;
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
