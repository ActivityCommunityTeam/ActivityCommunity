package com.example.administrator.activitycommunity.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.activitycommunity.R;
import com.example.administrator.activitycommunity.fragment.ApplyDialogFragment;
import com.example.administrator.activitycommunity.model.Activitys;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/11/10.
 */

public class TJ_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Activitys> mDatas;
    private FragmentManager fragmentManager;
    private final LayoutInflater mLayoutInflater;
    private MyItemClickListener mItemClickListener;

    public TJ_Adapter(Context mContext, List<Activitys> mDatas,FragmentManager fragmentManager) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.fragmentManager=fragmentManager;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.tj_recyclerview_list, parent, false);
        RecyclerView.ViewHolder viewHolder = new MyViewHoder(v, mItemClickListener);

        return viewHolder;
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHoder myViewHoder = (MyViewHoder) holder;
        final Activitys _activity = mDatas.get(position);
        String _imgUrl = _activity.getImage_url();
        Picasso.with(mContext).load(_imgUrl).into(myViewHoder.activity_pic_img);
        myViewHoder.activity_address_tv.setText("地点:" + _activity.getSite());
        myViewHoder.activity_attended_tv.setText("已报名:" + _activity.getAttended());
        myViewHoder.activity_sponsor_tv.setText(_activity.getSponsor_name());
        myViewHoder.activity_price_tv.setText("￥" + _activity.getPrice());
        int _sumPrice = _activity.getPrice() * _activity.getAttended();
        myViewHoder.activity_sumPrice_tv.setText("总金额:" + _sumPrice);
        myViewHoder.activity_time_tv.setText("时间:" + _activity.getBegin_time());
        myViewHoder.activity_title_tv.setText(_activity.getActivity_title());

        myViewHoder.activity_price_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApplyDialogFragment applyDialogFragment = new ApplyDialogFragment();
                applyDialogFragment.setmPrice(_activity.getPrice());
                applyDialogFragment.setmTime(_activity.getBegin_time());
                applyDialogFragment.setmTitle(_activity.getActivity_title());
                applyDialogFragment.show(fragmentManager, "applyDialogFragment");
//

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }

    static class MyViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView activity_pic_img;
        private TextView activity_title_tv;
        private TextView activity_time_tv;
        private TextView activity_address_tv;
        private TextView activity_sponsor_tv;
        private TextView activity_sumPrice_tv;
        private TextView activity_attended_tv;
        private TextView activity_price_tv;
        private MyItemClickListener mListener;

        public MyViewHoder(View view, MyItemClickListener listener) {
            super(view);
            activity_pic_img = (ImageView) view.findViewById(R.id.activity_pic_img);
            activity_title_tv = (TextView) view.findViewById(R.id.activity_title_tv);
            activity_time_tv = (TextView) view.findViewById(R.id.activity_time_tv);
            activity_address_tv = (TextView) view.findViewById(R.id.activity_address_tv);
            activity_sponsor_tv = (TextView) view.findViewById(R.id.activity_sponsor_tv);
            activity_sumPrice_tv = (TextView) view.findViewById(R.id.activity_sumPrice_tv);
            activity_attended_tv = (TextView) view.findViewById(R.id.activity_attended_tv);
            activity_price_tv = (TextView) view.findViewById(R.id.activity_price_tv);
            this.mListener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(view, getPosition());
            }

        }
    }
}
