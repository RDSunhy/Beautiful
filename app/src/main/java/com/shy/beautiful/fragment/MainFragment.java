package com.shy.beautiful.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shy.beautiful.R;
import com.shy.beautiful.base.Constant;
import com.shy.beautiful.bean.ImagesBean;
import com.shy.beautiful.http.RetrofitHelper;
import com.shy.beautiful.utils.SpUtils;
import com.shy.beautiful.utils.ToastUtils;
import com.shy.beautiful.view.Indcator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Shy on 2019/3/30.
 */

public class MainFragment extends Fragment {
    @BindView(R.id.vp_images)
    ViewPager vpImages;
    @BindView(R.id.indicator)
    Indcator indicator;
    Unbinder unbinder;
    @BindView(R.id.rv_main_images)
    RecyclerView rvMainImages;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    int images[] = {R.mipmap.img_vp1, R.mipmap.img_vp2, R.mipmap.img_vp3};
    SpUtils spUtils;
    int index=1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    public void initView() {

        spUtils = new SpUtils(getActivity(), Constant.FILENAME);
        List<View> list = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            ImageView iv = new ImageView(getActivity());
            iv.setImageResource(images[i]);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            list.add(iv);
        }
        BannerAdapter adapter = new BannerAdapter(list);
        vpImages.setAdapter(adapter);
        vpImages.setPageTransformer(true, new ScalePageTransformer());
        indicator.setUpWidthViewPager(vpImages);
        swipeRefresh.setColorSchemeResources(R.color.colorAccent);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Map<String, String> map = new HashMap<>();
                //map.put("num","10");
                map.put("key", "写真");
                map.put("start", ""+index);
                map.put("offset", "50");
                index+=50;
                RetrofitHelper retrofitHelper = new RetrofitHelper(getActivity());
                retrofitHelper.getImages(map)
                        .subscribeOn(Schedulers.io())//IO线程
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<ImagesBean>>() {
                            @Override
                            public void onCompleted() {
                                swipeRefresh.setRefreshing(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                //请求失败
                                ToastUtils.showShort(getActivity(), "请求过于频繁,请稍后再试！");
                                Log.e("请求失败:", "" + e.getMessage());
                                swipeRefresh.setRefreshing(false);
                            }

                            @Override
                            public void onNext(List<ImagesBean> bean) {
                                //请求成功
                                Log.e("请求的结果", bean.toString());
                                //存入缓存到本地
                                Gson gson2 = new Gson();
                                String str = gson2.toJson(bean);
                                spUtils.put(Constant.MAIN, str);
                                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                                rvMainImages.setLayoutManager(layoutManager);
                                RvImagesAdapter adapter = new RvImagesAdapter(getActivity(), bean);
                                rvMainImages.setAdapter(null);
                                rvMainImages.setAdapter(adapter);
                                swipeRefresh.setRefreshing(false);
                            }
                        });
            }
        });
    }

    public void initData() {
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "提示", "正在加载中...");
        if (spUtils.getSharedPreference(Constant.MAIN, ImagesBean.class) == null) {
            Map<String, String> map = new HashMap<>();
            //map.put("num","10");
            map.put("key", "邻家");
            map.put("start", ""+index);
            index+=50;
            map.put("offset", "50");
            RetrofitHelper retrofitHelper = new RetrofitHelper(getActivity());
            retrofitHelper.getImages(map)
                    .subscribeOn(Schedulers.io())//IO线程
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<ImagesBean>>() {
                        @Override
                        public void onCompleted() {
                            dialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            //请求失败
                            ToastUtils.showShort(getActivity(), "请求过于频繁,请稍后再试！");
                            Log.e("请求失败:", "" + e.getMessage());
                            dialog.dismiss();
                        }

                        @Override
                        public void onNext(List<ImagesBean> bean) {
                            //请求成功
                            Log.e("请求的结果", bean.toString());
                            //存入缓存到本地
                            Gson gson2 = new Gson();
                            String str = gson2.toJson(bean);
                            spUtils.put(Constant.MAIN, str);
                            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                            rvMainImages.setLayoutManager(layoutManager);
                            RvImagesAdapter adapter = new RvImagesAdapter(getActivity(), bean);
                            rvMainImages.setAdapter(adapter);
                            dialog.dismiss();
                        }
                    });
        } else {
            index = 50;
            String jsonList = (String) spUtils.getSharedPreference(Constant.MAIN, String.class);
            Gson gson1 = new Gson();
            List<ImagesBean> list = gson1.fromJson(jsonList, new TypeToken<List<ImagesBean>>() {
            }.getType());
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            rvMainImages.setLayoutManager(layoutManager);
            RvImagesAdapter adapter = new RvImagesAdapter(getActivity(), list);
            rvMainImages.setAdapter(adapter);
            dialog.dismiss();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
