package com.shushang.aishangjia.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushang.aishangjia.Bean.NewPeople;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GongGaoActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener{


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_gonggao)
    RecyclerView mRvGonggao;
    @BindView(R.id.srl_gonggao)
    SwipeRefreshLayout mSrlGonggao;
    @BindView(R.id.loading)
    ProgressBar mLoading;
    @BindView(R.id.ll_no_data)
    LinearLayout mLlNoData;

    @Override
    public int setLayout() {
        return R.layout.activity_gong_gao;
    }

    @Override
    public void init() {
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSrlGonggao.setOnRefreshListener(this);
        getData();
    }

    public void getData() {
//        String url= BaseUrl.BASE_URL+"phoneApi/customerManager.do?method=getCustomers&token_id="+token_id+"&page=1&rows=10&date=2018-10-17"+"&type=0";
//        Log.d("BaseUrl",url);
//        mSrlGonggao.setRefreshing(true);
//        RestClient.builder()
//                .url(url)
//                .success(new ISuccess() {
//                    @Override
//                    public void onSuccess(String response) {
//                        if(response!=null){
//                            mSrlGonggao.setRefreshing(false);
//                            Log.d("AppPeopleActivity",response);
//                            try {
//                                NewPeople test = JSONUtil.fromJson(response, NewPeople.class);
//                                if(test.getRet().equals("101")){
//                                    Toast.makeText(GongGaoActivity.this, ""+test.getMsg(), Toast.LENGTH_SHORT).show();
//                                    PreferencesUtils.putString(GongGaoActivity.this,"token_id",null);
//                                    startActivity(new Intent(GongGaoActivity.this, LoginActivity2.class));
//                                    finish();
//                                }
//                                else if(test.getRet().equals("200")){
//                                    dataList = test.getDataList();
//                                    if(dataList.size()!=0){
//                                        showTabData(dataList);
//                                        mLlNoData.setVisibility(View.GONE);
//                                    }
//                                    else {
//                                        showTabData(dataList);
//                                        mLlNoData.setVisibility(View.VISIBLE);
//                                    }
//                                }
//                                else if(test.getRet().equals("201")){
//                                    Toast.makeText(GongGaoActivity.this, ""+test.getMsg(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                            catch (Exception e){
//
//                                Toast.makeText(GongGaoActivity.this, ""+e, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                })
//                .failure(new IFailure() {
//                    @Override
//                    public void onFailure() {
//
//                        mSrlGonggao.setRefreshing(false);
//                        Toast.makeText(GongGaoActivity.this, "获取数据错误了！！！！", Toast.LENGTH_SHORT).show();
//                    }
//                }).error(new IError() {
//            @Override
//            public void onError(int code, String msg) {
//
//                mSrlGonggao.setRefreshing(false);
//                Toast.makeText(GongGaoActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
//            }
//        })
//                .build()
//                .get();
    }

    private void showTabData(final List<NewPeople.DataListBean> dataList) {
//        mLianMengAdapter = new LianMengAdapter(R.layout.item_lianmeng, dataList,this);
//        final LinearLayoutManager linermanager=new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(linermanager);
//        mLianMengAdapter.setOnLoadMoreListener(this, mRecyclerView);
//        mLianMengAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Intent intent=new Intent(GongGaoActivity.this,NewPeopleDetailActivity.class);
//                NewPeople.DataListBean dataListBean = dataList.get(position);
//                intent.putExtra("data",dataListBean);
//                startActivityForResult(intent,REQUEST_CODE_NEW_PEOPLE);
//            }
//        });
//        //重复执行动画
//        mLianMengAdapter.isFirstOnly(false);
//        mRecyclerView.setAdapter(mLianMengAdapter);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }





    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void onLoadMoreRequested() {

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
