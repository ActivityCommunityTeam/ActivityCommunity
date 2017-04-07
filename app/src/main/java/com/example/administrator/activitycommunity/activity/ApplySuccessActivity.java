package com.example.administrator.activitycommunity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.activitycommunity.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ApplySuccessActivity extends AppCompatActivity {

    @BindView(R.id.pay_money_txt)
    TextView payMoneyTxt;
    @BindView(R.id.pay_know_btn)
    Button payKnowBtn;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private Unbinder binder;
    private TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_succeed);
        binder=ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        appBarLayout = (AppBarLayout) findViewById(R.id.paysuccessToolbar);
        toolbar = (Toolbar) appBarLayout.findViewById(R.id.base_toolBar);
        toolbar_title = (TextView) appBarLayout.findViewById(R.id.toolbar_title);
        Intent _intent = getIntent();
        String _price = _intent.getStringExtra("price");
        Log.i("Daniel","---------------"+_price);
        payMoneyTxt.setText(_price);
        setToolBar();
    }

    private void setToolBar() {
        //让原始的toolbar的title不显示
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolbar_title.setText("支付成功");

    }

    @OnClick(R.id.pay_know_btn)
    public void onClick() {
        onBackPressed();
    }
}
