package com.shushang.aishangjia.fragment.LianMengFragment.adapter;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushang.aishangjia.Bean.NewPeople;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.LianMengActivity;
import com.shushang.aishangjia.base.PermissionListener;
import com.shushang.aishangjia.ui.ExtAlertDialog;
import com.shushang.aishangjia.utils.permissionUtil;

import java.util.List;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

public class LianMengAdapter extends BaseQuickAdapter<NewPeople.DataListBean,BaseViewHolder> {

    private LianMengActivity mActivity2;

    public LianMengAdapter(@LayoutRes int layoutResId, @Nullable List<NewPeople.DataListBean> data, LianMengActivity mainActivity2) {
        super(layoutResId, data);
        this.mActivity2=mainActivity2;
    }

    @Override
    protected void convert(BaseViewHolder helper, final NewPeople.DataListBean item) {
        helper.setText(R.id.people,item.getUsername());
        helper.setText(R.id.label,item.getMerchantName());
        helper.setText(R.id.date,item.getCjsj());
        helper.setText(R.id.phone,item.getPhone());
        helper.getView(R.id.call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissionPhone(item.getPhone());
            }
        });
    }

    //请求相机权限
    private void permissionPhone(final String phone){
        mActivity2.requestRunPermisssion(new String[]{Manifest.permission.CALL_PHONE,}, new PermissionListener() {
            @Override
            public void onGranted() {
                try {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    Uri data = Uri.parse("tel:" + phone);
                    intent.setData(data);
                    startActivity(intent);
                }
                catch (SecurityException e){
                    ToastUtils.showLong(e.toString());
                }
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                    for(String permission : deniedPermission){
                        reGetPermission();
                }
            }

        });
    }


    private void reGetPermission() {
        ExtAlertDialog.showSureDlg(mActivity2, "警告", "权限被拒绝，部分功能将无法使用，请重新授予权限", "确定", new ExtAlertDialog.IExtDlgClick() {
            @Override
            public void Oclick(int result) {
                if(result==1){
                    permissionUtil.GoToSetting(mActivity2);
                }
            }
        });
    }

}
