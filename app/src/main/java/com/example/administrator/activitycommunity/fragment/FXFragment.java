package com.example.administrator.activitycommunity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.activitycommunity.R;
import com.example.administrator.activitycommunity.activity.XQActivity;
import com.example.administrator.activitycommunity.adapter.FX_Adapter;
import com.example.administrator.activitycommunity.model.Activitys;
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

import static com.example.administrator.activitycommunity.fragment.TJFragment.SIGN_ACTIVITY;

/**
 * Created by Administrator on 2016/11/10.
 */

public class FXFragment extends Fragment implements FX_Adapter.MyItemClickListener {
    @BindView(R.id.recyclerview_fragment_fx)
    RecyclerView recyclerviewFragmentFx;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private List<Activitys> mActivitys;


    private Unbinder binder;
    private TextView toolbar_title;
    private Subscription mSubscription;
    private CompositeSubscription compositeSubscription;
    private FX_Adapter fx_Adapter;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fx, container, false);
        binder=ButterKnife.bind(this, view);
        compositeSubscription = new CompositeSubscription();
        recyclerviewFragmentFx.setNestedScrollingEnabled(false);
        recyclerviewFragmentFx.setLayoutManager(new LinearLayoutManager(mContext));
        initView(view);
        getActivityDatas();


        return view;
    }

    private void initView(View view) {
        mContext = getActivity();
        appBarLayout = (AppBarLayout) view.findViewById(R.id.fx_toolBar);
        toolbar = (Toolbar) appBarLayout.findViewById(R.id.base_toolBar);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setToolbar();

    }

    private void setToolbar() {
        //让原始的toolbar的title不显示
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar_title.setText("发现");
    }
    /**
     * 获取活动数据
     */
    private void getActivityDatas() {
        mSubscription = NetWork.getApiService().getActivitys(SIGN_ACTIVITY, "2016-11-11")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Activitys>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Activitys> activityses) {
                        Log.d("Daniel", "activityses.size():::" + activityses.size());
                        if (mActivitys==null) {
                            mActivitys = new ArrayList<Activitys>();
                        }
                        mActivitys = activityses;
                        setAdapter(mContext, activityses);


                    }
                });
        compositeSubscription.add(mSubscription);
    }

    private void setAdapter(Context mContext, List<Activitys> activityses) {
        if (fx_Adapter == null) {
            fx_Adapter = new FX_Adapter(mContext, activityses);
        }
        recyclerviewFragmentFx.setAdapter(fx_Adapter);
        fx_Adapter.setOnItemClickListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binder.unbind();
        compositeSubscription.unsubscribe();


    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onItemClick(View view, int postion) {
        int _activityId = mActivitys.get(postion).getActivity_id();
        int _Max_num_people = mActivitys.get(postion).getMax_num_people();
        Log.i("Daniel", "TJFragment---onItemClick---_activityId---" + _activityId);
        Log.i("Daniel", "TJFragment---onItemClick---_Max_num_people---" + _Max_num_people);
        Intent intent = new Intent(mContext, XQActivity.class);
        intent.putExtra("activityId", _activityId);
        intent.putExtra("Max_num_people", _Max_num_people);
        startActivity(intent);

    }
}
