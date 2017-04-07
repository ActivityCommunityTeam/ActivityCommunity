package com.example.administrator.activitycommunity.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.utils.PhoneUtils;
import com.example.administrator.activitycommunity.R;
import com.example.administrator.activitycommunity.model.ResultData;
import com.example.administrator.activitycommunity.model.StartPage;
import com.example.administrator.activitycommunity.model.User;
import com.example.administrator.activitycommunity.net.NetWork;
import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.administrator.activitycommunity.net.NetWork.getApiService;

public class WelcomeActivity extends AppCompatActivity {
    @BindView(R.id.startPage_img)
    ImageView startPageImg;
    @BindView(R.id.activity_time_tv)
    TextView activityTimeTv;
    private CompositeSubscription mCompositeSubscription;
    private static final int TIMETOCOUNT = 3;
    private static Unbinder mUnbinder;
    //    private MyCountDownTimer mc;
    private String IMEI;
    private Realm realm;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        mUnbinder = ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        mCompositeSubscription = new CompositeSubscription();
        //状态栏和activity之间的距离
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getWelcomePage();

        requestPhone();


    }

    /**
     * 获取权限和手机设备号
     */
    private void requestPhone() {
        RxPermissions.getInstance(this)
                .request(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @DebugLog
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            IMEI = PhoneUtils.getIMEI(getApplicationContext());
                            login();
                        } else {
                            requestPermissionInfo();
                        }
                    }
                });

    }

    private void requestPermissionInfo() {
        new MaterialDialog.Builder(this)
                .title("请给予相关权限")
                .content("谢谢")
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        WelcomeActivity.this.finish();

                    }
                }).show();
    }

    /**
     * 获取登录信息
     */
    @DebugLog
    private void login() {
//        if (StringUtils.isEmpty(IMEI)) {
//            requestPhone();
//            return;
//        }
        Log.i("Daniel", "IMEI:::" + IMEI);
        Subscription subscriptionLogin = NetWork.getApiService().getLogin(IMEI)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultData<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @DebugLog
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Daniel", "登陆失败！" + e);
                        Toast.makeText(WelcomeActivity.this, "登陆失败！", Toast.LENGTH_SHORT).show();
                        countToEnter();

                    }

                    @DebugLog
                    @Override
                    public void onNext(ResultData<User> userResultData) {
//
                        realm.beginTransaction();
                        User _realmUser=realm.copyToRealmOrUpdate(userResultData.getUser());
                        realm.commitTransaction();
                        SharedPreferences read =getSharedPreferences("user", MODE_WORLD_READABLE);
                        int _userId = read.getInt("userId",-1);
                        if (_userId==-1){
                            Log.i("Daniel","WelcomeActivity---onNext---realmUser.getUser_id()---"+_realmUser.getUser_id());
                            Log.i("Daniel","WelcomeActivity---onNext---realmUser.getUser_id()---"+_realmUser.getUser_no());
                            SharedPreferences.Editor editor = getSharedPreferences("user", MODE_WORLD_WRITEABLE).edit();
                            editor.putInt("userId",_realmUser.getUser_id());
                            editor.putString("userNo",_realmUser.getUser_no());
                            editor.commit();
                        }
//                        Toast.makeText(WelcomeActivity.this, "userId"+_realmUser.getUser_id(), Toast.LENGTH_SHORT).show();
                        countToEnter();

                    }
                });
        mCompositeSubscription.add(subscriptionLogin);


    }

    /**
     * 倒计时
     */
    @DebugLog
    private void countToEnter() {
        Subscription subscriptionCount = Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .limit(TIMETOCOUNT)
                .map(new Func1<Long, Long>() {
                    @DebugLog
                    @Override
                    public Long call(Long aLong) {
                        return TIMETOCOUNT - aLong;
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        startActivity(new Intent(WelcomeActivity.this, SelectRoleActivity.class));
                        WelcomeActivity.this.finish();
                    }

                    @DebugLog
                    @Override
                    public void onError(Throwable e) {

                    }

                    @DebugLog
                    @Override
                    public void onNext(Long aLong) {
                        activityTimeTv.setText(aLong + "秒");
                    }
                });
        mCompositeSubscription.add(subscriptionCount);

    }

//    private void startTime() {
//        mc = new MyCountDownTimer(3000, 1000);
//        mc.start();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
//                startActivity(intent);
//                WelcomeActivity.this.finish();
//
//            }
//        }, 3000);
//    }

    /**
     * 加载启动页
     */
    private void getWelcomePage() {
        Subscription subscriptionWelcomePage = getApiService().getAdMsgs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<StartPage>>() {
                    @Override
                    public void onCompleted() {
                        Log.i("Daniel", "获取启动页完成！");
                    }

                    @DebugLog
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Daniel", "获取启动页失败！" + e);
                        Picasso.with(WelcomeActivity.this).load(String.valueOf(getResources().getDrawable(R.drawable.activitydetail_address))).into(startPageImg);

                    }

                    @DebugLog
                    @Override
                    public void onNext(List<StartPage> startPages) {
                        String url = startPages.get(0).getAd_url();
                        Picasso.with(WelcomeActivity.this).load(url).into(startPageImg);

                    }
                });
        mCompositeSubscription.add(subscriptionWelcomePage);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        mUnbinder.unbind();
        mCompositeSubscription.unsubscribe();

    }

//
//    class MyCountDownTimer extends CountDownTimer {
//        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
//            super(millisInFuture, countDownInterval);
//        }
//
//        @Override
//        public void onTick(long l) {
//            activityTimeTv.setText("(" + l / 1000 + ")秒");
//
//        }
//
//
//        @Override
//        public void onFinish() {
////            activityTimeTv.setText("正在跳转");
//        }
//    }
}
