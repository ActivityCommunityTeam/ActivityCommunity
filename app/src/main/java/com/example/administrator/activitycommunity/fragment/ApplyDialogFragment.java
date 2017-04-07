package com.example.administrator.activitycommunity.fragment;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.activitycommunity.R;
import com.example.administrator.activitycommunity.activity.ApplySuccessActivity;
import com.example.administrator.activitycommunity.model.Event;
import com.example.administrator.activitycommunity.model.Payment;
import com.example.administrator.activitycommunity.model.SaveOrder;
import com.example.administrator.activitycommunity.net.NetWork;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.MODE_WORLD_READABLE;

/**
 * Created by Daniel on 2016/12/6.
 */

public class ApplyDialogFragment extends DialogFragment implements View.OnClickListener {
    private Button xqApplyBtn;
    private Button xqCancelBtn;
    private TextView begintimeTv;
    private TextView moneyTv;
    private TextView titleTv;
    private ImageView img;


    private String mTitle;
    private String mTime;
    private int mPrice;
    private int activityId;

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public void setmPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhifu, null);
        EventBus.getDefault().register(this);
        begintimeTv = (TextView) view.findViewById(R.id.begintime_tv);
        moneyTv = (TextView) view.findViewById(R.id.money_tv);
        xqApplyBtn = (Button) view.findViewById(R.id.xq_apply_btn);
        xqCancelBtn = (Button) view.findViewById(R.id.xq_cancel_btn);
        titleTv = (TextView) view.findViewById(R.id.title_tv);

        begintimeTv.setText("开始时间："+mTime);
        moneyTv.setText("￥"+mPrice);
        titleTv.setText(mTitle);
        xqApplyBtn.setOnClickListener(this);
        xqCancelBtn.setOnClickListener(this);

        this.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
           case R.id.xq_apply_btn:
               saveOrder();



                break;
            case R.id.xq_cancel_btn:
                Log.i("Daniel", "ApplyDialogFragment---onClick---xq_cancel_btn---");
                dismiss();

                break;
        }
    }

    private void payment(int orderId) {
        Log.i("Daniel", "ApplyDialogFragment---payment---orderId---"+orderId);
        Log.i("Daniel", "ApplyDialogFragment---payment---mPrice---"+mPrice);
        String _price = mPrice+"";
        NetWork.getApiService().payment(orderId,"01","01",_price)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Payment>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Daniel", "ApplyDialogFragment---支付失败!---");
                    }

                    @Override
                    public void onNext(Payment payment) {
                        int code=payment.getCode();
                        if (code==1){
                            Intent intent = new Intent(getActivity(), ApplySuccessActivity.class);
                            intent.putExtra("price",""+mPrice);
                            startActivity(intent);
                            dismiss();
                            Log.i("Daniel", "ApplyDialogFragment---支付成功!---");
                        }else {
                            Toast.makeText(getActivity(), "支付失败!", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void onEventMainThread(Event event) {

        String msg = event.getMsg();
        int _orderId=event.getOrderId();
        Log.i("Daniel", "ApplyDialogFragment---onEventMainThread---_orderId---"+_orderId);
        if (msg.equals("saveOrder")){
            payment(_orderId);
        }

    }

    private void saveOrder() {
        SharedPreferences read = getActivity().getSharedPreferences("user", MODE_WORLD_READABLE);
        int _userId = read.getInt("userId",-1);
        Log.i("Daniel", "ApplyDialogFragment---saveOrder---userId---"+_userId);
        Log.i("Daniel", "ApplyDialogFragment---saveOrder---mPrice---"+mPrice);
        Log.i("Daniel", "ApplyDialogFragment---saveOrder---activityId---"+activityId);
        String _price = mPrice+"";
        NetWork.getApiService().saveOrder(_userId,activityId,_price)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SaveOrder>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Daniel", "ApplyDialogFragment---保存订单失败!---");

                    }

                    @Override
                    public void onNext(SaveOrder saveOrder) {
                        Log.i("Daniel", "ApplyDialogFragment---保存订单成功!---");
                        Event _event = new Event("saveOrder");
                        _event.setOrderId(saveOrder.getOrder_id());
                        EventBus.getDefault().post(_event);



                    }
                });
    }

}
