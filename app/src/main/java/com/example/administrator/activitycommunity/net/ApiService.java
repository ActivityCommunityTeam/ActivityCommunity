package com.example.administrator.activitycommunity.net;

import com.example.administrator.activitycommunity.model.ActivityDetail;
import com.example.administrator.activitycommunity.model.Activitys;
import com.example.administrator.activitycommunity.model.AttentionStatus;
import com.example.administrator.activitycommunity.model.Carousel;
import com.example.administrator.activitycommunity.model.Login;
import com.example.administrator.activitycommunity.model.Payment;
import com.example.administrator.activitycommunity.model.PersonalActivitys;
import com.example.administrator.activitycommunity.model.ResultData;
import com.example.administrator.activitycommunity.model.SaveOrUpdateAtten;
import com.example.administrator.activitycommunity.model.SaveOrder;
import com.example.administrator.activitycommunity.model.StartPage;
import com.example.administrator.activitycommunity.model.User;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/11/10.
 */

public interface ApiService {

    @GET("getAttentionStatus/{user_id}-{activity_id}")
    Observable<AttentionStatus> getAttentionStatus(@Path("user_id")int user_id,@Path("activity_id")int activity_id);
    /**
     * 获取个人参与的活动
     * @param lzbz
     * @param user_id
     * @return
     */
    @GET("getPersonalActivitys/{lzbz}-{user_id}")
    Observable<List<PersonalActivitys>> getPersonalActivitys(@Path("lzbz")int lzbz, @Path("user_id")int user_id);
    /**
     * 支付信息保存
     * @param order_id
     * @param status
     * @param pay_third
     * @param amount
     * @return
     */
    @FormUrlEncoded
    @POST("payment")
    Observable<Payment> payment(@Field("order_id")int order_id, @Field("status")String status, @Field("pay_third")String pay_third, @Field("amount")String amount);

    /**
     * 保存订单
     * @param user_id
     * @param activity_id
     * @param pay_amount
     * @return
     */

    @FormUrlEncoded
    @POST("saveOrder")
    Observable<SaveOrder> saveOrder(@Field("user_id")int user_id,@Field("activity_id")int activity_id,@Field("pay_amount")String pay_amount);

    /**
     * 活动关注
     * @param user_id
     * @param activity_id
     * @param status
     * @return
     */
    @FormUrlEncoded
    @POST("saveOrUpdateAtten")
    Observable<SaveOrUpdateAtten> SaveOrUpdateAtten(@Field("user_id")int user_id, @Field("activity_id")int activity_id, @Field("status")String status);

    /**
     * 获取活动详情
     * @return
     */
    @GET("getActivityDetail/{activityId}")
    Observable<ActivityDetail> getActivityDetail(@Path("activityId")int activityId);
    /**
     * 获取图片
     * @return
     */
    @GET("getAdMsgs")
    Observable<List<StartPage>> getAdMsgs();
    /**
     * 获取轮播图片
     * @return
     */
    @GET("carousel")
    Observable<List<Carousel>> getCarousel();

    @FormUrlEncoded
    @POST("login")
    Observable<ResultData<User>> getLogin(@Field("user_no")String user_no);

    @FormUrlEncoded
    @POST("login")
    Observable<Login> getLogin2(@Field("user_no")String user_no);

    /**
     * 获取活动列表
     * @param sign
     * @param date_time
     * @return
     */

    @FormUrlEncoded
    @POST("getActivitys")
    Observable<List<Activitys>> getActivitys(@Field("sign")String sign,@Field("date_time")String date_time);



}
