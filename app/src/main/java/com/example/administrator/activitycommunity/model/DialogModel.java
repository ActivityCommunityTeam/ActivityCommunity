package com.example.administrator.activitycommunity.model;

/**
 * Created by Daniel on 2016/12/8.
 */

public class DialogModel<T> {
    private T Activitys;
    private T ActivityDetail;

    public T getActivitys() {
        return Activitys;
    }

    public void setActivitys(T activitys) {
        Activitys = activitys;
    }

    public T getActivityDetail() {
        return ActivityDetail;
    }

    public void setActivityDetail(T activityDetail) {
        ActivityDetail = activityDetail;
    }
}
