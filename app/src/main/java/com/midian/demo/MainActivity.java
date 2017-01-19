package com.midian.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.midian.demo.retrofit.HttpMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;


public class MainActivity extends FragmentActivity {


    @BindView(R.id.fragment)
    FrameLayout fragment;
    @BindView(R.id.iv_tab1)
    ImageView ivTab1;
    @BindView(R.id.tv_tab1)
    TextView tvTab1;
    @BindView(R.id.ll_tab1)
    LinearLayout llTab1;
    @BindView(R.id.iv_tab2)
    ImageView ivTab2;
    @BindView(R.id.tv_tab2)
    TextView tvTab2;
    @BindView(R.id.ll_tab2)
    LinearLayout llTab2;

    private FragmentManager fm;
    private Fragment mFragment;
    private FragmentNews fragmentNews;
    private FragmentArticle fragmentArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fm = getSupportFragmentManager();
        fragmentNews=new FragmentNews();
        fragmentArticle=new FragmentArticle();
        switchFragment(fragmentNews);
    }

    @OnClick({R.id.ll_tab1, R.id.ll_tab2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_tab1:
                setButton(R.id.ll_tab1);
                switchFragment(fragmentNews);
                break;
            case R.id.ll_tab2:
                setButton(R.id.ll_tab2);
                switchFragment(fragmentArticle);
                break;
        }
    }

    public void switchFragment(Fragment to) {
        FragmentTransaction mTransaction = fm.beginTransaction();
        if (mFragment == null)
            mFragment = new Fragment();
        Fragment from = mFragment;
        if (mFragment != to) {
            mFragment = to;
            System.out.println("to.isAdded()" + to.isAdded());
            if (to.isAdded()) {
                mTransaction.hide(from).show(to).commit();
            } else {
                mTransaction.hide(from).add(R.id.fragment, to)
                        .commit();
            }

        }
    }

    private void setButton(int id){
        if(id==R.id.ll_tab1){
            llTab1.setClickable(false);
            ivTab1.setImageResource(R.mipmap.news_y);
            tvTab1.setTextColor(Color.parseColor("#ff6666"));
            llTab2.setClickable(true);
            ivTab2.setImageResource(R.mipmap.article_n);
            tvTab2.setTextColor(Color.parseColor("#666666"));
        }else{
            llTab1.setClickable(true);
            ivTab1.setImageResource(R.mipmap.news_n);
            tvTab1.setTextColor(Color.parseColor("#666666"));
            llTab2.setClickable(false);
            ivTab2.setImageResource(R.mipmap.article_y);
            tvTab2.setTextColor(Color.parseColor("#ff6666"));
        }
    }
}
