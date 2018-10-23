package com.shushang.aishangjia.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.NewPeople;
import com.shushang.aishangjia.Bean.TabList;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.adapter.ZhangDanAdapter;
import com.shushang.aishangjia.application.MyApplication;
import com.shushang.aishangjia.base.BaseActivity;
import com.shushang.aishangjia.base.BaseUrl;
import com.shushang.aishangjia.fragment.HomeFragment.adapter.TabRecyclerViewAdapter;
import com.shushang.aishangjia.net.RestClient;
import com.shushang.aishangjia.net.callback.IError;
import com.shushang.aishangjia.net.callback.IFailure;
import com.shushang.aishangjia.net.callback.ISuccess;
import com.shushang.aishangjia.utils.Json.JSONUtil;
import com.shushang.aishangjia.utils.SharePreferences.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class ZhangDanActivity extends BaseActivity implements View.OnClickListener,BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener {

    private Toolbar mToolbar;
    private RelativeLayout mLoading;
    private TextView mMounth;
    private RecyclerView mRlTab;
    private ImageView mMore;
    private LinearLayout mTabLayout;
    private View mLine;
    private View mView;
    private int page=1;
    private RecyclerView mRvZhangdan;
    private SwipeRefreshLayout mSrlZhangdan;
    private LinearLayout mLlNoData;
    private TabRecyclerViewAdapter tabRecyclerViewAdapter;
    private List<TabList.DataListBean> data;
    private boolean isFirst=true;
    private String allData="100";
    private List<NewPeople.DataListBean> dataList=new ArrayList<>();
    private  List<NewPeople.DataListBean> data2=new ArrayList<>();
    private ZhangDanAdapter mZhangDanAdapter;
    private String  token_id = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(), "token_id");
    @Override
    public int setLayout() {
        return R.layout.activity_zhang_dan;
    }

    @Override
    public void init() {
        mLoading=findViewById(R.id.loading);
        mMounth=findViewById(R.id.allMerchat);
        mRlTab=findViewById(R.id.rl_tab);
        mTabLayout=findViewById(R.id.TabLayout);
        mRvZhangdan=findViewById(R.id.rv_zhangdan);
        mSrlZhangdan=findViewById(R.id.srl_zhangdan);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mLlNoData=findViewById(R.id.ll_no_data);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getTabData("");
        mMounth.setOnClickListener(this);
        mSrlZhangdan.setOnRefreshListener(this);
    }

    //获取tab栏数据
    private void getTabData(final String allData) {
        String activity_id= PreferencesUtils.getString(ZhangDanActivity.this,"activityId");
        String url= BaseUrl.BASE_URL+"phoneApi/activityController.do?method=getMerchants&token_id="+token_id+"&activity_id="+activity_id;
        Log.d("TabList",url);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("TabList",response);
                        if(response!=null){
                            try {
                                TabList tabList = JSONUtil.fromJson(response, TabList.class);
                                if(tabList.getRet().equals("101")){
                                    Toast.makeText(ZhangDanActivity.this, ""+tabList.getMsg(), Toast.LENGTH_SHORT).show();
                                    PreferencesUtils.putString(ZhangDanActivity.this,"token_id",null);
                                    startActivity(new Intent(ZhangDanActivity.this, LoginActivity2.class));
                                    finish();
                                }
                                else {
                                    if(tabList.getRet().equals("200")){
                                        data = tabList.getDataList();
                                        if(data.size()!=0){
                                            showTabData(data);
                                            isFirst=false;
                                            if(!allData.equals("")){
                                                getData();
                                            }else {
                                                getData();
                                            }
                                        }
                                    }
                                    else if(tabList.getRet().equals("201")){
                                        Toast.makeText(ZhangDanActivity.this, ""+tabList.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            catch (Exception e){

                            }
                        }

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(ZhangDanActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .get();
    }



    public void getData() {
        String url= BaseUrl.BASE_URL+"phoneApi/customerManager.do?method=getCustomers&token_id="+token_id+"&page=1&rows=10&date=2018-10-17"+"&type=0";
        Log.d("BaseUrl",url);
        mSrlZhangdan.setRefreshing(true);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if(response!=null){
                            mSrlZhangdan.setRefreshing(false);
                            Log.d("AppPeopleActivity",response);
                            try {
                                NewPeople test = JSONUtil.fromJson(response, NewPeople.class);
                                if(test.getRet().equals("101")){
                                    Toast.makeText(ZhangDanActivity.this, ""+test.getMsg(), Toast.LENGTH_SHORT).show();
                                    PreferencesUtils.putString(ZhangDanActivity.this,"token_id",null);
                                    startActivity(new Intent(ZhangDanActivity.this, LoginActivity2.class));
                                    finish();
                                }
                                else if(test.getRet().equals("200")){
                                    dataList = test.getDataList();
                                    if(dataList.size()!=0){
                                        showData(dataList);
                                        mLlNoData.setVisibility(View.GONE);
                                    }
                                    else {
                                        showData(dataList);
                                        mLlNoData.setVisibility(View.VISIBLE);
                                    }
                                }
                                else if(test.getRet().equals("201")){
                                    Toast.makeText(ZhangDanActivity.this, ""+test.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (Exception e){

                                Toast.makeText(ZhangDanActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                        mSrlZhangdan.setRefreshing(false);
                        Toast.makeText(ZhangDanActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
                    }
                }).error(new IError() {
            @Override
            public void onError(int code, String msg) {

                mSrlZhangdan.setRefreshing(false);
                Toast.makeText(ZhangDanActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
            }
        })
                .build()
                .get();
    }

    private void showData(final List<NewPeople.DataListBean> dataList) {
        mView=View.inflate(MyApplication.getInstance().getApplicationContext(), R.layout.headerview8,null);
        mZhangDanAdapter = new ZhangDanAdapter(R.layout.item_zhangdan, dataList);
        final LinearLayoutManager linermanager=new LinearLayoutManager(this);
        mRvZhangdan.setLayoutManager(linermanager);
        mZhangDanAdapter.setOnLoadMoreListener(this, mRvZhangdan);
        mZhangDanAdapter.addHeaderView(mView);
        //重复执行动画
        mZhangDanAdapter.isFirstOnly(false);
        mRvZhangdan.setAdapter(mZhangDanAdapter);

    }


    private void showTabData(List<TabList.DataListBean> data) {
        if(isFirst){
            tabRecyclerViewAdapter=new TabRecyclerViewAdapter(R.layout.tab_items,data);
            tabRecyclerViewAdapter.setThisPosition(100);
        }
        initRecyclerView(tabRecyclerViewAdapter);
    }


    private void initRecyclerView(final TabRecyclerViewAdapter tabRecyclerViewAdapter) {
        final LinearLayoutManager manager=new LinearLayoutManager(ZhangDanActivity.this,LinearLayoutManager.HORIZONTAL,false);
        mRlTab.setLayoutManager(manager);
        //解决嵌套滑动冲突
        mRlTab.setNestedScrollingEnabled(false);
        mRlTab.setAdapter(tabRecyclerViewAdapter);
        tabRecyclerViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mMounth.setTextColor(getResources().getColor(R.color.darker_gray));
                tabRecyclerViewAdapter.setThisPosition(position);
                tabRecyclerViewAdapter.notifyDataSetChanged();
//                mGiftPaperRefreshHandler.switchData(data.get(position).getMerchant_id());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 退出activity的动画效果不起作用，要在java代码里写
     * 复写activity的finish方法，在overridePendingTransition中写入自己的动画效果
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.allMerchat:
                getTabData(allData);
                mMounth.setTextColor(getResources().getColor(R.color.colorPrimary));
//                tabRecyclerViewAdapter.setThisPosition(100);
//                tabRecyclerViewAdapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    public void onRefresh() {
        getData();
    }


    @Override
    public void onLoadMoreRequested() {
        loadMore();
    }

    private void loadMore() {
        page=page+1;
        String url= BaseUrl.BASE_URL+"phoneApi/customerManager.do?method=getCustomers&token_id="+token_id+"&page="+page+"&rows=10"+"&date=2018-10-17"+"&type=0";
        try {
            RestClient.builder()
                    .url(url)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            if(response!=null) {
                                Log.d("nnnnnnn",response);
                                NewPeople test = JSONUtil.fromJson(response, NewPeople.class);
                                if(test.getRet().equals("200")){
                                    if(page>test.getIntmaxPage()){
                                        page=1;
                                        mZhangDanAdapter.loadMoreComplete();
                                        mZhangDanAdapter.loadMoreEnd();
                                    }
                                    else if(test.getDataList().size()!=0){
                                        List<NewPeople.DataListBean> dataList = test.getDataList();
                                        LoadMoreData(dataList);
                                        Log.d("33333333333",response);
                                        mZhangDanAdapter.loadMoreComplete();
                                    }
                                    else if(test.getDataList().size()==0){
                                        mZhangDanAdapter.loadMoreComplete();
                                        mZhangDanAdapter.loadMoreEnd();
                                    }
                                }
                                else {
                                    mZhangDanAdapter.loadMoreComplete();
                                    mZhangDanAdapter.loadMoreEnd();
                                }
                            }
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            Toast.makeText(ZhangDanActivity.this, "错误了", Toast.LENGTH_SHORT).show();
                            mZhangDanAdapter.loadMoreComplete();
                            mZhangDanAdapter.loadMoreEnd();
                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            Toast.makeText(ZhangDanActivity.this, "错误了"+code+msg, Toast.LENGTH_SHORT).show();
                            mZhangDanAdapter.loadMoreComplete();
                            mZhangDanAdapter.loadMoreEnd();
                        }
                    })
                    .build()
                    .get();
        }
        catch (Exception e){

        }

    }

    private void LoadMoreData(List<NewPeople.DataListBean> dataList) {
        if(dataList.size()!=0){
            mZhangDanAdapter.addData(dataList);
            mZhangDanAdapter.loadMoreComplete();
        }

    }
}
