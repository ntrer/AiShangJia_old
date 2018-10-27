package com.shushang.aishangjia.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushang.aishangjia.Bean.LianMeng;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.aishangjia.ui.GenderDialog;
import com.xys.libzxing.zxing.utils.PreferencesUtils;

public class NewPeopleDetailActivity2 extends AppCompatActivity {

    private LinearLayout mLlCustomerGender,mLLXiaoqu,mLLStyle,mLLProduct;
    private TextView mEtCustomerName;//客户姓名
    private TextView mEtCustomerMobile;//客户电话
    private TextView mTvCustomerGender;//客户性别
    private TextView mTvDecorateProgress;//装修进度
    private TextView mEtXiaoQu;//小区名称
    private TextView mEdit;
    private RelativeLayout rl_cardNum;
    private TextView mKaHao;//小区名称
    private TextView mEtDecorateStyle;//装修风格
    //    private Button mButton,mButton2;
    private Toolbar mToolbar;
    private RelativeLayout rl_activity;
    private TextView mEtDecorateAddress;//装修地址
    private TextView mTvIntentionToPurchaseProduct;//意向购买产品
    private TextView mTvActivity;
    private GenderDialog mGenderDialog;
    private View line;
    private Button mButton1,mButton2;
    private String token_id = null;
    private String activityId, activityName, activityCode;
    private String username, phone, sex, address,xiaoqu, sheng_code, shi_code, qu_code, sheng_name, shi_name, qu_name, decorationProgress, decorationStyle, thinkBuyGood,card_num;
    private RadioGroup mRadioButton;
    private String customerActionId;
    private static final int REQUEST_CODE_CITY = 2010;
    private static final int REQUEST_PROGRESS_ACTIVITY = 2012;
    private String progress=null;
    private String type;
    private String lianmeng;
    //    private TimeCount time;
    private Dialog mRequestDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_people_detail2);
        mEtCustomerName = (TextView) findViewById(R.id.et_customer_name);
        mEtCustomerMobile = (TextView) findViewById(R.id.et_customer_mobile);
        mLlCustomerGender = (LinearLayout) findViewById(R.id.ll_customer_gender);
        mTvCustomerGender = (TextView) findViewById(R.id.tv_customer_gender);
        mTvDecorateProgress = (TextView) findViewById(R.id.et_customer_progress);
        mEtDecorateStyle =  findViewById(R.id.et_decorate_style);
        mEtDecorateAddress = (TextView) findViewById(R.id.tv_customer_address);
        mLLXiaoqu=findViewById(R.id.ll_xiaoqu);
        mLLStyle=findViewById(R.id.ll_style);
        mLLProduct=findViewById(R.id.ll_product);
        mEtXiaoQu=  findViewById(R.id.et_customer_xiaoqu);
        mKaHao=findViewById(R.id.et_customer_kahao);
        mTvIntentionToPurchaseProduct= findViewById(R.id.et_intention_to_purchase_product);
        mTvActivity = (TextView) findViewById(R.id.tv_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        line=findViewById(R.id.cardNumLine);
        rl_cardNum=findViewById(R.id.cardNum);
        rl_activity = (RelativeLayout) findViewById(R.id.activity);
        mRequestDialog = ExtAlertDialog.creatRequestDialog(this, getString(R.string.submit));
        mRequestDialog.setCancelable(false);
        token_id = PreferencesUtils.getString(this, "token_id");
        final Intent data=getIntent();
        final LianMeng.DataListBean dataListBean= (LianMeng.DataListBean) data.getSerializableExtra("data");
        mTvDecorateProgress.setEnabled(false);
        mEtDecorateAddress.setEnabled(false);
        mLLProduct.setEnabled(false);
        mLLStyle.setEnabled(false);
        mLLXiaoqu.setEnabled(false);
        mEtDecorateStyle.setEnabled(false);
        mEtXiaoQu.setEnabled(false);
        mTvIntentionToPurchaseProduct.setEnabled(false);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData(dataListBean);
        mTvDecorateProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(NewPeopleDetailActivity2.this,ProgressActivity.class),REQUEST_PROGRESS_ACTIVITY);
            }
        });
        mEtDecorateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(NewPeopleDetailActivity2.this, CityActivity.class), REQUEST_CODE_CITY);
            }
        });



    }

    private void initData(LianMeng.DataListBean dataListBean) {
        if(dataListBean.getCustomerName()!=null){
            mEtCustomerName.setText(String.valueOf(dataListBean.getCustomerName()));
        }
        if(dataListBean.getCustomerMobile()!=null){
            mEtCustomerMobile.setText(String.valueOf(dataListBean.getCustomerMobile()));
        }
        if(dataListBean.getCustomerSex()!=null){
            mTvCustomerGender.setText(String.valueOf(dataListBean.getCustomerSex()));
        }
        if(dataListBean.getDecorationProgress()!=null){
            mTvDecorateProgress.setText(String.valueOf(dataListBean.getDecorationProgress()));
        }
        if(dataListBean.getAddress()!=null){
            mEtXiaoQu.setText(String.valueOf(dataListBean.getAddress()));
        }
        if(dataListBean.getDecorationStyle()!=null){
            mEtDecorateStyle.setText(dataListBean.getDecorationStyle().toString());
        }
        if(dataListBean.getShengName()!=null){
            mEtDecorateAddress.setText(String.valueOf(dataListBean.getShengName())+String.valueOf(dataListBean.getShiName())+String.valueOf(dataListBean.getQuName()));
        }
        if(dataListBean.getThinkBuyGood()!=null){
            mTvIntentionToPurchaseProduct.setText(dataListBean.getThinkBuyGood().toString());
        }
        if(dataListBean.getActivityName()!=null){
            rl_activity.setVisibility(View.VISIBLE);
            mTvActivity.setText(dataListBean.getActivityName().toString());
        }
        if(dataListBean.getCardNum()!=null){
                if(!dataListBean.getCardNum().equals("")){
                    mKaHao.setText(dataListBean.getCardNum().toString());
                    rl_cardNum.setVisibility(View.VISIBLE);
                    line.setVisibility(View.VISIBLE);
                }
            }

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


}
