package com.shushang.aishangjia.activity.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.NewPeople;

import java.util.List;

public class ZhangDanAdapter extends BaseQuickAdapter<NewPeople.DataListBean,BaseViewHolder> {


    public ZhangDanAdapter(@LayoutRes int layoutResId, @Nullable List<NewPeople.DataListBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, NewPeople.DataListBean item) {

    }
}
