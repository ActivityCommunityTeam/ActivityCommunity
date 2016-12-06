package com.example.administrator.activitycommunity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.activitycommunity.R;
import com.example.administrator.activitycommunity.adapter.CalendarActivityAdapter;
import com.example.administrator.activitycommunity.model.Activitys;
import com.example.administrator.activitycommunity.net.NetWork;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class CalenderActivity extends AppCompatActivity implements CalendarActivityAdapter.MyItemClickListener {


    @BindView(R.id.recyclerview_calendarActivity)
    RecyclerView recyclerviewCalendarActivity;
    @BindView(R.id.calendarViewTop)
    MaterialCalendarView calendarViewTop;
    @BindView(R.id.calendarViewDown)
    MaterialCalendarView calendarViewDown;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.calendar_img)
    ImageView calendarImg;
    @BindView(R.id.calendar_linearlayout)
    LinearLayout calendarLinearlayout;

    private Unbinder mUnbinder;
    private CalendarActivityAdapter mCalendarActivityAdapter;
    private CompositeSubscription compositeSubscription;
    private Subscription mSubscription;
    private List<Activitys> mActivitys;
    private Context mContext;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private TextView toolbar_title;
    private boolean isClick = false;


    public static String SIGN_ACTIVITY = "01";
    public static String SIG_RILI = "02";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        mUnbinder = ButterKnife.bind(this);
        compositeSubscription = new CompositeSubscription();
        initView();
        initDate();


    }

    private void initDate() {
        mSubscription = NetWork.getApiService().getActivitys(SIGN_ACTIVITY, "2016-11-11")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Activitys>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Daniel", "CalenderActivity---onError---请求失败！---");

                    }
                    @Override
                    public void onNext(List<Activitys> activityses) {
                        if (mActivitys == null) {
                            mActivitys = new ArrayList<Activitys>();
                        }
                        mActivitys = activityses;
                        Log.i("Daniel", "CalenderActivity---onNext---mActivitys.size()---" + mActivitys.size());
                        setAdapter(mContext, activityses);

                    }
                });
        compositeSubscription.add(mSubscription);
    }

    private void initView() {
        mContext = CalenderActivity.this;
        recyclerviewCalendarActivity.setNestedScrollingEnabled(false);
        recyclerviewCalendarActivity.setLayoutManager(new LinearLayoutManager(mContext));
        setToolbar();
    }

    private void setToolbar() {
        appBarLayout = (AppBarLayout) findViewById(R.id.calenderActivity_toolBar);
        toolbar = (Toolbar) appBarLayout.findViewById(R.id.base_toolBar);
        toolbar_title = (TextView) appBarLayout.findViewById(R.id.toolbar_title);
        //让原始的toolbar的title不显示
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar_title.setText("日历");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setAdapter(Context mContext, List<Activitys> activityses) {
        if (mCalendarActivityAdapter == null) {
            mCalendarActivityAdapter = new CalendarActivityAdapter(mContext, activityses);
        }
        recyclerviewCalendarActivity.setAdapter(mCalendarActivityAdapter);
        mCalendarActivityAdapter.setOnItemClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        compositeSubscription.unsubscribe();
    }

    @Override
    public void onItemClick(View view, int postion) {
//        Toast.makeText(mContext, "点击"+postion, Toast.LENGTH_SHORT).show();
        int _activityId = mActivitys.get(postion).getActivity_id();
        int _Max_num_people = mActivitys.get(postion).getMax_num_people();
        Log.i("Daniel", "TJFragment---onItemClick---_activityId---" + _activityId);
        Log.i("Daniel", "TJFragment---onItemClick---_Max_num_people---" + _Max_num_people);
        Intent intent = new Intent(mContext, XQActivity.class);
        intent.putExtra("activityId", _activityId);
        intent.putExtra("Max_num_people", _Max_num_people);
        startActivity(intent);


    }

    @OnClick({R.id.calendar_img,R.id.calendar_linearlayout})
    public void onClick() {
        if (isClick) {
            calendarViewTop.setVisibility(View.GONE);
            calendarViewDown.setVisibility(View.VISIBLE);
            calendarImg.setImageDrawable(getResources().getDrawable(R.drawable.calendar_down));
            isClick = false;
        } else {
            calendarViewTop.setVisibility(View.VISIBLE);
            calendarViewDown.setVisibility(View.GONE);
            calendarImg.setImageDrawable(getResources().getDrawable(R.drawable.calendar_top));
            isClick = true;
        }
    }
}
