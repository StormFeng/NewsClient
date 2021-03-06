package com.midian.demo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.midian.demo.bean.NewsBean;
import com.midian.demo.R;
import com.midian.demo.adapter.AdapterNews;
import com.midian.demo.retrofit.BaseSubcriber;
import com.midian.demo.retrofit.HttpMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/1/19 0019.
 */

public class FragmentTab extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private List<NewsBean.DataBean> data=new ArrayList<>();
    private AdapterNews adapterNews;
    private String param;
    private String title;

    public FragmentTab(String param, String title) {
        this.param = param;
        this.title = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_net, null);
        ButterKnife.bind(this, v);
        HttpMethods.getInstance().getNews(new NewsSubscriber(), param);
        adapterNews=new AdapterNews(getContext(),data,title);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterNews);
        swipeRefreshLayout.setOnRefreshListener(listener);
        return v;
    }

    SwipeRefreshLayout.OnRefreshListener listener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            HttpMethods.getInstance().getNews(new NewsSubscriber(), param);
        }
    };

    class NewsSubscriber extends BaseSubcriber{
        @Override
        public void onNext(Object o) {
            super.onNext(o);
            NewsBean bean = (NewsBean) o;
            data = bean.getData();
            adapterNews.setNewData(data);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
