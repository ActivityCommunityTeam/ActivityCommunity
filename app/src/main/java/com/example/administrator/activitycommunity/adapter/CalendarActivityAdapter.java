package com.example.administrator.activitycommunity.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.activitycommunity.R;
import com.example.administrator.activitycommunity.model.Activitys;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Daniel on 2016/11/30.
 */

public class CalendarActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Activitys> datas;
    private LayoutInflater mLayoutInflater;
    private MyItemClickListener mItemClickListener;

    public CalendarActivityAdapter(Context mContext, List<Activitys> datas) {
        this.mContext = mContext;
        this.datas = datas;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.calendar_recyclerview_list, parent, false);
        RecyclerView.ViewHolder myViewHolder = new MyViewHolder(view,mItemClickListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i("Daniel","CalendarActivityAdapter---onBindViewHolder---position---"+position);
        MyViewHolder viewholder = (MyViewHolder) holder;
        Activitys _activity = datas.get(position);
        Picasso.with(mContext).load(_activity.getImage_url()).into(viewholder.activityPicImg);
        viewholder.activityTitleTv.setText(_activity.getActivity_title());
        viewholder.activityBegaintimeTv.setText("开始时间："+_activity.getBegin_time());
        viewholder.activityEndtimeTv.setText("结束时间:"+_activity.getEnd_time());
        viewholder.activitySumPriceTv.setText("总金额:"+(_activity.getAttended()*_activity.getPrice()));
        viewholder.activityAttendedTv.setText("已报名"+_activity.getAttended());
        viewholder.activityAddressTv.setText("地址："+_activity.getSite());
        viewholder.activityApplyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"11",Toast.LENGTH_SHORT).show();
            }
        });

    }
    /**
     * 设置Item点击监听
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView activityPicImg;
        private TextView activityTitleTv;
        private TextView activityBegaintimeTv;
        private TextView activityEndtimeTv;
        private TextView activityAddressTv;
        private TextView activitySumPriceTv;
        private TextView activityAttendedTv;
        private TextView activityApplyTv;
        private MyItemClickListener mListener;


        public MyViewHolder(View itemView,MyItemClickListener listener) {
            super(itemView);
            activityPicImg = (ImageView) itemView.findViewById(R.id.activity_pic_img);
            activityTitleTv = (TextView) itemView.findViewById(R.id.activity_title_tv);
            activityBegaintimeTv = (TextView) itemView.findViewById(R.id.activity_begaintime_tv);
            activityEndtimeTv = (TextView) itemView.findViewById(R.id.activity_endtime_tv);
            activityAddressTv = (TextView) itemView.findViewById(R.id.activity_address_tv);
            activitySumPriceTv = (TextView) itemView.findViewById(R.id.activity_sumPrice_tv);
            activityAttendedTv = (TextView) itemView.findViewById(R.id.activity_attended_tv);
            activityApplyTv = (TextView) itemView.findViewById(R.id.activity_apply_tv);
            this.mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mListener != null){
                mListener.onItemClick(view,getPosition());
            }
        }
    }


}
