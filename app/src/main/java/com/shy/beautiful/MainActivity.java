package com.shy.beautiful;

import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shy.beautiful.base.ActivityCollector;
import com.shy.beautiful.base.BaseActivity;
import com.shy.beautiful.fragment.FollowFragment;
import com.shy.beautiful.fragment.MainFragment;
import com.shy.beautiful.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fl_fragment_content)
    FrameLayout flFragmentContent;
    @BindView(R.id.iv_sy)
    ImageView ivSy;
    @BindView(R.id.tv_sy)
    TextView tvSy;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    @BindView(R.id.iv_kq)
    ImageView ivKq;
    @BindView(R.id.tv_kq)
    TextView tvKq;
    @BindView(R.id.rl_follow)
    RelativeLayout rlFollow;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nv_item)
    NavigationView nvItem;

    MainFragment mainFragment;
    FollowFragment followFragment;
    private static boolean mBackKeyPressed = false;//记录是否有首次按键


    //fragment事务管理
    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;
    private FrameLayout mFlFragmentContent;

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        nvItem.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_collection:
                        break;
                    case R.id.menu_sub:
                        Intent i = new Intent(MainActivity.this, SubscribeActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.menu_return:
                        ToastUtils.showShort(MainActivity.this,"待开发...  ");
                        break;
                    case R.id.menu_reward :
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void initData() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.img_head_nav);
        }
        //默认第一个首页被选中高亮显示
        rlMain.setSelected(true);
        mFragmentManager = getSupportFragmentManager();
        mTransaction = mFragmentManager.beginTransaction();
        mTransaction.replace(R.id.fl_fragment_content, mainFragment = new MainFragment());
        mTransaction.commit();
    }

    @OnClick({R.id.rl_main, R.id.rl_follow})
    public void onViewClicked(View view) {
        mTransaction = mFragmentManager.beginTransaction(); //开启事务
        hideAllFragment(mTransaction);
        switch (view.getId()) {
            case R.id.rl_main:
                seleted();
                rlMain.setSelected(true);
                if (mainFragment == null) {
                    mainFragment = new MainFragment();
                    mTransaction.add(R.id.fl_fragment_content, mainFragment);
                } else {
                    mTransaction.show(mainFragment);
                }
                break;
            case R.id.rl_follow:
                seleted();
                rlFollow.setSelected(true);
                if (followFragment == null) {
                    followFragment = new FollowFragment();
                    mTransaction.add(R.id.fl_fragment_content, followFragment);
                } else {
                    mTransaction.show(followFragment);
                }
                break;
        }
        mTransaction.commit();
    }

    //设置所有按钮都是默认都不选中
    private void seleted() {
        rlMain.setSelected(false);
        rlFollow.setSelected(false);
    }

    //删除所有Fragment
    private void hideAllFragment(FragmentTransaction transaction) {
        if (mainFragment != null) {
            transaction.hide(mainFragment);
        }
        if (followFragment != null) {
            transaction.hide(followFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 为ActionBar扩展菜单项
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.START);
            case R.id.menu_set:
                ToastUtils.showShort(MainActivity.this, "待开发...");
                break;
            case R.id.menu_return_main:
                ToastUtils.showShort(MainActivity.this, "待开发...");
                break;
            case R.id.menu_quit:
                ActivityCollector.finishAll();
                break;

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!mBackKeyPressed) {
            ToastUtils.showShort(MainActivity.this, "再按一次退出程序");
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            }, 2000);
        } else {
            this.finish();
            System.exit(0);
        }
    }
}
