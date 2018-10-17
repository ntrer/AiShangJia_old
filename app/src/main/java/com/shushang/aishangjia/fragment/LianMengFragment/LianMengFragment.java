package com.shushang.aishangjia.fragment.LianMengFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.MoneyPeople;
import com.shushang.aishangjia.Bean.YiXiangJin;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.AppPeopleActivity;
import com.shushang.aishangjia.activity.DailyOrderActivity;
import com.shushang.aishangjia.activity.LoginActivity2;
import com.shushang.aishangjia.activity.ProActivityActivity2;
import com.shushang.aishangjia.activity.SignActivity;
import com.shushang.aishangjia.activity.XiansuoActivity;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseFragment;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.fragment.YiXiangJinFragment.adapter.MoneyPeopleRecyclerViewAdapter2;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.ui.MyFab.FabAlphaAnimate;
import com.shushang.aishangjia.ui.MyFab.FabAttributes;
import com.shushang.aishangjia.ui.MyFab.OnFabClickListener;
import com.shushang.aishangjia.ui.MyFab.SuspensionFab;
import com.shushang.aishangjia.ui.SwipeItemLayout;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class LianMengFragment extends BaseFragment  implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, OnFabClickListener {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout=null;
    private RelativeLayout mLoading;
    private boolean isFirst=true;
    private LinearLayout llnodata;
    private SuspensionFab fabTop;
    private int page=1;
    private String resourceName=null;
    private Handler fabHandler=new Handler();
    private static final int REQUEST_CODE_SIGN= 2003;
    private static final int REQUEST_CODE_ADD= 2004;
    private static final int REQUEST_CODE_ACTIVITY = 2005;
    private static final int REQUEST_CODE_DAILY = 2006;
    private static final int REQUEST_CODE_XIANSUO = 2002;
    List<YiXiangJin.DataListBean> SignPeopleData = new ArrayList<>();
    List<MoneyPeople.DataListBean> refreshSignPeopleData = new ArrayList<>();
    private String  token_id = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "token_id");
    private  String activity_id = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "activityId");
    String signPelpleUrl = BaseUrl.BASE_URL+"phoneApi/activityController.do?method=getOrders&token_id="+token_id+"&page=1"+"&rows=10";
    private MoneyPeopleRecyclerViewAdapter2 mMoneyPeopleRecyclerViewAdapter;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mToolbar=rootView.findViewById(R.id.toolbar);
        fabTop= rootView.findViewById(R.id.fab_top);
        llnodata=rootView.findViewById(R.id.ll_no_data);
        mLoading=rootView.findViewById(R.id.loading);
        mToolbar.setTitle("");
        FabAttributes collection = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.mipmap.note))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(1)
                .build();

        FabAttributes xiansuo = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.mipmap.xiansuo))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(3)
                .build();

        FabAttributes email = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.mipmap.people_add))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(2)
                .build();

        FabAttributes email2 = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.mipmap.money_activity))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(4)
                .build();

        FabAttributes email3 = new FabAttributes.Builder()
                .setBackgroundTint(Color.parseColor("#2096F3"))
                .setSrc(getResources().getDrawable(R.mipmap.money_4_coloring))
                .setFabSize(FloatingActionButton.SIZE_MINI)
                .setPressedTranslationZ(10)
                .setTag(5)
                .build();
        if(resourceName!=null&&resourceName.equals("1116")){
            fabTop.addFab(collection,xiansuo,email,email2,email3);
        }
        else {
            fabTop.addFab(collection,email,email2,email3);
        }
        fabTop.setAnimationManager(new FabAlphaAnimate(fabTop));
        fabTop.setFabClickListener(this);
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_lianmeng, null);
        mSwipeRefreshLayout=view.findViewById(R.id.srl_home);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }


    @Override
    public void initData() {
        super.initData();
        resourceName=PreferencesUtils.getString(mContext,"ResourceName");
    }

    private void getData() {
        mSwipeRefreshLayout.setRefreshing(true);
        RestClient.builder()
                .url(signPelpleUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("SignP",response);
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (response != null) {
                            try {
                                YiXiangJin yiXiangJin = JSONUtil.fromJson(response, YiXiangJin.class);
                                if(yiXiangJin.getRet().equals("200")){
                                    SignPeopleData = yiXiangJin.getDataList();
                                    if(SignPeopleData.size()!=0){
                                        showTabData(SignPeopleData);
                                        llnodata.setVisibility(View.GONE);
                                    }
                                    else {
                                        showTabData(SignPeopleData);
                                        llnodata.setVisibility(View.VISIBLE);
                                    }
                                    isFirst=false;
                                }
                                else if(yiXiangJin.getRet().equals("101")){
                                    Toast.makeText(getActivity(), ""+yiXiangJin.getMsg(), Toast.LENGTH_SHORT).show();
                                    PreferencesUtils.putString(getActivity(),"token_id",null);
                                    startActivity(new Intent(getActivity(), LoginActivity2.class));
                                    getActivity().finish();
                                }
                                else if(yiXiangJin.getRet().equals("201")){
                                    Toast.makeText(getActivity(), ""+yiXiangJin.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (Exception e){
                                Log.d("出错了",e.toString());
                            }
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(MyApplication.getInstance().getApplicationContext(), "服务器内部错误！", Toast.LENGTH_LONG).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(MyApplication.getInstance().getApplicationContext(), "服务器内部错误！", Toast.LENGTH_LONG).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                })
                .build()
                .get();
    }

    private void showTabData(List<YiXiangJin.DataListBean> signPeopleData) {
        final LinearLayoutManager linermanager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linermanager);
//        mMoneyPeopleRecyclerViewAdapter = new MoneyPeopleRecyclerViewAdapter2(R.layout.item_money3, signPeopleData,mHandler);
        mMoneyPeopleRecyclerViewAdapter.setOnLoadMoreListener(this, mRecyclerView);
        if(isFirst){
            Log.d("真",isFirst+"");
            mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(MyApplication.getInstance().getApplicationContext()));
        }
        //重复执行动画
        mMoneyPeopleRecyclerViewAdapter.isFirstOnly(false);
        mRecyclerView.setAdapter(mMoneyPeopleRecyclerViewAdapter);
        mLoading.setVisibility(View.GONE);
    }



    @Override
    public void onRefresh() {
        getData();
    }


    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    public void onFabClick(FloatingActionButton fab, Object tag) {
        if (tag.equals(1)) {
            fabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fabTop.closeAnimate();
                }
            },1000);
            startActivityForResult(new Intent(getActivity(), SignActivity.class),REQUEST_CODE_SIGN);
        }else if (tag.equals(2)){
            fabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fabTop.closeAnimate();
                }
            },1000);
            startActivityForResult(new Intent(getActivity(), AppPeopleActivity.class),REQUEST_CODE_ADD);
        }
        else if (tag.equals(3)){
            fabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fabTop.closeAnimate();
                }
            },1000);
            startActivityForResult(new Intent(getActivity(), XiansuoActivity.class),REQUEST_CODE_ADD);
        }
        else if (tag.equals(4)){
            fabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fabTop.closeAnimate();
                }
            },1000);
            //表示所有权限都授权了
            Intent openCameraIntent = new Intent(getActivity(), ProActivityActivity2.class);
            openCameraIntent.putExtra("type", "3");
            openCameraIntent.putExtra("event","7");
            startActivityForResult(openCameraIntent, REQUEST_CODE_ACTIVITY );
        }
        else if (tag.equals(5)){
            fabHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fabTop.closeAnimate();
                }
            },1000);
            startActivityForResult(new Intent(getActivity(), DailyOrderActivity.class),REQUEST_CODE_DAILY);
        }
    }
}
