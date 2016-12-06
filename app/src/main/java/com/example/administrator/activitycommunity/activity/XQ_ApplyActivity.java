package com.example.administrator.activitycommunity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.administrator.activitycommunity.R;
import com.example.administrator.activitycommunity.model.ActivityDetail;
import com.example.administrator.activitycommunity.model.PersonalActivitys;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class XQ_ApplyActivity extends AppCompatActivity {
    @BindView(R.id.tittle_tv)
    TextView tittleTv;
    @BindView(R.id.enrol_time_tv)
    TextView enrolTimeTv;
    @BindView(R.id.number_tv)
    TextView numberTv;
    @BindView(R.id.money_tv)
    TextView moneyTv;
    @BindView(R.id.status_tv)
    TextView statusTv;
    @BindView(R.id.activity_time_tv)
    TextView activityTimeTv;
    @BindView(R.id.site_tv)
    TextView siteTv;
    @BindView(R.id.webView)
    WebView webView;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private TextView toolbar_title;
    private Subscription mSubscription;
    private ActivityDetail mActivityDetail;
    private CompositeSubscription compositeSubscription;
    private static String URL="http://211.149.235.17:8080/hdsq/app/getDetailContent/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xq_apply);
        ButterKnife.bind(this);
        compositeSubscription = new CompositeSubscription();
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        PersonalActivitys _personalActicity= (PersonalActivitys) intent.getSerializableExtra("_personalActicity");
        setData(_personalActicity);
        int _activityId = intent.getIntExtra("activityId", -1);
//        Log.i("Daniel", "XQActivity---initData---_activityId---" + _activityId);
//        Log.i("Daniel", "XQActivity---initData---mMax_num_people---" + mMax_num_people);
//        if (_activityId != -1) {
//            mSubscription = NetWork.getApiService().getActivityDetail(_activityId)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<ActivityDetail>() {
//                        @Override
//                        public void onCompleted() {
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Log.i("Daniel", "XQActivity---onError---获取详情失败！---");
//                        }
//
//                        @Override
//                        public void onNext(ActivityDetail activityDetail) {
//                            mActivityDetail = activityDetail;
//                            Log.i("Daniel", "XQActivity---onError---mActivityDetail---"+mActivityDetail.getActivity_title());
//                            setData();
//                        }
//                    });
//            compositeSubscription.add(mSubscription);
//
//        }
    }

    private void setData(PersonalActivitys personalActicity) {
        tittleTv.setText(personalActicity.getActivity_title());
        enrolTimeTv.setText("报名时间："+personalActicity.getEnrol_time());
        moneyTv.setText("支付金额："+personalActicity.getPay_amount());
        activityTimeTv.setText("活动时间："+personalActicity.getBegin_time()+"-"+personalActicity.getEnd_time());
        siteTv.setText("详细地址："+personalActicity.getSite());
        statusTv.setText("支付状态："+personalActicity.getPay_status());
        numberTv.setText("报名编号："+personalActicity.getOrder_no());
        webView.loadUrl(URL+personalActicity.getActivity_id());


    }

    private void initView() {
        appBarLayout = (AppBarLayout) findViewById(R.id.activity_xq_apply_toolBar);
        toolbar = (Toolbar) appBarLayout.findViewById(R.id.base_toolBar);
        toolbar_title = (TextView) appBarLayout.findViewById(R.id.toolbar_title);
        setToolBar();

    }

    private void setToolBar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar_title.setText("活动详情");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
