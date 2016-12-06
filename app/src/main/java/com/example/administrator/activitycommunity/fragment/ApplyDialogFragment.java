package com.example.administrator.activitycommunity.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.activitycommunity.R;
import com.example.administrator.activitycommunity.model.ActivityDetail;

/**
 * Created by Daniel on 2016/12/6.
 */

public class ApplyDialogFragment extends DialogFragment implements View.OnClickListener {
    private Button xqApplyBtn;
    private Button xqCancelBtn;
    private TextView begintimeTv;
    private TextView moneyTv;
    private TextView titleTv;
    public static final int FILL_PARENT = -1;
    public static final int MATCH_PARENT = -1;
    public static final int WRAP_CONTENT = -2;

    private ActivityDetail activityDetail;

    public ApplyDialogFragment(ActivityDetail activityDetail) {
        this.activityDetail = activityDetail;
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
        begintimeTv.setText("开始时间："+activityDetail.getBegin_time());
        moneyTv.setText("￥"+activityDetail.getPrice());
        titleTv.setText(activityDetail.getActivity_title());
        xqApplyBtn.setOnClickListener(this);
        xqCancelBtn.setOnClickListener(this);
        return view;
    }

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.zhifu, null);
//        begintimeTv = (TextView) view.findViewById(R.id.begintime_tv);
//        moneyTv = (TextView) view.findViewById(R.id.money_tv);
//        xqApplyBtn = (Button) view.findViewById(R.id.xq_apply_btn);
//        xqCancelBtn = (Button) view.findViewById(R.id.xq_cancel_btn);
//        titleTv = (TextView) view.findViewById(R.id.title_tv);
//
//        begintimeTv.setText("开始时间："+activityDetail.getBegin_time());
//        moneyTv.setText("￥"+activityDetail.getPrice());
//        titleTv.setText(activityDetail.getActivity_title());
//        xqApplyBtn.setOnClickListener(this);
//        xqCancelBtn.setOnClickListener(this);
//
//          /*
//         * 将对话框的大小按屏幕大小的百分比设置
//         */
//        WindowManager wm = getActivity().getWindowManager();
//        int width = wm.getDefaultDisplay().getWidth()/2;
//        int height = wm.getDefaultDisplay().getHeight()/2;
////        Log.i("Daniel", "ApplyDialogFragment---onCreateDialog---height---"+height);
////        Log.i("Daniel", "ApplyDialogFragment---onCreateDialog--- view.getLayoutParams()---"+ view.getLayoutParams());
////        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
////        params.width = width;
////        params.height=height;
////        view.setLayoutParams(params);
////        WindowManager m = getWindowManager();
////        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
////        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
////        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
////        p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.65
////        dialogWindow.setAttributes(p);
//
//
//
//        // Inflate and set the layout for the dialog
//        // Pass null as the parent view because its going in the dialog layout
//        builder.setView(view);
////        builder.setView(view)
////                // Add action buttons
////                .setPositiveButton("Sign in",
////                        new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialog, int id) {
////                            }
////                        }).setNegativeButton("Cancel", null);
//
//        return builder.create();
//    }
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
