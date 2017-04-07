package com.example.administrator.activitycommunity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.activitycommunity.R;
import com.example.administrator.activitycommunity.model.PersonalActivitys;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Daniel on 2016/11/30.
 */

public class MY_FragmentAttentionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<PersonalActivitys> mDatas;
    private final LayoutInflater mLayoutInflater;
    private MyItemClickListener mItemClickListener;

    public MY_FragmentAttentionAdapter(Context mContext, List<PersonalActivitys> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        Log.i("Daniel","MY_FragmentAttentionAdapter---MY_FragmentAttentionAdapter---mDatas.size()---"+mDatas.size());
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.my_attention_recyclerview_list, parent, false);
        RecyclerView.ViewHolder viewHolder = new MyViewHolder(v, mItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHoder = (MyViewHolder) holder;
        PersonalActivitys personalActivitys = mDatas.get(position);
        Picasso.with(mContext).load(personalActivitys.getImage_url()).into(myViewHoder.activityPicImg);
        myViewHoder.activityTitleTv.setText(personalActivitys.getActivity_title());
        myViewHoder.activityBegaintimeTv.setText("开始时间:" + personalActivitys.getBegin_time());
        myViewHoder.activityEndtimeTv.setText("结束时间:"+personalActivitys.getEnd_time());
        myViewHoder.activityMoneyTv.setText("金额:" + personalActivitys.getPay_amount());

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.activity_pic_img)
        ImageView activityPicImg;
        @BindView(R.id.activity_title_tv)
        TextView activityTitleTv;
        @BindView(R.id.activity_begaintime_tv)
        TextView activityBegaintimeTv;
        @BindView(R.id.activity_endtime_tv)
        TextView activityEndtimeTv;
        @BindView(R.id.activity_money_tv)
        TextView activityMoneyTv;

        private MyItemClickListener mListener;

        public MyViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(view, getPosition());
            }

        }
    }

}
