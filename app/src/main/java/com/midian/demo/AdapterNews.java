package com.midian.demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/1/19 0019.
 */

public class AdapterNews extends BaseQuickAdapter<NewsBean.DataBean> {

    private Context context;
    private String title;

    public AdapterNews(Context context,List<NewsBean.DataBean> data,String title) {
        super(R.layout.item,data);
        this.context=context;
        this.title=title;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final NewsBean.DataBean dataBean) {
        final List<String> pics=new ArrayList<>();
        pics.add(dataBean.getThumbnail_pic_s());
        pics.add(dataBean.getThumbnail_pic_s02());
        pics.add(dataBean.getThumbnail_pic_s03());
        baseViewHolder.setText(R.id.tv_Title,dataBean.getTitle())
                .setText(R.id.tv_Author,dataBean.getAuthor_name())
                .setText(R.id.tv_Date,dataBean.getDate());
        baseViewHolder.setOnClickListener(R.id.ll_item, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                LogUtils.e("url:"+dataBean.getUrl());
                bundle.putString("url",dataBean.getUrl());
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("url",dataBean.getUrl());
                intent.putExtra("title",title);
                context.startActivity(intent);
            }
        });
        GridView gridView = (GridView) baseViewHolder.getConvertView().findViewById(R.id.gridView);
        gridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return pics.size();
            }

            @Override
            public Object getItem(int i) {
                return pics.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(final int i, View convertView, ViewGroup viewGroup) {
                ViewHolder viewHolder = null;
                if(convertView==null){
                    convertView=LayoutInflater.from(context).inflate(R.layout.item_pic, null);
                    viewHolder=new ViewHolder();
                    viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv);
                    convertView.setTag(viewHolder);
                }else{
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                Glide.with(context).load(pics.get(i)).into(viewHolder.iv);
                return convertView;
            }

            class ViewHolder{
                ImageView iv;
            }
        });
    }
}
