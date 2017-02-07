package com.midian.demo;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_newsdetail);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        tvTitle.setText(title);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
            }
        });
//        webview.getSettings().setJavaScriptEnabled(true);//启用js
        webview.canGoBack();
        webview.loadUrl(url);
    }

    @OnClick(R.id.iv_Back)
    public void onClick() {
        finish();
    }
}
