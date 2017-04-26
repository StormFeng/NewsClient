package com.midian.demo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.midian.demo.R;
import com.midian.demo.bean.WeChatBean;
import com.midian.demo.adapter.AdapterWeChat;
import com.midian.demo.retrofit.BaseSubcriber;
import com.midian.demo.retrofit.HttpMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/1/19 0019.
 */

public class FragmentArticle extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private List<WeChatBean.ListBean> data=new ArrayList<>();
    private AdapterWeChat adapterWeChat;
    private int page=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_article, null);
        ButterKnife.bind(this, v);
        HttpMethods.getInstance().getWeChat(new WeChatSubscriber(),"1");
        adapterWeChat=new AdapterWeChat(getContext(),data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterWeChat);
        swipeRefreshLayout.setOnRefreshListener(listener);
        adapterWeChat.openLoadAnimation();
        adapterWeChat.openLoadMore(20,true);
        adapterWeChat.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                HttpMethods.getInstance().getWeChat(new OnLoadMoreSubscriber(), page+"");
            }
        });
        return v;
    }

    SwipeRefreshLayout.OnRefreshListener listener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            page=1;
            HttpMethods.getInstance().getWeChat(new WeChatSubscriber(), "1");
        }
    };

    class WeChatSubscriber extends BaseSubcriber{
        @Override
        public void onNext(Object o) {
            super.onNext(o);
            WeChatBean weChatBean = (WeChatBean) o;
            data = weChatBean.getList();
            adapterWeChat.setNewData(data);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    class OnLoadMoreSubscriber extends BaseSubcriber{
        @Override
        public void onNext(Object o) {
            super.onNext(o);
            List<WeChatBean.ListBean> list = ((WeChatBean)o).getList();
            adapterWeChat.notifyDataChangedAfterLoadMore(list,true);
            if(list.size()<20){
                adapterWeChat.notifyDataChangedAfterLoadMore(false);
            }
        }
    }
}
