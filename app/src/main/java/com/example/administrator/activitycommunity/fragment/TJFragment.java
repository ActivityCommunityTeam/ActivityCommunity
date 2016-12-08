package com.example.administrator.activitycommunity.fragment;

import android.app.FragmentManager;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.StringUtils;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.administrator.activitycommunity.R;
import com.example.administrator.activitycommunity.activity.CalenderActivity;
import com.example.administrator.activitycommunity.activity.XQActivity;
import com.example.administrator.activitycommunity.adapter.TJ_Adapter;
import com.example.administrator.activitycommunity.model.Activitys;
import com.example.administrator.activitycommunity.model.Carousel;
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
 * Created by Administrator on 2016/11/9.
 */

public class TJFragment extends Fragment implements BaseSliderView.OnSliderClickListener,TJ_Adapter.MyItemClickListener,View.OnClickListener {

    @BindView(R.id.recyclerview_fragment_tj)
    RecyclerView recyclerviewFragmentTj;
//    private Toolbar mainActivity_toolBar;
    private LinearLayout mLinearLayout;

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private Unbinder binder;
    private TextView toolbar_title;
    private TextView hd_tv;
    private TextView rl_tv;
    private TextView fj_tv;
    private SliderLayout mSliderLayout;
    private Subscription mSubscription;
    private CompositeSubscription compositeSubscription;
    private TJ_Adapter tj_Adapter;
    private Context mContext;
    private List<Activitys> mActivityses;

    public static String SIGN_ACTIVITY = "01";
    public static String SIG_RILI = "02";

    /**
     * 使用现在的menu
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tj, container, false);
        compositeSubscription = new CompositeSubscription();
        binder = ButterKnife.bind(this, view);
        //解决滑动冲突
        recyclerviewFragmentTj.setNestedScrollingEnabled(false);
        //设置recyclerview
        recyclerviewFragmentTj.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.i("Daniel", "--------------> initView(view)");
        initView(view);
        Log.i("Daniel", "--------------> getCarousel();");
        getCarousel();
        Log.i("Daniel", "-------------->  getActivityDatas();");
        getActivityDatas();
        rl_tv.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_tj, menu);

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
//                        Log.d("Daniel", "activityses.size():::" + activityses.size());
                        if (mActivityses==null) {
                            mActivityses = new ArrayList<Activitys>();
                        }
                        mActivityses=activityses;
                        setAdapter(mContext, activityses);

                    }
                });
        compositeSubscription.add(mSubscription);
    }

    private void setAdapter(Context mContext, List<Activitys> activityses) {
        if (tj_Adapter == null) {
            FragmentManager f=getActivity().getFragmentManager();
            tj_Adapter = new TJ_Adapter(mContext, activityses,f);
        }
        recyclerviewFragmentTj.setAdapter(tj_Adapter);
        tj_Adapter.setOnItemClickListener(this);

    }

    private void setViewPager(List<Carousel> carousels) {
        if (carousels.size() > 1) {
            mSliderLayout.startAutoCycle();
        }
        for (Carousel m : carousels) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            if (!StringUtils.isEmpty(m.getImage_url()))
                textSliderView.description(m.getActivity_title()).image(m.getImage_url()).setScaleType(BaseSliderView.ScaleType.CenterCrop).setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("url", m.getImage_url() == null ? "no" : m.getImage_url());
            textSliderView.getBundle().putString("name", m.getActivity_title());
            mSliderLayout.addSlider(textSliderView);
        }
    }

    private void initView(View view) {
        mContext = getActivity();
//        mainActivity_toolBar = (Toolbar) view.findViewById(R.id.mainActivity_toolBar);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.hdrlfj);
        hd_tv = (TextView) mLinearLayout.findViewById(R.id.activitys_hdrlfj);
        rl_tv = (TextView) mLinearLayout.findViewById(R.id.calendar_hdrlfj);
        fj_tv = (TextView) mLinearLayout.findViewById(R.id.nearby_hdrlfj);
        appBarLayout = (AppBarLayout) view.findViewById(R.id.mainActivity_toolBar);
        toolbar = (Toolbar) appBarLayout.findViewById(R.id.base_toolBar);
        toolbar_title = (TextView) appBarLayout.findViewById(R.id.toolbar_title);
        mSliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        setToolbar();
    }

    private void setToolbar() {
        //让原始的toolbar的title不显示
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar_title.setText("活动社区");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binder.unbind();
        compositeSubscription.unsubscribe();


    }

    @Override
    public void onStop() {
        mSliderLayout.stopAutoCycle();
        super.onStop();

    }


    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    /**
     * 获取轮播图片
     */
    public void getCarousel() {
        mSubscription = NetWork.getApiService().getCarousel().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Carousel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Carousel> carousels) {
                Log.i("Daniel", "" + carousels.size());
                setViewPager(carousels);

            }
        });
        compositeSubscription.add(mSubscription);
    }

    @Override
    public void onItemClick(View view, int postion) {
        int _activityId = mActivityses.get(postion).getActivity_id();
        int _Max_num_people=mActivityses.get(postion).getMax_num_people();
        Log.i("Daniel","TJFragment---onItemClick---_activityId---"+_activityId);
        Log.i("Daniel","TJFragment---onItemClick---_Max_num_people---"+_Max_num_people);

        Intent intent = new Intent(mContext, XQActivity.class);
        intent.putExtra("activityId",_activityId);
        intent.putExtra("Max_num_people",_Max_num_people);
        startActivity(intent);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activitys_hdrlfj:break;
            case R.id.calendar_hdrlfj:
//                Toast.makeText(mContext, "日历", Toast.LENGTH_SHORT).show();
                Intent _intent = new Intent(getActivity(), CalenderActivity.class);
                startActivity(_intent);
                break;
            case R.id.nearby_hdrlfj:break;
        }
    }
}
