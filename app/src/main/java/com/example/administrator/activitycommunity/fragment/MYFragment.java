package com.example.administrator.activitycommunity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.activitycommunity.R;
import com.example.administrator.activitycommunity.activity.SelectRoleActivity;
import com.example.administrator.activitycommunity.adapter.MY_FragmentPageAdapter;
import com.example.administrator.activitycommunity.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.example.administrator.activitycommunity.R.id.toolbar_title;

/**
 * Created by Administrator on 2016/11/10.
 */

public class MYFragment extends Fragment {
    @BindView(toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.base_toolBar)
    Toolbar baseToolBar;
    @BindView(R.id.user_image)
    CircleImageView userImage;
    @BindView(R.id.userName_tv)
    TextView userNameTv;
    @BindView(R.id.fragment_my_tablayout)
    TabLayout fragmentMyTablayout;
    @BindView(R.id.fragment_my_viewpager)
    ViewPager fragmentMyViewpager;
    private Realm realm;
    private RealmResults<User> mUser;
    private Unbinder mUnbinder;
    private MYFragment_attend mMYFragment_attend;
    private MYFragment_attention mMYFragment_attention;
    private MY_FragmentPageAdapter my_fragmentPageAdapter;
    private List<Fragment> fragments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        mUnbinder=ButterKnife.bind(this, view);
        initView();
        realm = Realm.getDefaultInstance();
        mUser = realm.where(User.class).findAll();
        Log.i("Daniel","MYFragment---onCreateView-----");
        setFragmentPageAdapter();
//        if (!mUser.get(0).getNickname().equals("")){
//
//            userNameTv.setText( mUser.get(0).getNickname());
//        }

        return view;
    }

    private void setFragmentPageAdapter() {
        // TODO: 2016/12/6  
        Log.i("Daniel","MYFragment---setFragmentPageAdapter------");
            mMYFragment_attend = new MYFragment_attend();
            mMYFragment_attention = new MYFragment_attention();
            fragments = new ArrayList<>();
            fragments.add(mMYFragment_attend);
            fragments.add(mMYFragment_attention);
        if (my_fragmentPageAdapter==null) {
            Log.i("Daniel","MYFragment---onCreateView---fragments.size()---"+fragments.size());
            my_fragmentPageAdapter = new MY_FragmentPageAdapter(getActivity().getSupportFragmentManager(),fragments);
        }
        fragmentMyViewpager.setAdapter(my_fragmentPageAdapter);
        fragmentMyTablayout.setupWithViewPager(fragmentMyViewpager);
    }

    private void initView() {
        setToolbar();
    }
    private void setToolbar() {
        //让原始的toolbar的title不显示
        baseToolBar.setTitle("");
//        baseToolBar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        ((AppCompatActivity) getActivity()).setSupportActionBar(baseToolBar);
//        baseToolBar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().onBackPressed();
//
//            }
//        });
        toolbarTitle.setText("个人中心");
    }





    @OnClick({R.id.user_image, R.id.userName_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_image:
//                startActivity(new Intent(getActivity(), SelectRoleActivity.class));
                break;
            case R.id.userName_tv:
                startActivity(new Intent(getActivity(), SelectRoleActivity.class));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Daniel","MYFragment---onResume---");

    }
}
