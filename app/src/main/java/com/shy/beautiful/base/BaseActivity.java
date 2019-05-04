package com.shy.beautiful.base;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.shy.beautiful.R;
import com.shy.beautiful.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

/**
 * Created by Shy on 2019/3/29.
 */

public abstract class BaseActivity extends AppCompatActivity {

    /***是否显示标题栏*/
    private  boolean isshowtitle = true;
    /***是否显示标题栏*/
    private  boolean isshowstate = false;

    private IntentFilter intentFilter;

    static private BaseActivity currentActivity;
    //private NetworkStateReceiver networkStateReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        intentFilter = new IntentFilter();
        //networkStateReceiver = new NetworkStateReceiver();
        if(!isshowtitle){
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        if(isshowstate){
            getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                    WindowManager.LayoutParams. FLAG_FULLSCREEN);
        }
        overridePendingTransition(R.anim.from_right, R.anim.no_slide);
        //设置布局
        setContentView(initLayout());
        //初始化控件
        initView();
        //设置数据
        initData();


    }

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int initLayout();

    /**
     * 初始化布局
     */
    public abstract void initView();

    /**
     * 设置数据
     */
    public abstract void initData();

    /**
     * 是否设置标题栏
     *
     * @return
     */
    public void setTitle(boolean ishow) {
        isshowtitle=ishow;
    }

    /**
     * 设置是否显示状态栏
     * @param ishow
     */
    public void setState(boolean ishow) {
        isshowstate=ishow;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(networkStateReceiver);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_slide, R.anim.out_left  );
    }
    @Override
    protected void onResume() {
        super.onResume();
        //intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        //registerReceiver(networkStateReceiver,intentFilter);
    }

    /**
     * 获取当前Activity的实例
     *
     * @return
     */
    public static Activity getCurrentActivty() {
        return currentActivity;
    }
}
