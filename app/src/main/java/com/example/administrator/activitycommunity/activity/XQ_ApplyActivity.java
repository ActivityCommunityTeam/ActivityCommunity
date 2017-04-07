package com.example.administrator.activitycommunity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.administrator.activitycommunity.R;
import com.example.administrator.activitycommunity.model.ActivityDetail;
import com.example.administrator.activitycommunity.model.PersonalActivitys;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
    private static String MAINURL="http://211.149.235.17:8080/hdsq/app/getDetailContent/";

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

    }

    private void setData(PersonalActivitys personalActicity) {
        tittleTv.setText(personalActicity.getActivity_title());
        enrolTimeTv.setText("报名时间："+personalActicity.getEnrol_time());
        moneyTv.setText("支付金额："+personalActicity.getPay_amount());
        activityTimeTv.setText("活动时间："+personalActicity.getBegin_time()+"-"+personalActicity.getEnd_time());
        siteTv.setText("详细地址："+personalActicity.getSite());
        statusTv.setText("支付状态："+personalActicity.getPay_status());
        numberTv.setText("报名编号："+personalActicity.getOrder_no());
        setWeb();


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
    private void setWeb() {
        WebSettings settings = webView.getSettings();
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        settings.setTextZoom(100);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setInitialScale(57);
        webView.getSettings().setBlockNetworkImage(false);



        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);

        webView.getSettings().setBuiltInZoomControls(true);// 设置缩放
        webView.getSettings().setDisplayZoomControls(false);
        /*webView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
        webView.setWebViewClient(new CustomWebViewClient());*/
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HtmlStr = testGetHtml(MAINURL + mActivityDetail.getActivity_id());
                    Log.i("gqfaaa",HtmlStr);
                    Message m=new Message();
                    m.what=1;
                    handler.sendMessage(m);
                }catch (Exception e){

                }
            }
        });
        t.start();
    }

    String HtmlStr="";
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            //要做的事情
            super.handleMessage(msg);
            if(msg.what==1){
                if(HtmlStr!=null&&!HtmlStr.equals("")){
                    webView.loadDataWithBaseURL(null,getNewContent(HtmlStr), "text/html", "utf-8", null);
                }
            }
        }
    };

    private String getNewContent(String htmltext){
        Log.i("gqfhtml",htmltext);
        Document doc= Jsoup.parse(htmltext);
        Elements elements=doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width","100%").attr("height","auto");
            if(!element.attr("src").contains("http://")){
                element.attr("src","http://211.149.235.17:8080"+element.attr("src"));
            }
            Log.i("gqf",element.toString());
        }
        Log.d("VACK", doc.toString());
        return doc.toString();
    }

    public static byte[] readStream(InputStream inputStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }

        inputStream.close();
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static String testGetHtml(String urlpath) throws Exception {
        java.net.URL url = new URL(urlpath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(6 * 1000);
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() == 200) {
            InputStream inputStream = conn.getInputStream();
            byte[] data = readStream(inputStream);
            String html = new String(data);
            return html;
        }
        return null;
    }
}
