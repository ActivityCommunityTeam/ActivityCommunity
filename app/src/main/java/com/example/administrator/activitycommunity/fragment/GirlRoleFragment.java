package com.example.administrator.activitycommunity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.activitycommunity.R;
import com.example.administrator.activitycommunity.activity.MainActivity;
import com.example.administrator.activitycommunity.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by Administrator on 2016/11/10.
 */

public class GirlRoleFragment extends Fragment {
    @BindView(R.id.selectsex_nick_et)
    EditText selectsexNickEt;
    @BindView(R.id.selectsex_phone_et)
    EditText selectsexPhoneEt;
    @BindView(R.id.confirm_btn)
    Button confirmBtn;
    @BindView(R.id.selectsex_index_community)
    ImageView selectsexIndexCommunity;
    @BindView(R.id.firstRole_img)
    ImageView firstRoleImg;
    @BindView(R.id.goMy)
    Button goMy;
    @BindView(R.id.no_select_girl1_tv)
    ImageView noSelectGirl1Tv;
    @BindView(R.id.no_select_girl2_tv)
    ImageView noSelectGirl2Tv;
    @BindView(R.id.no_select_girl3_tv)
    ImageView noSelectGirl3Tv;
    @BindView(R.id.no_select_girl4_tv)
    ImageView noSelectGirl4Tv;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private TextView toolbar_title;
    private Realm realm;
    private int selectRole_flag=-1;//2:男，3：女


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, container, false);
        realm = Realm.getDefaultInstance();
        ButterKnife.bind(this, view);
        initView(view);
        selectRole_flag = SelectSexFragment.CONTENT_SELECT;
        Log.i("Daniel","GirlRoleFragment---onCreateView---selectRole_flag---"+selectRole_flag);
        if(selectRole_flag!=-1){

            if (selectRole_flag==2){
                setBoyImage();

            }else if (selectRole_flag==3){
                setGirlImage();

            }
        }



        return view;
    }

    private void setGirlImage() {
        firstRoleImg.setImageResource(R.drawable.girl_01);
        noSelectGirl1Tv.setImageResource(R.drawable.nv_01);
        noSelectGirl2Tv.setImageResource(R.drawable.nv_02);
        noSelectGirl3Tv.setImageResource(R.drawable.nv_03);
        noSelectGirl4Tv.setImageResource(R.drawable.nv_04);
    }

    private void setBoyImage() {
        firstRoleImg.setImageResource(R.drawable.boy_01);
        noSelectGirl1Tv.setImageResource(R.drawable.nan_01);
        noSelectGirl2Tv.setImageResource(R.drawable.nan_02);
        noSelectGirl3Tv.setImageResource(R.drawable.nan_03);
        noSelectGirl4Tv.setImageResource(R.drawable.nan_04);
    }

    private void initView(View view) {
        appBarLayout = (AppBarLayout) view.findViewById(R.id.selectRole_girl_toolbar);
        toolbar = (Toolbar) appBarLayout.findViewById(R.id.base_toolBar);
        toolbar_title = (TextView) appBarLayout.findViewById(R.id.toolbar_title);
        setToolbar();
    }

    private void setToolbar() {
        //让原始的toolbar的title不显示
        toolbar.setTitle("");
//        baseToolBar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        baseToolBar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().onBackPressed();
//
//            }
//        });
        toolbar_title.setText("个人中心");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.confirm_btn, R.id.selectsex_index_community,R.id.firstRole_img, R.id.goMy, R.id.no_select_girl1_tv, R.id.no_select_girl2_tv, R.id.no_select_girl3_tv, R.id.no_select_girl4_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_btn:
                realm.beginTransaction();
                User user = new User();
                user.setNickname(selectsexNickEt.getText().toString());
                user.setPhone_num(selectsexPhoneEt.getText().toString());
                User realmUser = realm.copyToRealmOrUpdate(user);
                realm.commitTransaction();
                Toast.makeText(getActivity(), "信息提交成功！", Toast.LENGTH_SHORT).show();

                break;
            case R.id.selectsex_index_community:

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.firstRole_img:
                break;
            case R.id.goMy:
                break;
            case R.id.no_select_girl1_tv:
                if (selectRole_flag==2){
                    firstRoleImg.setImageResource(R.drawable.boy_01);
                }else {
                    firstRoleImg.setImageResource(R.drawable.girl_01);
                }

                break;
            case R.id.no_select_girl2_tv:
                if (selectRole_flag==2){
                    firstRoleImg.setImageResource(R.drawable.boy_02);
                }else {
                    firstRoleImg.setImageResource(R.drawable.girl_02);
                }

                break;
            case R.id.no_select_girl3_tv:
                if (selectRole_flag==2){
                    firstRoleImg.setImageResource(R.drawable.boy_03);
                }else {
                    firstRoleImg.setImageResource(R.drawable.girl_03);
                }

                break;
            case R.id.no_select_girl4_tv:
                if (selectRole_flag==2){
                    firstRoleImg.setImageResource(R.drawable.boy_04);
                }else {
                    firstRoleImg.setImageResource(R.drawable.girl_04);
                }

                break;
        }
    }


}
