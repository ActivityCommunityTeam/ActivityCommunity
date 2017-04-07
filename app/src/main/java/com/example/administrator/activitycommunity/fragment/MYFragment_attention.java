package com.example.administrator.activitycommunity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.activitycommunity.R;
import com.example.administrator.activitycommunity.activity.XQ_ApplyActivity;
import com.example.administrator.activitycommunity.adapter.MY_FragmentAttentionAdapter;
import com.example.administrator.activitycommunity.model.PersonalActivitys;
import com.example.administrator.activitycommunity.net.NetWork;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.content.Context.MODE_WORLD_READABLE;

/**
 * Created by Administrator on 2016/11/10.
 */

public class MYFragment_attention extends Fragment implements MY_FragmentAttentionAdapter.MyItemClickListener{


    @BindView(R.id.recyclerview_fragment_attention)
    RecyclerView recyclerviewFragmentAttention;
    private Unbinder mUnbinder;
    private MY_FragmentAttentionAdapter myFragmentAttentionAdapter;
    private Subscription mSubscription;
    private CompositeSubscription compositeSubscription;
    private List<PersonalActivitys> mDatas;
    private Context mContext;
    private static int ATTENTION=02;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_attention, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        compositeSubscription = new CompositeSubscription();
        //解决滑动冲突
        recyclerviewFragmentAttention.setNestedScrollingEnabled(false);
        //设置recyclerview
        recyclerviewFragmentAttention.setLayoutManager(new LinearLayoutManager(mContext));
        initDate();



        return view;
    }

    private void initDate() {
        SharedPreferences read = getActivity().getSharedPreferences("user", MODE_WORLD_READABLE);
        int _userId = read.getInt("userId",-1);
        Log.i("Daniel","MYFragment_attend---initData---userId---"+_userId);
        mSubscription = NetWork.getApiService().getPersonalActivitys(ATTENTION, _userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PersonalActivitys>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<PersonalActivitys> personalActivityses) {
                        mDatas = new ArrayList<PersonalActivitys>();
                        mDatas = personalActivityses;
                        setAdapter();

                    }
                });
        compositeSubscription.add(mSubscription);
    }

    private void setAdapter() {
        myFragmentAttentionAdapter = new MY_FragmentAttentionAdapter(mContext,mDatas);
        recyclerviewFragmentAttention.setAdapter(myFragmentAttentionAdapter);
        myFragmentAttentionAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        compositeSubscription.unsubscribe();
    }

    @Override
    public void onItemClick(View view, int postion) {
        PersonalActivitys _personalActicity = mDatas.get(postion);

        Intent intent = new Intent(mContext, XQ_ApplyActivity.class);
        Bundle _bundle = new Bundle();
        _bundle.putSerializable("_personalActicity",_personalActicity);
        intent.putExtras(_bundle);
        startActivity(intent);

    }
}
