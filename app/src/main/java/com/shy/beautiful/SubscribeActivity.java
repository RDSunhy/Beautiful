package com.shy.beautiful;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shy.beautiful.base.BaseActivity;
import com.shy.beautiful.base.Constant;
import com.shy.beautiful.utils.SpUtils;
import com.shy.beautiful.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shy on 2019/3/30.
 */

public class SubscribeActivity extends BaseActivity {
    @BindView(R.id.iv_legs)
    ImageView ivLegs;
    @BindView(R.id.tv_legs)
    TextView tvLegs;
    @BindView(R.id.bn_legs)
    ImageButton bnLegs;
    @BindView(R.id.iv_lolita)
    ImageView ivLolita;
    @BindView(R.id.tv_lolita)
    TextView tvLolita;
    @BindView(R.id.bn_lolita)
    ImageButton bnLolita;
    @BindView(R.id.iv_temptation)
    ImageView ivTemptation;
    @BindView(R.id.tv_temptation)
    TextView tvTemptation;
    @BindView(R.id.bn_temptation)
    ImageButton bnTemptation;
    @BindView(R.id.iv_europe)
    ImageView ivEurope;
    @BindView(R.id.tv_europe)
    TextView tvEurope;
    @BindView(R.id.bn_europe)
    ImageButton bnEurope;
    @BindView(R.id.iv_acg)
    ImageView ivAcg;
    @BindView(R.id.tv_acg)
    TextView tvAcg;
    @BindView(R.id.bn_acg)
    ImageButton bnAcg;
    @BindView(R.id.iv_ttt)
    ImageView ivTtt;
    @BindView(R.id.tv_ttt)
    TextView tvTtt;
    @BindView(R.id.bn_ttt)
    ImageButton bnTtt;
    @BindView(R.id.bn_next)
    Button bnNext;

    SpUtils spUtils;

    int isSubNum = 0 ;
    private static boolean mBackKeyPressed = false;//记录是否有首次按键

    @Override
    public int initLayout() {
        return R.layout.activity_subscribe;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        spUtils = new SpUtils(SubscribeActivity.this, Constant.FILENAME);
        getSupportActionBar().hide();
        if (spUtils.getSharedPreference(Constant.ISLEGS,String.class)!=null&&spUtils.getSharedPreference(Constant.ISLEGS,String.class).equals("1")){
            isSubNum++;
            bnLegs.setBackgroundResource(R.drawable.ib_sub);
            bnLegs.setEnabled(false);
        }
        if (spUtils.getSharedPreference(Constant.ISLOLITA,String.class)!=null&&spUtils.getSharedPreference(Constant.ISLOLITA,String.class).equals("1")){
            isSubNum++;
            bnLolita.setBackgroundResource(R.drawable.ib_sub);
            bnLolita.setEnabled(false);
        }
        if (spUtils.getSharedPreference(Constant.ISTEMPTATION,String.class)!=null&&spUtils.getSharedPreference(Constant.ISTEMPTATION,String.class).equals("1")){
            isSubNum++;
            bnTemptation.setBackgroundResource(R.drawable.ib_sub);
            bnTemptation.setEnabled(false);
        }
        if (spUtils.getSharedPreference(Constant.ISEUROPE,String.class)!=null&&spUtils.getSharedPreference(Constant.ISEUROPE,String.class).equals("1")){
            isSubNum++;
            bnEurope.setBackgroundResource(R.drawable.ib_sub);
            bnEurope.setEnabled(false);
        }
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.bn_legs, R.id.bn_lolita, R.id.bn_temptation, R.id.bn_europe, R.id.bn_acg, R.id.bn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bn_legs:
                spUtils.put(Constant.ISLEGS,"1");
                isSubNum++;
                bnLegs.setBackgroundResource(R.drawable.ib_sub);
                bnLegs.setEnabled(false);
                break;
            case R.id.bn_lolita:
                spUtils.put(Constant.ISLOLITA,"1");
                isSubNum++;
                bnLolita.setBackgroundResource(R.drawable.ib_sub);
                bnLolita.setEnabled(false);
                break;
            case R.id.bn_temptation:
                spUtils.put(Constant.ISTEMPTATION,"1");
                isSubNum++;
                bnTemptation.setBackgroundResource(R.drawable.ib_sub);
                bnTemptation.setEnabled(false);
                break;
            case R.id.bn_europe:
                spUtils.put(Constant.ISEUROPE,"1");
                isSubNum++;
                bnEurope.setBackgroundResource(R.drawable.ib_sub);
                bnEurope.setEnabled(false);
                break;
            case R.id.bn_acg:
                ToastUtils.showShort(SubscribeActivity.this,"待开发...");
                break;
            case R.id.bn_next:
                if(isSubNum>0){
                    spUtils.put(Constant.ISFIRST,"1");
                    Intent i = new Intent(SubscribeActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    ToastUtils.showShort(SubscribeActivity.this,"最少关注一个");
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!mBackKeyPressed) {
            ToastUtils.showShort(SubscribeActivity.this, "再按一次退出程序");
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mBackKeyPressed=false;
                }
            },2000);
        }else {
            this.finish();
            System.exit(0);
        }
    }
}
