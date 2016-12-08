package com.example.administrator.activitycommunity.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.activitycommunity.R;

/**
 * Created by Daniel on 2016/12/6.
 */

public class ApplyDialogFragment extends DialogFragment implements View.OnClickListener {
    private Button xqApplyBtn;
    private Button xqCancelBtn;
    private TextView begintimeTv;
    private TextView moneyTv;
    private TextView titleTv;


    private String mTitle;
    private String mTime;
    private int mPrice;

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
                Log.i("Daniel", "ApplyDialogFragment---onClick---xq_apply_btn---");


                break;
            case R.id.xq_cancel_btn:
                Log.i("Daniel", "ApplyDialogFragment---onClick---xq_cancel_btn---");
                dismiss();

                break;
        }
    }

}
