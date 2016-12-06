package com.example.administrator.activitycommunity.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.activitycommunity.R;
import com.example.administrator.activitycommunity.fragment.FXFragment;
import com.example.administrator.activitycommunity.fragment.MYFragment;
import com.example.administrator.activitycommunity.fragment.TJFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {

    private BottomBar mBottomBar;
    private TJFragment tjFragment;
    private FXFragment fxFragment;
    private MYFragment myFragment;

    private static final int CONTENT_TJ = 1;
    private static final int CONTENT_FX = 2;
    private static final int CONTENT_MINE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//setSupportActionBar();

    }

    private void initView() {

        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
        setContent(CONTENT_TJ);
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_tj:
                        setContent(CONTENT_TJ);
                        break;
                    case R.id.tab_fx:
                        setContent(CONTENT_FX);
//                        setToolbar()
                        break;
                    case R.id.tab_my:
                        setContent(CONTENT_MINE);
                        break;
                }
            }
        });
    }




    private void setContent(int contentHome) {
        switch (contentHome) {
            case CONTENT_TJ:
//                NormalMoviesFragment normalMoviesFragment = (NormalMoviesFragment) getSupportFragmentManager().findFragmentByTag(HOME_TAG);
                if (tjFragment == null) {
                    tjFragment = new TJFragment();

                }
                fragmentCommit(tjFragment);
                break;
            case CONTENT_FX:

//                VipMoviesFragment vipMoviesFragment = (VipMoviesFragment) getSupportFragmentManager().findFragmentByTag(VIP_TAG);
                if (fxFragment == null) {
                    fxFragment = new FXFragment();

                }
                fragmentCommit(fxFragment);
                break;
            case CONTENT_MINE:

//                myFragment mineFragment = (myFragment) getSupportFragmentManager().findFragmentByTag(MINE_TAG);
                if (myFragment == null) {
                    myFragment = new MYFragment();

                }
                fragmentCommit(myFragment);
                break;
        }

    }

    private void fragmentCommit(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frameLayout, fragment);
        fragmentTransaction.commitNow();
    }
}
