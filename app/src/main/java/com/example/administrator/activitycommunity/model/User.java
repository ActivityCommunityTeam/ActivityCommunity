package com.example.administrator.activitycommunity.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Daniel on 2016/11/15.
 */

public class User extends RealmObject {
    @PrimaryKey
    private int user_id;
    private String user_no;
    private String nickname;
    private String phone_num;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_no='" + user_no + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phone_num='" + phone_num + '\'' +
                '}';
    }
}
