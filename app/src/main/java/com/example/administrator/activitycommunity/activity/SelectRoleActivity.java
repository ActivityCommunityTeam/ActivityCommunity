package com.example.administrator.activitycommunity.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.activitycommunity.R;
import com.example.administrator.activitycommunity.fragment.BoyRoleFragment;
import com.example.administrator.activitycommunity.fragment.GirlRoleFragment;
import com.example.administrator.activitycommunity.fragment.SelectSexFragment;

public class SelectRoleActivity extends AppCompatActivity {
    private SelectSexFragment mSelectSexFragment;
    private BoyRoleFragment mBoyRoleFragment;
    private GirlRoleFragment mGirlRoleFragment;

    private static final int CONTENT_SEX = 1;
    private static final int CONTENT_BOY = 2;
    private static final int CONTENT_GRIL = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        initView();
    }

    private void initView() {
        setContent(CONTENT_SEX);

    }

    private void setContent(int contentHome) {
        switch (contentHome) {
            case CONTENT_SEX:
                if (mSelectSexFragment == null) {
                    mSelectSexFragment = new SelectSexFragment();

                }
                fragmentCommit(mSelectSexFragment);
                break;
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
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.selectRole_frameLayout, fragment);
        fragmentTransaction.commitNow();
    }
}
