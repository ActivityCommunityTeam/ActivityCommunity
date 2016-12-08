package com.example.administrator.activitycommunity.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.activitycommunity.R;
import com.example.administrator.activitycommunity.fragment.ApplyDialogFragment;
import com.example.administrator.activitycommunity.model.ActivityDetail;
import com.example.administrator.activitycommunity.model.AttentionStatus;
import com.example.administrator.activitycommunity.model.Payment;
import com.example.administrator.activitycommunity.model.SaveOrUpdateAtten;
import com.example.administrator.activitycommunity.model.SaveOrder;
import com.example.administrator.activitycommunity.model.User;
import com.example.administrator.activitycommunity.net.NetWork;
import com.squareup.picasso.Picasso;

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
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class XQActivity extends AppCompatActivity {


    @BindView(R.id.activityDetail_title_tv)
    TextView activityDetailTitleTv;
    @BindView(R.id.activityDetail_data_tv)
    TextView activityDetailDataTv;
    @BindView(R.id.activityDetail_money_tv)
    TextView activityDetailMoneyTv;
    @BindView(R.id.activityDetail_attended_tv)
    TextView activityDetailAttendedTv;
    @BindView(R.id.activityDetail_residue_tv)
    TextView activityDetailResidueTv;
    @BindView(R.id.activityDetail_address_tv)
    TextView activityDetailAddressTv;
    @BindView(R.id.activityDetail_price_tv)
    TextView activityDetailPriceTv;
    @BindView(R.id.activityDetail_time_tv)
    TextView activityDetailTimeTv;
    @BindView(R.id.activityDetail_imageUrl_top_img)
    ImageView activityDetailImageUrlBottomImg;
    @BindView(R.id.xq_apply_btn)
    Button xqApplyBtn;
    @BindView(R.id.xq_atttion_btn)
    Button xqAtttionBtn;
    @BindView(R.id.webView)
    WebView webView;
    private Unbinder mUnbinder;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private TextView toolbar_title;
    private Subscription mSubscription;
    private ActivityDetail mActivityDetail;
    private CompositeSubscription compositeSubscription;
    private int mMax_num_people = -1;
    private Realm realm;
    private User mUser;
    private static String STATUS = "";
    private static int CODE = -1;
    private static String LZBZ = "";
    private static String MAINURL="http://211.149.235.17:8080/hdsq/app/getDetailContent/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xq);
        mUnbinder = ButterKnife.bind(this);



        compositeSubscription = new CompositeSubscription();
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        mUser = realm.where(User.class).findFirst();
        realm.commitTransaction();
        initData();
        initView();


    }


    private void getAttentionStatus(int activityId) {
        NetWork.getApiService().getAttentionStatus(mUser.getUser_id(),activityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AttentionStatus>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AttentionStatus attentionStatus) {
                        if (attentionStatus.getStatus().equals("01")){
                            xqAtttionBtn.setText("已关注");
                            STATUS = "02";
                        }else {
                            xqAtttionBtn.setText("关注");
                            STATUS = "01";
                        }

                    }
                });

    }

    private void setData() {
        activityDetailTitleTv.setText(mActivityDetail.getActivity_title());
        activityDetailDataTv.setText(mActivityDetail.getBegin_time());
        activityDetailMoneyTv.setText("总金额：" + mActivityDetail.getPrice() * mActivityDetail.getAttended());
        activityDetailAttendedTv.setText("已报名：" + mActivityDetail.getAttended());
        if (mMax_num_people != -1) {
            activityDetailResidueTv.setText("剩余名额：" + (mMax_num_people - mActivityDetail.getAttended()));
        }
        activityDetailAddressTv.setText(mActivityDetail.getSite());
        activityDetailPriceTv.setText(mActivityDetail.getPrice() + "元/人");
        activityDetailTimeTv.setText(mActivityDetail.getEnd_time());
        Picasso.with(this).load(mActivityDetail.getImage_url()).into(activityDetailImageUrlBottomImg);

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
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
        webView.setWebViewClient(new CustomWebViewClient());
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

        //webView.loadUrl(MAINURL+mActivityDetail.getActivity_id());
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setLoadWithOverviewMode(true);






    }
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
    final class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:window.java_obj.getSource('<head>'+" +
                    "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            super.onPageFinished(view, url);


        }
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

    }
    String HtmlStr="";
    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void getSource(String html) {
            Log.d("html=", html);
            HtmlStr=html;
            //webView.clearFormData();
            //webView.loadDataWithBaseURL(null,getNewContent(HtmlStr), "text/html", "utf-8", null);

        }
    }
    private String getNewContent(String htmltext){
        Log.i("gqfhtml",htmltext);
        Document doc=Jsoup.parse(htmltext);
        Elements elements=doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width","100%").attr("height","auto");
            element.attr("src","http://211.149.235.17:8080"+element.attr("src"));
            Log.i("gqf",element.toString());
        }

        Log.d("VACK", doc.toString());
        return doc.toString();
    }
    private void initData() {
        Intent intent = getIntent();
        int _activityId = intent.getIntExtra("activityId", -1);
        mMax_num_people = intent.getIntExtra("Max_num_people", -1);
        if (_activityId != -1) {
            mSubscription = NetWork.getApiService().getActivityDetail(_activityId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ActivityDetail>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("Daniel", "XQActivity---onError---获取详情失败！---");
                        }

                        @Override
                        public void onNext(ActivityDetail activityDetail) {
                            Log.i("Daniel", "XQActivity---onError---成功获取详情！---");
                            mActivityDetail = activityDetail;
                            getAttentionStatus(activityDetail.getActivity_id());
                            setData();
                        }
                    });
            compositeSubscription.add(mSubscription);

        }

    }

    private void initView() {
        appBarLayout = (AppBarLayout) findViewById(R.id.activity_xq_include_toolBar);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        compositeSubscription.unsubscribe();
    }

    @OnClick({R.id.xq_apply_btn, R.id.xq_atttion_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xq_apply_btn:
                show_Dialog(mActivityDetail);

                break;
            case R.id.xq_atttion_btn:

                getAttentionNetWork();

                break;
        }
    }

    private void show_Dialog(ActivityDetail activityDetail) {
        ApplyDialogFragment applyDialogFragment = new ApplyDialogFragment(activityDetail);

        applyDialogFragment.show(getFragmentManager(),"applyDialogFragment");
//        View view = LayoutInflater.from(this).inflate(R.layout.zhifu, null);
//        // 设置style 控制默认dialog带来的边距问题
//        final Dialog dialog = new Dialog(this, R.style.common_dialog);
//        dialog.setContentView(view);
//        dialog.show();
//        Window window = dialog.getWindow();
//        window.getDecorView().setPadding(0, 0, 0, 0);
//        WindowManager.LayoutParams params = window.getAttributes();
//        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.height=WindowManager.LayoutParams.WRAP_CONTENT;
//        params.gravity = Gravity.CENTER;
//        window.setAttributes(params);

//        new MaterialDialog.Builder(this)
//                .customView(R.layout.zhifu,true)
//                .
//                .show();
    }

    private int getSaveOrderNetWork() {
        final int[] _Order_id = {-1};
        // TODO: 2016/12/2  
        NetWork.getApiService().saveOrder(7, 6, "1503")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SaveOrder>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Daniel", "XQActivity---onError---保存订单失败！---");

                    }

                    @Override
                    public void onNext(SaveOrder saveOrder) {
                        Log.i("Daniel", "XQActivity---onNext---保存订单成功！---");
                        Log.i("Daniel", "XQActivity---onNext---saveOrder.getCode()---" + saveOrder.getCode());
                        Log.i("Daniel", "XQActivity---onNext---saveOrder.getOrder_id()---" + saveOrder.getOrder_id());
                        Log.i("Daniel", "XQActivity---onNext---saveOrder.getOrder_no()---" + saveOrder.getOrder_no());
                        _Order_id[0] = saveOrder.getOrder_id();
                    }
                });
        return _Order_id[0];

    }

    private void getPaymentNetwork(int Order_id) {

        NetWork.getApiService().payment(Order_id, "01", "01", "1502")
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Payment>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Daniel", "XQActivity---支付失败！---");


                    }

                    @Override
                    public void onNext(Payment payment) {
                        Toast.makeText(XQActivity.this, "" + payment.getCode(), Toast.LENGTH_SHORT).show();
                        Log.i("Daniel", "XQActivity---payment.getCode()---" + payment.getCode());

                    }
                });
    }

    private void getAttentionNetWork() {

        NetWork.getApiService().SaveOrUpdateAtten(mUser.getUser_id(), mActivityDetail.getActivity_id(), STATUS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SaveOrUpdateAtten>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(XQActivity.this, "关注失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(SaveOrUpdateAtten saveOrUpdateAtten) {
                        Log.i("Daniel", "XQActivity---onNext---saveOrUpdateAtten.getCode()---" + saveOrUpdateAtten.getCode());
                        Log.i("Daniel", "XQActivity---onNext---STATUS---" + STATUS);
                        CODE=saveOrUpdateAtten.getCode();
                        if (CODE == 1 && STATUS.equals("01")) {
                            Toast.makeText(XQActivity.this, "关注成功！", Toast.LENGTH_SHORT).show();
                            xqAtttionBtn.setText("已关注");
                            STATUS="02";
                        } else {
                            Toast.makeText(XQActivity.this, "已取消关注！", Toast.LENGTH_SHORT).show();
                            xqAtttionBtn.setText("关注");
                            STATUS="01";
                        }

                    }
                });
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
        URL url = new URL(urlpath);
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
