package com.midian.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/1/19 0019.
 */

public class NewsDetailActivity extends Activity {
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.iv_Back)
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_newsdetail);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra("url");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        LogUtils.e(url);
        webview.loadUrl(url);
    }

    @OnClick(R.id.iv_Back)
    public void onClick() {
        finish();
    }
}
