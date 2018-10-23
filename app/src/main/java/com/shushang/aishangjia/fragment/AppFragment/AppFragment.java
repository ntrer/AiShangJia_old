package com.shushang.aishangjia.fragment.AppFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.shushang.aishangjia.Bean.MenuItem;
import com.shushang.aishangjia.R;
import com.shushang.aishangjia.activity.AppPeopleActivity;
import com.shushang.aishangjia.activity.DailyOrderActivity;
import com.shushang.aishangjia.activity.LianMengActivity;
import com.shushang.aishangjia.activity.ProActivityActivity2;
import com.shushang.aishangjia.activity.SignActivity;
import com.shushang.aishangjia.activity.XiansuoActivity;
import com.shushang.aishangjia.activity.ZhangDanActivity;
import com.shushang.aishangjia.base.BaseFragment;
import com.shushang.aishangjia.fragment.AppFragment.adapter.AppAdapter;
import com.shushang.aishangjia.ui.MyFab.FabAlphaAnimate;
import com.shushang.aishangjia.ui.MyFab.FabAttributes;
import com.shushang.aishangjia.ui.MyFab.OnFabClickListener;
import com.shushang.aishangjia.ui.MyFab.SuspensionFab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AppFragment extends BaseFragment implements OnFabClickListener {

    private SuspensionFab fabTop;
    private int page=1;
    private String resourceName=null;
    private Handler fabHandler=new Handler();
    private static final int REQUEST_CODE_SIGN= 2003;
    private static final int REQUEST_CODE_ADD= 2004;
    private static final int REQUEST_CODE_ACTIVITY = 2005;
    private static final int REQUEST_CODE_DAILY = 2006;
    private static final int REQUEST_CODE_XIANSUO = 2002;
    private static final int REQUEST_CODE_NEW_PEOPLE =2662;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private AppAdapter adapter;
    private List<MenuItem> list;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRecyclerView=rootView.findViewById(R.id.rv_app);
        mToolbar=rootView.findViewById(R.id.toolbar);
        fabTop= rootView.findViewById(R.id.fab_top);
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

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));
        adapter=new AppAdapter(list);
        mRecyclerView.setAdapter(adapter);
        //为RecycleView绑定触摸事件
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                //首先回调的方法 返回int表示是否监听该方向
                int dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;//拖拽
                int swipeFlags =0;//侧滑删除
                return makeMovementFlags(dragFlags,swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //滑动事件
                Collections.swap(list,viewHolder.getAdapterPosition(),target.getAdapterPosition());
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //侧滑事件
                list.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }

            @Override
            public boolean isLongPressDragEnabled() {
                //是否可拖拽
                return true;
            }
        });

        helper.attachToRecyclerView(mRecyclerView);
        adapter.setItemClickListener(new AppAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name, int position) {
               if(name.equals("财务")){
                   startActivity(new Intent(getActivity(),ZhangDanActivity.class));
               }
               else if(name.equals("公告")){
                   startActivity(new Intent(getActivity(),LianMengActivity.class));
               }
               else if(name.equals("联盟")){
                 startActivity(new Intent(getActivity(),LianMengActivity.class));
               }
               else if(name.equals("记账")){
                   startActivity(new Intent(getActivity(),LianMengActivity.class));
               }
            }
        });
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_app, null);
        return view;
    }


    @Override
    public void initData() {
        super.initData();
        list = new ArrayList<>();
        list.add(new MenuItem("财务", R.mipmap.caiwu));
        list.add(new MenuItem("公告", R.mipmap.gonggao));
        list.add(new MenuItem("联盟", R.mipmap.lianmeng));
        list.add(new MenuItem("记账", R.mipmap.caiwu));
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
//            startActivityForResult(new Intent(getActivity(), AppPeopleActivity.class),REQUEST_CODE_ADD);
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
            fabTop.closeAnimate();
            //表示所有权限都授权了
            Intent openCameraIntent = new Intent(getActivity(), ProActivityActivity2.class);
            openCameraIntent.putExtra("type", "3");
            openCameraIntent.putExtra("event","6");
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
