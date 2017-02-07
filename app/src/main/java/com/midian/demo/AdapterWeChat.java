package com.midian.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/19 0019.
 */

public class AdapterWeChat extends BaseQuickAdapter<WeChatBean.ListBean> {

    private Context context;

    public AdapterWeChat(Context context, List<WeChatBean.ListBean> data) {
        super(R.layout.item_wechat,data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final WeChatBean.ListBean ListBean) {

        baseViewHolder.setText(R.id.tvTitle,ListBean.getTitle())
                .setText(R.id.tvSource,ListBean.getSource());
        baseViewHolder.setOnClickListener(R.id.ll_item, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                LogUtils.e("url:"+ListBean.getUrl());
                bundle.putString("url",ListBean.getUrl());
                Intent intent = new Intent(context, WeChatDetailActivity.class);
                intent.putExtra("url",ListBean.getUrl());
                intent.putExtra("title","微信精选");
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(ListBean.getFirstImg()).into((ImageView) baseViewHolder.getView(R.id.iv));
    }
}
