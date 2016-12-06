package com.example.administrator.activitycommunity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.activitycommunity.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/11/10.
 */

public class SelectSexFragment extends Fragment {

    @BindView(R.id.no_select_girl_tv)
    RadioButton noSelectGirlTv;
    @BindView(R.id.no_select_boy_tv)
    RadioButton noSelectBoyTv;
    @BindView(R.id.selectSex_rGroup)
    RadioGroup selectSexRGroup;
    @BindView(R.id.confirm_btn)
    Button confirmBtn;
    private Unbinder mUnbinder;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private TextView toolbar_title;
    private BoyRoleFragment mBoyRoleFragment;
    private GirlRoleFragment mGirlRoleFragment;
    private static int CONTENT_SELECT = -1;
    private static final int CONTENT_BOY = 2;
    private static final int CONTENT_GRIL = 3;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sex, container, false);
        mUnbinder=ButterKnife.bind(this, view);
        initView(view);
        selectSexRGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (noSelectGirlTv.getId()==i){
                    CONTENT_SELECT=3;
                }else if(noSelectBoyTv.getId()==i){
                    CONTENT_SELECT=2;
                }
            }
        });
        return view;
    }

    private void initView(View view) {
        appBarLayout = (AppBarLayout) view.findViewById(R.id.selectRole_toolbar);
        toolbar = (Toolbar) appBarLayout.findViewById(R.id.base_toolBar);
        toolbar_title = (TextView) appBarLayout.findViewById(R.id.toolbar_title);
        setToolbar();
    }

    private void setToolbar() {
        //让原始的toolbar的title不显示
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar_title.setText("人物选择");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick(R.id.confirm_btn)
    public void onClick(View v) {
        setContent(CONTENT_SELECT);

    }

    private void setContent(int contentSelect) {
        switch (contentSelect) {

            case CONTENT_BOY:

                if (mBoyRoleFragment == null) {
                    mBoyRoleFragment = new BoyRoleFragment();

                }
                fragmentCommit(mBoyRoleFragment);
                break;
            case CONTENT_GRIL:

                if (mGirlRoleFragment == null) {
                    mGirlRoleFragment = new GirlRoleFragment();

                }
                fragmentCommit(mGirlRoleFragment);
                break;
        }
    }

    private void fragmentCommit(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.selectRole_frameLayout, fragment);
        fragmentTransaction.commitNow();
    }
}
