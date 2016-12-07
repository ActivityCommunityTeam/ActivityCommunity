package com.example.administrator.activitycommunity.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.example.administrator.activitycommunity.adapter.MY_FragmentAttendAdapter;
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

/**
 * Created by Administrator on 2016/11/10.
 */

public class MYFragment_attend extends Fragment implements MY_FragmentAttendAdapter.MyItemClickListener {

    @BindView(R.id.recyclerview_fragment_attend)
    RecyclerView recyclerviewFragmentAttend;
    private Unbinder mUnbinder;
    private MY_FragmentAttendAdapter myFragmentAttendAdapter;
    private Subscription mSubscription;
    private CompositeSubscription compositeSubscription;
    private List<PersonalActivitys> mDatas;
    private Context mContext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_attend, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        compositeSubscription = new CompositeSubscription();
        //解决滑动冲突
        recyclerviewFragmentAttend.setNestedScrollingEnabled(false);
        //设置recyclerview
        recyclerviewFragmentAttend.setLayoutManager(new LinearLayoutManager(mContext));
        Log.i("Daniel","MYFragment_attend---onCreateView------");


        return view;
    }

    private void initData() {
        Log.i("Daniel","MYFragment_attend---initData---fragments.size()---");
        mSubscription= NetWork.getApiService().getPersonalActivitys(01,7)
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
                        mDatas=  personalActivityses;
                        setAdapter();

                    }
                });
        compositeSubscription.add(mSubscription);
    }

    private void setAdapter() {
        myFragmentAttendAdapter = new MY_FragmentAttendAdapter(mContext,mDatas);
        recyclerviewFragmentAttend.setAdapter(myFragmentAttendAdapter);
        myFragmentAttendAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Daniel","MYFragment_attend---initData---fragments.size()---");
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

    @Override
    public void onResume() {
        super.onResume();
        initData();
        Log.i("Daniel","MYFragment_attend---onResume---");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Daniel","MYFragment_attend---onStart---");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Daniel","MYFragment_attend---onPause---");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Daniel","MYFragment_attend---onStop---");
    }
}
