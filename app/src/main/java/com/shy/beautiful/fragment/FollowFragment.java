package com.shy.beautiful.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shy.beautiful.R;
import com.shy.beautiful.base.Constant;
import com.shy.beautiful.fragment.category.EuropeFragment;
import com.shy.beautiful.fragment.category.FragmentAdapter;
import com.shy.beautiful.fragment.category.LegsFragment;
import com.shy.beautiful.fragment.category.LolitaFragment;
import com.shy.beautiful.fragment.category.TemptationFragment;
import com.shy.beautiful.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Shy on 2019/3/30.
 */

public class FollowFragment extends Fragment {

    @BindView(R.id.tab_Layout)
    TabLayout tabLayout;
    @BindView(R.id.view_Pagers)
    ViewPager viewPagers;
    Unbinder unbinder;

    private List<Fragment> list;
    SpUtils spUtils;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        spUtils = new SpUtils(getActivity(), Constant.FILENAME);
        list = new ArrayList<>();
        if(spUtils.getSharedPreference(Constant.ISLEGS,String.class)!=null&&spUtils.getSharedPreference(Constant.ISLEGS,String.class).equals("1")){
            list.add(new LegsFragment());
        }
        if(spUtils.getSharedPreference(Constant.ISLOLITA,String.class)!=null&&spUtils.getSharedPreference(Constant.ISLOLITA,String.class).equals("1")){
            list.add(new LolitaFragment());
        }
        if(spUtils.getSharedPreference(Constant.ISTEMPTATION,String.class)!=null&&spUtils.getSharedPreference(Constant.ISTEMPTATION,String.class).equals("1")){
            list.add(new TemptationFragment());
        }
        if(spUtils.getSharedPreference(Constant.ISEUROPE,String.class)!=null&&spUtils.getSharedPreference(Constant.ISEUROPE,String.class).equals("1")){
            list.add(new EuropeFragment());
        }
        int tabIndex = 0;
        FragmentAdapter adapter = new FragmentAdapter(getFragmentManager(), list);
        viewPagers.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPagers);
        if(spUtils.getSharedPreference(Constant.ISLEGS,String.class)!=null&&spUtils.getSharedPreference(Constant.ISLEGS,String.class).equals("1")){
            tabLayout.getTabAt(tabIndex++).setText("美腿");
        }
        if(spUtils.getSharedPreference(Constant.ISLOLITA,String.class)!=null&&spUtils.getSharedPreference(Constant.ISLOLITA,String.class).equals("1")){
            tabLayout.getTabAt(tabIndex++).setText("萝莉");
        }
        if(spUtils.getSharedPreference(Constant.ISTEMPTATION,String.class)!=null&&spUtils.getSharedPreference(Constant.ISTEMPTATION,String.class).equals("1")){
            tabLayout.getTabAt(tabIndex++).setText("诱惑");
        }
        if(spUtils.getSharedPreference(Constant.ISEUROPE,String.class)!=null&&spUtils.getSharedPreference(Constant.ISEUROPE,String.class).equals("1")){
            tabLayout.getTabAt(tabIndex++).setText("欧美");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
