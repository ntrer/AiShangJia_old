package com.shushang.aishangjia.fragment.ShopTopFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.shushang.aishangjia.R;
import com.shushang.aishangjia.base.BaseFragment;

/**
 * Created by YD on 2018/9/12.
 */

public class ShopTopFragment extends BaseFragment {
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_shop_top, null);
        return view;
    }
}
