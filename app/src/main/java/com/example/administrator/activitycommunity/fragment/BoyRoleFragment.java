package com.example.administrator.activitycommunity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class BoyRoleFragment extends Fragment {
    @BindView(R.id.selectsex_nick_et)
    EditText selectsexNickEt;
    @BindView(R.id.selectsex_phone_et)
    EditText selectsexPhoneEt;
    @BindView(R.id.confirm_btn)
    Button confirmBtn;
    @BindView(R.id.selectsex_index_community)
    ImageView selectsexIndexCommunity;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private TextView toolbar_title;
    private Realm realm;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boy, container, false);
        realm = Realm.getDefaultInstance();
        initView(view);


        ButterKnife.bind(this, view);
        return view;
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

    @OnClick({R.id.confirm_btn, R.id.selectsex_index_community})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_btn:
                realm.beginTransaction();
                User user = new User();
                user.setNickname(selectsexNickEt.getText().toString());
                user.setPhone_num(selectsexPhoneEt.getText().toString());
                User realmUser=realm.copyToRealmOrUpdate(user);
                realm.commitTransaction();
                Toast.makeText(getActivity(), "信息提交成功！", Toast.LENGTH_SHORT).show();


                break;
            case R.id.selectsex_index_community:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
