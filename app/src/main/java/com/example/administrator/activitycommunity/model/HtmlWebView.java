package com.example.administrator.activitycommunity.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by johe on 2016/12/8.
 */

public class HtmlWebView extends WebView{
    String htmlStr;
    public HtmlWebView(Context context) {
        super(context);
    }
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

}
